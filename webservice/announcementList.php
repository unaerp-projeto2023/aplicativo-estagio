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
$myJobs           = (isset($_REQUEST["myJobs"          ])) ? $_REQUEST["myJobs"          ] :  0;
$companyId        = (int)$companyId;
$userId           = (int)$userId;
$myJobs           = (int)$myJobs;

if ($companyId === 0 && $myJobs === 0) { $_condi .= " AND (a.final_date > NOW())"; }
if ($companyId > 0)                    { $_condi .= " AND (a.id_user = {$companyId})"; }
if ($userId    > 0 && $myJobs === 1)   { $_condi .= " AND (c.id_user = {$userId})"; }
if (!empty($area_description))         { $_condi .= " AND (a.area_description LIKE '%{$area_description}%')"; }
if (!empty($locality        ))         { $_condi .= " AND (a.locality         LIKE '%{$locality}%')";         }
if (!empty($description     ))         { $_condi .= " AND (a.description      LIKE '%{$description}%')";      }

// Retorna a lista
$query = "SELECT a.*, b.name as company, c.id_user AS id_relation
          FROM {$TB_ANUNCIO} a
          LEFT JOIN {$TB_USUARIO} b ON b.id = a.id_user
          LEFT JOIN {$TB_RELACAO} c ON c.id_announcement = a.id
          WHERE 1
          {$_condi}
          ORDER BY a.start_date DESC";
          //echo $query; exit();
$lsql = $dba->query($query);
$num  = $dba->num_rows($lsql);
$num  = (int)$num;

// verifica o retorno
if ($num === 0) {
   $response = array("codeError" => 99,
                     "message"   => "Nenhum anúncio de vaga disponível!",
                     "result"    => null);
}
else {
   // inicia o JSON
   $response = array("codeError" => 0, "message" => "");
   $data     = array();

   // percorre o resultado
   while($row = $dba->fetch_object($lsql)) {
       $id          = $row->id;
       $id_relation = $row->id_relation;
       $start_date  = Util::validar($row->start_date);
       $final_date  = Util::validar($row->final_date);
       $total       = $dba->retNumSQL($TB_RELACAO, "(id_announcement = {$id})");
       
       if ($myJobs === 0 && $userId === (int)$id_relation && $companyId === 0) {}
       else {
           // monta o JSON de retorno
           $data[] = array("id"               => $id                   ,
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
                           "total"            => $total                ,
                           "myJobs"           => $myJobs               );
       }
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

