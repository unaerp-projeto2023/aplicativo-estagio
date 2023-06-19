<?php
/**
 * API de Cadastro de Usuario
 * -------------------------------------------------------------------
 * type: (A)nunciante
 *       (U)suario   (Estagario)
 * -------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */

// pega as configuracoes
require_once("config.inc");

// pega os dados
$name     = (isset($_REQUEST["name"    ])) ? $_REQUEST["name"    ] : "";
$password = (isset($_REQUEST["password"])) ? $_REQUEST["password"] : "";
$email    = (isset($_REQUEST["email"   ])) ? $_REQUEST["email"   ] : "";
$type     = (isset($_REQUEST["type"    ])) ? $_REQUEST["type"    ] : "U";


// pega o Cadastro
$cadastro = new Cadastro();
$campos = array("name"     => $name    ,
                "email"    => $email   ,
                "password" => $password,
                "type"     => $type    );

// inclui os dados da solicitação
$id = $cadastro->incluir($TB_USUARIO, $campos);


if ($id <= 0) {
   $response = array("codeError" => 99, "message" => "Erro ao realizar o cadastro!", "result" => null);
}
else {
   $response = array("codeError" => 0,
                     "message"   => "Usuário cadastrado com sucesso!!!",
                     "result"    => array("id"       => $id      ,
                                          "name"     => $name    ,
                                          "password" => $password,
                                          "email"    => $email   ,
                                          "type"     => $type    ));
}


// finaliza o script
$dba->close();
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();

