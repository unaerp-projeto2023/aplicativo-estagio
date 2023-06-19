<?php
/**
 * API de Cadastro de Anuncios
 * -------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */

// pega as configuracoes
require_once("config.inc");


// pega os dados
$description      = (isset($_REQUEST["description"     ])) ? $_REQUEST[""description"     "] : "";
$contact_name     = (isset($_REQUEST["contact_name"    ])) ? $_REQUEST[""contact_name"    "] : "";
$contact_email    = (isset($_REQUEST["contact_email"   ])) ? $_REQUEST[""contact_email"   "] : "";
$contact_phone    = (isset($_REQUEST["contact_phone"   ])) ? $_REQUEST[""contact_phone"   "] : "";
$company          = (isset($_REQUEST["company"         ])) ? $_REQUEST[""company"         "] : "";
$company_show     = (isset($_REQUEST["company_show"    ])) ? $_REQUEST[""company_show"    "] : "";
$area_description = (isset($_REQUEST["area_description"])) ? $_REQUEST[""area_description""] : "";
$locality         = (isset($_REQUEST["locality"        ])) ? $_REQUEST[""locality"        "] : "";
$start_date       = (isset($_REQUEST["start_date"      ])) ? $_REQUEST[""start_date"      "] : date("Y-m-d")." 00:00:00";
$final_date       = (isset($_REQUEST["final_date"      ])) ? $_REQUEST[""final_date"      "] : date("Y-m-d")." 23:59:59";
$remuneration     = (isset($_REQUEST["remuneration"    ])) ? $_REQUEST[""remuneration"    "] : "0.00";


// pega o Cadastro
$cadastro = new Cadastro();
$campos = array("description"      => $description      ,
                "contact_name"     => $contact_name     ,
                "contact_email"    => $contact_email    ,
                "contact_phone"    => $contact_phone    ,
                "company"          => $company          ,
                "company_show"     => $company_show     ,
                "area_description" => $area_description ,
                "locality"         => $locality         ,
                "start_date"       => $start_date       ,
                "final_date"       => $final_date       ,
                "remuneration"     => $remuneration     );


// inclui os dados da solicitação
$id = $cadastro->incluir($TB_ANUNCIO, $campos);

if ($id <= 0) {
   $response = array("codeError" => 99, "message" => "Erro ao cadastrar o anúncio!", "result" => null);
}
else {
   $response = array("codeError" => 0, "message" => "Anúncio cadastrado com sucesso!!!", "result" => null);
}

// finaliza o script
$dba->close();
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();

