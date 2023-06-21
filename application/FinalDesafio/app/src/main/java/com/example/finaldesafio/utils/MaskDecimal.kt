package com.example.finaldesafio.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.*

class MaskDecimal constructor(private val campo: TextInputEditText) : TextWatcher {
    private var isUpdating = false
    private val nf         = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    init {
        campo.editableText.toString().setValue()
    }

    private fun CharSequence.setValue() {
        var str = this.toString().uMask()
        try {
            str = nf.format(str.trim().toDouble() / 100)
            if (!str.startsWith("R$ ", true)) {
                if (str.startsWith("R$", true)) {
                    str = str.replace("R$", "R$ ")
                }
            }
            campo.setText(str)
            campo.setSelection(campo.editableText.toString().length)
        }
        catch (e: NumberFormatException) {
            Log.d("MIFARE", "NumberFormatException: ${e.message}")
        }
    }

    private fun String.uMask(): String {
        var str     = this.trim()
        val hasMask = (str.startsWith("R$ ", true) ||
                       str.startsWith("R$" , true) ||
                       str.startsWith("$"  , true) &&
                      (str.indexOf(".")  > -1 || str.indexOf(",") > -1))
        if (hasMask) {
            str = str.replace("R$ ", "")
                     .replace("R$" , "")
                     .replace(","  , "")
                     .replace("."  , "")
        }
        return str
    }

    /****************************************************
     * Interfaces from TextWatcher
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isUpdating) { isUpdating = false }
        else {
            isUpdating = true
            s?.setValue()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
}