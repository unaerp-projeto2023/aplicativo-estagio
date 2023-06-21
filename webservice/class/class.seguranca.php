<?php
/**
 * Classe de Seguranca
 * ---------------------------------------------------------------------------------
 * Metodos desta classe
 * ---------------------------------------------------------------------------------
 * encrypt() ...: encripta uma string
 * decrypt() ...: descripta uma string
 * ---------------------------------------------------------------------------------
 * Exemplo de uso:
 * 
 * encripta
 * $encrypt = SEGURANCA::encrypt("teste");
 *
 * descripta
 * $decrypt = SEGURANCA::decrypt($encrypt);
 * ---------------------------------------------------------------------------------
 * @author Claudio Monteoliva
 * @version 1.0
 * @copyright 2021 Monteoliva Developer
 */

class SEGURANCA {
   /**
    * Metodo para encriptar uma string
    * --------------------------------------------------------------
    * @param <text>  $s (string a ser encriptada)
    * --------------------------------------------------------------
    * @return <text> (retorna a string encriptada)
    * --------------------------------------------------------------
   */
   public static function encrypt($s) { return urlencode(base64_encode($s)); }

   /**
    * Metodo para descriptar uma string
    * --------------------------------------------------------------
    * @param <text>  $s (string encriptada)
    * --------------------------------------------------------------
    * @return <text> (retorna a string descriptada)
    * --------------------------------------------------------------
   */
   public static function decrypt($s) { return base64_decode(urldecode($s)); }
}