<?php
/**
 * API que lista os Anuncios
 * -------------------------------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */
// pega as configuracoes
require_once("config.inc");


// pega os campos
$_condi           = "";
$companyId        = (isset($_REQUEST["companyId"       ])) ? $_REQUEST["companyId"       ] :  0;
$userId           = (isset($_REQUEST["userId"          ])) ? $_REQUEST["userId"          ] :  0;
$area_description = (isset($_REQUEST["area_description"])) ? $_REQUEST["area_description"] : "";
$locality         = (isset($_REQUEST["locality"        ])) ? $_REQUEST["locality"        ] : "";
$description      = (isset($_REQUEST["description"     ])) ? $_REQUEST["description"     ] : "";


if ($companyId > 0)            { $_condi .= " AND (a.id_user = {$companyId})"; }
if ($userId    > 0)            { $_condi .= " AND (b.id_user = {$userId})";    }
if (!empty($area_description)) { $_condi .= " AND (a.area_description LIKE '%{$area_description}%')"; }
if (!empty($locality        )) { $_condi .= " AND (a.locality         LIKE '%{$locality}%')";         }
if (!empty($description     )) { $_condi .= " AND (a.description      LIKE '%{$description}%')";      }


// Retorna a lista
$query = "SELECT a.*,
                 count(b.id) AS total,
                 c.name as company
          FROM {$TB_ANUNCIO} a
          LEFT JOIN {$TB_RELACAO} b ON b.id_announcement = a.id
          LEFT JOIN {$TB_USUARIO} c ON c.id              = a.id_user
          WHERE (a.final_date > NOW())
          {$_condi}
          ORDER BY a.start_date DESC";
$lsql = $dba->query($query);
$num  = $dba->num_rows($lsql);
$num  = (int)$num;

// verifica o retorno
if ($num === 0) {
   $response = array("codeError" => 99,
                     "message"   => "Nenhuma anúncio de vaga disponível!",
                     "result"    => null);
}
else {
   // inicia o JSON
   $response = array("codeError" => 0, "message" => "");

   // percorre o resultado
   while($row = $dba->fetch_object($lsql)) {
     $start_date = Util::validar($row->start_date);
     $final_date = Util::validar($row->final_date);

     // monta o JSON de retorno
     $data[] = array("id"               => $row->id              ,
                     "id_user"          => $row->id_user         ,
                     "description"      => $row->description     ,
                     "contact_name"     => $row->contact_name    ,
                     "contact_email"    => $row->contact_email   ,
                     "contact_phone"    => $row->contact_phone   ,
                     "company"          => $row->company         ,
                     "company_show"     => $row->company_show    ,
                     "area_description" => $row->area_description,
                     "locality"         => $row->locality        ,
                     "start_date"       => $start_date           ,
                     "final_date"       => $final_date           ,
                     "remuneration"     => $row->remuneration    ,
                     "total"            => $row->total           );
	}

    // finaliza
	$response["result"] = array("items" => $data);
}

// limpa
$dba->free($lsql);
$dba->close();

// retorna
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();



