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
$esenha   = SEGURANCA::encrypt($password);
$esenha   = str_replace("%","-", $esenha);


// verifica o login
$sql  = "SELECT * FROM {$TB_USUARIO} WHERE (email = '{$login}'))";
$lsql = $dba->query($sql);
$num  = $dba->num_rows($lsql);
$num  = (int)$num;

// verifica o retorno
if ($num === 0) {
   $response = array("codeError" => 99,
                     "message"   => "E-mail já existente!",
                     "result"    => null);
}
else {
// pega o Cadastro
$cadastro = new Cadastro();
$campos = array("name"     => $name  ,
                "email"    => $email ,
                "password" => $esenha,
                "type"     => $type  );

// inclui os dados da solicitação
$id = $cadastro->incluir($TB_USUARIO, $campos);


if ($id <= 0) {
   $response = array("codeError" => 99, "message" => "Erro ao realizar o cadastro!", "result" => null);
}
else {
   $response = array("codeError" => 0,
                     "message"   => "Usuário cadastrado com sucesso!!!",
                     "result"    => array("id"    => $id   ,
                                          "name"  => $name ,
                                          "email" => $email,
                                          "type"  => $type ));
}
}


// finaliza o script
$dba->close();
echo json_encode($response, JSON_UNESCAPED_UNICODE);
exit();

