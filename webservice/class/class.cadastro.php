<?php
/**
 * Classe para gerar instrucoes no banco de dados
 * --------------------------------------------------------------------------------------
 * Metodos desta classe
 * --------------------------------------------------------------------------------------
 * incluir() ..: Metodo que insere os dados em uma tabela (INSERT)
 * atualizar() : Metodo para atualizar dados de uma tabela (UPDATE)
 * excluir() ..: Metodo para excluir dados de uma tabela (DELETE)
 * --------------------------------------------------------------------------------------
 * @author Luis Claudio Monteoliva
 * @version 1.0
 * @copyright 2021 Monteoliva Developer
 */
class Cadastro {
	// parametros
	private $dba;
	
	/**
	 * Constructor
	 */
	public function __construct() {
		// pega variavel global
		global $dba;
	
		// seta o objeto do banco
		$this->dba = $dba;
	}
	
  /**
   * Metodo que insere os dados em uma tabela (INSERT)
   * --------------------------------------------------------------
   * @param <text>  $tabela (tabela que vai ser inserida os dados)
   * @param <array> $dados  (array com os campos e dados)
   * --------------------------------------------------------------
   * @return <int> return (0-FALHA ou 1-OK)
   * --------------------------------------------------------------
   * exemplo:
   * // instacia a classe
   * $cadastro = new Cadastro();
   *
   * // cria o array de campos e os dados
   * $dados = array("campo1" => "dado1",
   *                "campo2" => "dado2",
   *                "campo3" => "dado3");
   *
   * // executa a inclusao
   * $code = $cadastro->incluir("tabela",$dados);
   * --------------------------------------------------------------
   */
  public function incluir($tabela, $dados) {
    // seta variaveis
    $campos  = array();
    $valores = array();

    // pega os campos e valores
    foreach(array_keys($dados)   AS $campo) { $campos[]  = $campo; }
    foreach(array_values($dados) AS $valor) { $valores[] = $valor; }

    // inicia a instrucao
    $strSql = "INSERT INTO ".$tabela." (";

    // percorre o array com os campos
    for($li=0;$li<count($campos);$li++) { $strSql .= ($li == 0) ? $campos[$li] : ", ".$campos[$li]; }

    // inicia o VALUES
    $strSql .= ") VALUES (";

    // percorre array com os dados
    for($li=0;$li<count($valores);$li++) { $strSql .= ($li == 0) ? "'".$valores[$li]."'" : ", '".$valores[$li]."'"; }

    // finaliza a instrucao
    $strSql .= ")";

    // executa a instrucao (query)
    $bolResult = $this->dba->query($strSql);

    // retorna (0-FALHA ou ID-OK)
    return ((!$bolResult) ? 0 : $this->dba->identity());
  }

  /**
   * Metodo para atualizar dados de uma tabela (UPDATE)
   * --------------------------------------------------------------
   * @param <text>  $tabela (tabela que vai ser inserida os dados)
   * @param <array> $dados  (array com os campos e dados)
   * @param <text>  $cond   (condicao "WHERE")
   * --------------------------------------------------------------
   * @return <int> return (0-FALHA ou 1-OK)
   * --------------------------------------------------------------
   * exemplo:
   * // instacia a classe
   * $cadastro = new Cadastro();
   *
   * // cria o array de campos e os dados
   * $dados = array("campo1" => "dado1",
   *                "campo2" => "dado2",
   *                "campo3" => "dado3");
   *
   * // executa a alteracao
   * $cadastro->alterar("tabela",$dados,"(cd_codigo = 1)");
   * --------------------------------------------------------------
   */
  public function atualizar($tabela, $dados, $cond) {
    // incia a instrucao
    $strQuery = "UPDATE ".$tabela." SET ";

    // seta variaveis
    $campos  = array();
    $valores = array();

    // pega os campos e valores
    foreach(array_keys($dados)   AS $campo) { $campos[]  = $campo; }
    foreach(array_values($dados) AS $valor) { $valores[] = $valor; }

    // percorre o array com os campos e o array com os dados
    for($li=0;$li<count($campos);$li++) {
        // seta a query
        if ($li == 0) {
            $strQuery .= $campos[$li]." = '".$valores[$li]."'";
        }
        else {
            $strQuery .= ", ".$campos[$li]." = '".$valores[$li]."'";
        }
    }

    // pega a condicao (se existir)
    if (!empty($cond)) {
        $strQuery .= " WHERE ".$cond;
    }

    // executa a instrucao (query)
    $bolResult = $this->dba->query($strQuery);

    // retorna (0-FALHA ou 1-OK)
    return ((!$bolResult) ? 0 : 1);
  }

  /**
   * Metodo para excluir dados de uma tabela (DELETE)
   * --------------------------------------------------------------
   * @param <text>  $tabela (tabela que vai ser inserida os dados)
   * @param <text>  $cond   (condicao "WHERE")
   * --------------------------------------------------------------
   * @return <int> return (0-FALHA ou 1-OK)
   * --------------------------------------------------------------
   * exemplo:
   * // instacia a classe
   * $cadastro = new Cadastro();
   *
   * // executa a exclusao
   * $code = $cadastro->excluir("tabela","(cd_codigo = 1)");
   * --------------------------------------------------------------
   */
  public function excluir($tabela, $cond) {
    // monta a instrucao
    $strQuery = "DELETE FROM ".$tabela." WHERE ".$cond;

    // executa a instrucao (query)
    $bolResult = $this->dba->query($strQuery);

    // retorna (0-FALHA ou 1-OK)
    return ((!$bolResult) ? 0 : 1);
  }
}