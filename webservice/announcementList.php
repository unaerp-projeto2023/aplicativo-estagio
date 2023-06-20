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


if ($companyId > 0)            { $_condi .= " AND (a.id_user = {$companyId})" }
if ($userId    > 0)            { $_condi .= " AND (b.id_user = {$userId})"    }
if (!empty($area_description)) { $_condi .= " AND (a.area_description LIKE '%{$area_description}%')" }
if (!empty($locality        )) { $_condi .= " AND (a.locality         LIKE '%{$locality}%')"         }
if (!empty($description     )) { $_condi .= " AND (a.description      LIKE '%{$description}%')"      }


// Retorna a lista
$query = "SELECT a.*,
                 count(b.id) AS total
          FROM {$TB_ANUNCIO} a
          LEFT JOIN {$TB_RELACAO} b ON b.id_announcement = a.id
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
     $dt_conclusao = Util::validar($row->dt_conclusao);
     $dt_ultimo    = Util::validar($row->dt_ultimo_andamento);
          
     // monta o JSON de retorno
     $data[] = array("id"                => $row->id         ,
                     "assunto"           => $row->nm_tipo    ,
                     "dtAbertura"        => $row->dt_abertura,
                     "dtUltimoAndamento" => $dt_ultimo       ,
                     "dtConclusao"       => $dt_conclusao);
	}

    // finaliza
	$response["result"] = array("items" => $data);








$response = array("codeError" => 0,
                  "message"   => "Login de acesso realizado com sucesso!",
                  "result"    => array("id"       => $row->id      ,
                                       "name"     => $row->name    ,
                                       "password" => $row->password,
                                       "email"    => $row->email   ,
                                       "type"     => $row->type    ));
}

// limpa
$dba->free($lsql);
$dba->close();

// retorna
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();



