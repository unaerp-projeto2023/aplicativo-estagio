<?php
/**
 * Programa de Configuracao
 * -------------------------------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */
// adiciona as classes
require_once("local.inc"                  );
require_once("./class/class.seguranca.php");
require_once("./class/class.mysqli.php"   );
require_once("./class/class.cadastro.php" );


// pega o metodo do HTTP
$httpMethod = (isset($_SERVER["REQUEST_METHOD"])) ? $_SERVER["REQUEST_METHOD"] : "";


// seta classe de banco de dados
$dba = new Connect_Database();
$dba->setHost($config["host"]);
$dba->setUser($config["user"]);
$dba->setPass($config["pass"]);
$dba->setDatabase($config["database"]);

// realiza a conexao com o MySQL
$dba->__connect();
