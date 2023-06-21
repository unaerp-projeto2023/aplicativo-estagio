package com.example.finaldesafio.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.textfield.TextInputEditText

class MaskDate constructor(private val campo: TextInputEditText) : TextWatcher {
    private var isUpdating = false
    private val phoneMask  = "##/##/####"
    private var old        = ""

    init {
        campo.editableText.toString().setValue()
    }

    private fun CharSequence.setValue() {
        val str  = this.toString().uMask()
        val mask = StringBuilder()
        try {
            var i = 0
            for (m in phoneMask.toCharArray()) {
                if (m != '#' && str.length > old.length) {
                    mask.append(m)
                    continue
                }
                try { mask.append(str[i]) }
                catch (_: Exception) { break }
                i++
            }
            campo.setText(mask.toString());
            campo.setSelection(mask.toString().length);
        }
        catch (e: NumberFormatException) {
            Log.d("MIFARE", "NumberFormatException: ${e.message}")
        }
    }

    private fun String.uMask() : String =
        this.trim()
            .replace(".", "")
            .replace("-", "")
            .replace("/", "")
            .replace("(", "")
            .replace(")", "")

    /****************************************************
     * Interfaces from TextWatcher
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isUpdating) {
            old        = s.toString()
            isUpdating = false
        }
        else {
            isUpdating = true
            s?.setValue()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
}