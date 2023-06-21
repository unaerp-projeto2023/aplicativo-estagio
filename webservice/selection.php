<?php
/**
 * API de Relacionamento de Vaga e Usuario
 * -------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */

// pega as configuracoes
require_once("config.inc");

// pega os dados
$id_announcement = (isset($_REQUEST["id_announcement"])) ? $_REQUEST["id_announcement"] : 0;
$id_user         = (isset($_REQUEST["id_user"        ])) ? $_REQUEST["id_user"        ] : 0;

// verifica o login
$sql  = "SELECT id FROM {$TB_RELACAO} WHERE (id_user = {$id_user}) AND (id_announcement = {$id_announcement})";
$lsql = $dba->query($sql);
$num  = $dba->num_rows($lsql);
$num  = (int)$num;

$dba->free($lsql);

// verifica o retorno
if ($num > 0) {
   $response = array("codeError" => 99, "message" => "Você já se candidatou a essa vaga!", "result" => null);
}
else {
    // realiza o cadastro
    $cadastro = new Cadastro();
    $campos   = array("id_user" => $id_user, "id_announcement" => $id_announcement);
    $id       = $cadastro->incluir($TB_RELACAO, $campos);
    
    if ($id <= 0) { $response = array("codeError" => 99, "message" => "Erro ao se candidatar a essa vaga!", "result" => null); }
    else {
        $response = array("codeError" => 0,
                          "message"   => "Você se candidatou a essa vaga com sucesso!!!",
                          "result"    => null);
    }
}

// finaliza o script
$dba->close();
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();

