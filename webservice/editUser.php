<?php
/**
 * API de Cadastro de Usuario (Atualizar)
 * -------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */

// pega as configuracoes
require_once("config.inc");

// pega os dados
$id    = (isset($_REQUEST["id"   ])) ? $_REQUEST["id"   ] :  0;
$name  = (isset($_REQUEST["name" ])) ? $_REQUEST["name" ] : "";
$email = (isset($_REQUEST["email"])) ? $_REQUEST["email"] : "";

// pega o Cadastro
$cadastro = new Cadastro();
$campos = array("name" => $name,"email" => $email);

// inclui os dados da solicitação
$result = $cadastro->atualizar($TB_USUARIO, $campos, "(id = {$id})");


if ($result <= 0) {
   $response = array("codeError" => 99, "message" => "Erro ao atualizar o perfil!", "result" => null);
}
else {
   $response = array("codeError" => 0,
                     "message"   => "Perfil atualizado com sucesso!!!",
                     "result"    => null);
}


// finaliza o script
$dba->close();
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();

