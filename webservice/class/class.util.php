<?php
/**
 * Classe de Metodos Globais
 * ---------------------------------------------------------------------------------
 * @author Guilherme Galvao
 * @version 1.0
 * @copyright 2023
 */
class Util {
   public static function validar($value) {
       $value = trim($value);
       $value = str_replace("0000-00-00 00:00:00", "", $value);
       if      (empty($value))    { return ""; }
       else if ($value == "null") { return ""; }
       return $value;
   }
}
