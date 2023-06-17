<?php
/**
 * Classe de Acesso ao Banco de Dados MySQLi
 * ---------------------------------------------------------------------------------
 * Metodos desta classe
 * ---------------------------------------------------------------------------------
 * connect() ....: realiza a conexao com o banco de dados
 * query() ......: executa uma query
 * num_rows() ...: retorna o numero de linhas de uma query
 * error() ......: retorna erro de uma query
 * result() .....: retorna um resultado de uma query
 * fetch_assoc() : retorna um array com o resultado de uma query
 * free() .......: libera a memoria de uma query
 * close() ......: disconnecta o banco de dados
 * identity() ...: retorna o ID do ultimo INSERT
 * retNum() .....: retorna o numero de linhas da query pesquisada
 * retNumSQL() ..: retorna o numero de linhas da query pesquisada (SQL)
 * geral() ......: retorna qualquer coisa que esteja no banco (abreviacao de query)
 * sql() ........: retorna uma query qualquer
 * ---------------------------------------------------------------------------------
 * @author Claudio Monteoliva
 * @version 1.0
 * @copyright 2021 Monteoliva Developer
 */
class Connect_Database {
  // seta propriedades
  private $conexao  = 0;
  private $host     = "";
  private $user     = "";
  private $pass     = "";
  private $database = "";

  /**
   * Metodo de conexao
   */
  public function __connect() {
	  try {
		  $this->conexao = new PDO("mysql:host={$this->host};dbname={$this->database}", $this->user, $this->pass);
          $this->conexao->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	  }
	  catch (PDOException $e) {
		  echo "Erro ao conectar com o Banco de Dados MySQL!!!"; exit();
      }

	  // retorna
      return 1;
  }

  /**
   * Metodo que executa uma query
   * 
   * @param $query
   */
  public function query($query) { return $this->conexao->query($query); }

  /**
   * Metodo de retorna as linhas de uma query
   * 
   * @param $query
   * @return number
   */
  public function num_rows($query) { return $query->rowCount(); }

  /**
   * Metodo que checa erro de query
   * 
   * @return string
   */
  public function error() { return $this->conexao->error(); }
  
  /**
   * Metodo que retorna a sequencia de escape
   *
   * @return string
   */
  public function real_escape($q) { return $this->conexao->real_escape_string($q); }
  
  /**
   * Metodo que retorna um array com o resultado de uma query (MYSQL_ASSOC)
   * 
   * @param $query
   * @return multitype:
   */
  public function fetch_assoc($query) { return $query->fetch(PDO::FETCH_ASSOC); }

  /**
   * Metodo que retorna um array com o resultado de uma query
   * @param $query
   */
  public function fetch_row($query) { return $query->fetch(PDO::FETCH_ASSOC); }

  /**
   * Metodo que retorna um array com o resultado de uma query
   * ------------------------------------------------------------------------------
   * $tipo: (1) - MYSQL_NUM   (traz o indice do array numerico)
   *        (2) - MYSQL_ASSOC (traz o indice do array nome do campo)
   * ------------------------------------------------------------------------------
   * @param $query
   * @param number $tipo
   * @return multitype:
   */
  public function fetch_array($query, $tipo=2) {
    // verifica o tipo
    $ntipo = ($tipo == 1) ? MYSQL_NUM : MYSQL_ASSOC;

    // retorna o Fetch Array
    return $query->fetch_array($ntipo);
  }

  /**
   * Metodo que retorna um objeto com o resultado de uma query
   * 
   * @param $query
   */
  public function fetch_object($query) { return $query->fetch(PDO::FETCH_OBJ); }

  /**
   * Metodo que retorna um field com o resultado de uma query
   * 
   * @param $query
   */
  public function fetch_field($query) { return $query->fetch_field(); }

  /**
   * Metodo que limpa a query
   * 
   * @param $query
   * @return number
   */
  public function free($query) { $query->closeCursor(); return 1; }

  /**
   * Metodo de disconnect
   * 
   * @return <number>
   */
//public function close() { $this->conexao->close(); return 1; }
  public function close() { return 1; }

  /**
   * Metodo que retorna o Identity
   */
  public function identity() { return $this->conexao->lastInsertId(); }

