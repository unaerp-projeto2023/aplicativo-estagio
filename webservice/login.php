<?php
/**
 * API que valida um LOGIN
 * -------------------------------------------------------------------------------------------
 * @author Guilherme Galvão
 * @version 1.0
 * @copyright 2023
 */
// pega as configuracoes
require_once("config.inc");


// pega os campos
$login = (isset($_REQUEST["email"   ])) ? $_REQUEST["email"   ] : "";
$senha = (isset($_REQUEST["password"])) ? $_REQUEST["password"] : "";

// tira dados mal intencionados da login
$login = htmlentities($login);
$login = trim(strip_tags(str_replace("'", "", $login)));
$login = str_replace("--"   , "", $login);
$login = str_replace("UNION", "", $login);
$login = str_replace("#"    , "", $login);
$login = str_replace("md5"  , "", $login);
$login = str_replace("\\"   , "", $login);
$login = strtolower($login);

// tira dados mal intencionados da Senha
$senha = htmlentities($senha);
$senha = trim(strip_tags(str_replace("'", "", $senha)));
$senha = str_replace("--"   , "", $senha);
$senha = str_replace("UNION", "", $senha);
$senha = str_replace("#"    , "", $senha);
$senha = str_replace("/"    , "", $senha);
$senha = str_replace("md5"  , "", $senha);
$senha = str_replace("\\"   , "", $senha);

//************************************************
// seta os tipos de erro (status)
//************************************************
// ok             - Login autorizado
// nao_autorizado - Login nao autorizado
//************************************************

// seta variaveis
$response = array();

// verifica o login
$lsql = $dba->query("SELECT * FROM {$TB_USUARIO} WHERE (email = '{$login}' AND (password = '{$esenha}')");
$num  = $dba->num_rows($lsql);
$num  = (int)$num;

// verifica o retorno
if ($num === 0) {
   $response = array("codeError" => 99,
                     "message"   => "Usuário/Senha inválido!",
                     "result"    => null);
}
else {
// pega os campos
$row      = $dba->fetch_object($lsql);
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