  /**
   * Metodo que retorna o numero de linhas da query pesquisada
   * 
   * @param <String> $tab
   * @param <String> $pes
   * @param <String> $code
   */
  public function retNum($tab,$pes,$code) {
	  $num = 0;
	  try {
		  // query
          $sql = $this->query("SELECT * FROM {$tab} WHERE ({$pes} = '{$code}')");
          $num = $this->num_rows($sql);
          $num = (int)$num;

          // limpa a query
          $this->free($sql);

	  }
	  catch (PDOException $e) {
		  echo "ERROR: ".$e->getMessage(); exit();
      }

	  // retorna
      return $num;
  }

  /**
   * Metodo que retorna o numero de linhas da query pesquisada (SQL)
   * 
   * @param <String> $tab
   * @param <String> $where
   * @return <number>
   */
  public function retNumSQL($tab, $where) {
	  $num = 0;
	  try {
		  // query
          $sql = $this->query("SELECT DISTINCT * FROM {$tab} WHERE ".$where);
          $num = $this->num_rows($sql);
          $num = (int)$num;

          // limpa a query
          $this->free($sql);
	  }
	  catch (PDOException $e) {
		  echo "ERROR: ".$e->getMessage(); exit();
      }

      // retorna
      return $num;
  }

  /**
   * Metodo que retorna qualquer coisa que esteja no banco (abreviacao de query)
   * 
   * @param $campo
   * @param $tabela
   * @param $index
   * @param $busca
   * @return <string, multitype:>
   */
  public function geral($campo, $tabela, $index, $busca) {
	  $retorno = "";
	  try {
		  // monta a query
          $sql = $this->query("SELECT {$campo} FROM {$tabela} WHERE ({$index} = '{$busca}')");
          $num = $this->num_rows($sql);
          $num = (int)$num;

          // verifica
          if ($num > 0) { $row = $this->fetch_assoc($sql); $retorno = $row[$campo]; }
          else          {                                  $retorno = "";           }
    
          // limpa a query
          $this->free($sql);
	  }
	  catch (PDOException $e) {
		  echo "ERROR: ".$e->getMessage(); exit();
      }
      
	  // retorno
      return $retorno;
  }

  /**
   * Metodo que gera uma query
   * 
   * @param <String> $campo
   * @param <String> $tabela
   * @param <String> $where
   */
  public function sql($campo, $tabela, $where) {
	  $retorno = "";
	  try {
		  // monta a query
          $sql = $this->query("SELECT {$campo} FROM {$tabela} WHERE ".$where);
          $num = $this->num_rows($sql);
          $num = (int)$num;

          // verifica
          if ($num > 0) { $row = $this->fetch_assoc($sql); $retorno = $row[$campo]; }
          else          {                                  $retorno = "";           }
    
          // limpa a query
          $this->free($sql);
	  }
	  catch (PDOException $e) {
		  echo "ERROR: ".$e->getMessage(); exit();
      }
      
	  // retorno
      return $retorno;
  }

  /**
   * Metodo que retorna o resultSet
   * 
   * @param <String> $query
   * @return <Array> (com o ResultSet)
   */
  public function sql_query($query) {
	  // inicia o resultSet
      $resultSet = array();
	  try {
		  // executa a query
          $result = $this->query($query);

	      // verifica o resultado
          if ($result) {
			  // retorna o numero de campos
              $numField = $result->num_fields();

              // inicia a linha do array
              $linha = 0;

               // percorre o resultado da query
               while($row = $this->fetch_array($result, 1)) {
				   for($li=0;$li<$numField;$li++) {
					   $nameField                     = $result->field_name($li);
                       $resultSet[$linha][$nameField] = $row[$li];
                   }

                   // incrementa a linha
                   $linha++;
               }
          }
	  }
	  catch (PDOException $e) {
		  echo "ERROR: ".$e->getMessage(); exit();
      }

    // returna o resultSet
    return $resultSet;
  }
  
  /**
  * Metodos setters
  */
  public function setHost($host)         { $this->host     = $host;     }
  public function setUser($user)         { $this->user     = $user;     }
  public function setPass($pass)         { $this->pass     = $pass;     }
  public function setDatabase($database) { $this->database = $database; }
}