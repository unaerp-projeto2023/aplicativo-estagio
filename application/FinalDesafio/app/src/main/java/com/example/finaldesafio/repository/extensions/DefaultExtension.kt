package com.example.finaldesafio.repository.extensions

import android.view.View
import com.google.android.material.textfield.TextInputEditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

import com.example.finaldesafio.utils.MaskDate
import com.example.finaldesafio.utils.MaskDecimal
import com.example.finaldesafio.utils.MaskPhone
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun View.visible()   { this.visibility = View.VISIBLE   }
fun View.invisible() { this.visibility = View.INVISIBLE }
fun View.gone()      { this.visibility = View.GONE      }

fun View.visibility(hasVisible: Boolean) {
    if (hasVisible) { this.visible() } else { this.gone() }
}

fun TextInputEditText.addChangeTextDate()    { this.addTextChangedListener(MaskDate(this))    }
fun TextInputEditText.addChangeTextPhone()   { this.addTextChangedListener(MaskPhone(this))   }
fun TextInputEditText.addChangeTextDecimal() { this.addTextChangedListener(MaskDecimal(this)) }

fun String.getDate(ff: String) : String {
    val locale = Locale("pt", "BR")
    val vDate  = this.trim().validationDate()
    if (vDate.isNotEmpty()) {
        ff.let {
            val dataO = if (vDate.length > 10) "yyyy-MM-dd HH:mm:ss" else "yyyy-MM-dd"
            val dataH = when(ff) {
                "ext"   -> "dd 'de' MMMM 'de' yyyy"
                "dma"   -> "dd/MM/yyyy"
                "amd"   -> "yyyy-MM-dd"
                "dmah"  -> "dd/MM/yyyy HH:mm"
                "dmahs" -> "dd/MM/yyyy HH:mm:ss"
                "hms"   -> "HH:mm:ss"
                "hm"    -> "HH:mm"
                else    -> "dd/MM/yyyy"
            }

            val f0 = SimpleDateFormat(dataO, locale)
            val f1 = SimpleDateFormat(dataH, locale)

            try {
                val c = GregorianCalendar(locale).also {
                    f0.parse(vDate)?.let { it1 -> it.time = it1 }
                }
                return f1.format(c.time)
            }
            catch (_: ParseException) {}
        }
    }
    return ""
}

fun String.getDateSave() : String {
    val vDate  = this.trim().validationDate()
    if (vDate.isNotEmpty()) {
        val f0 = SimpleDateFormat("dd/MM/yyyy", Locale("pr", "BR"))
        val f1 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        try {
            val c = GregorianCalendar(Locale.US).also {
                f0.parse(vDate)?.let { it1 -> it.time = it1 }
            }
            val result = f1.format(c.time)
            return result
        }
        catch (_: ParseException) {}
    }
    return ""
}

private fun String.validationDate() : String {
    val value: String = this.trim()
        .replace("0000-00-00 00:00:00", "")
        .replace("0000-00-00"         , "")
    return      if (value.isEmpty()) { "" }
    else if (value.isBlank()) { "" }
    else if (value == "null") { "" }
    else                      { value }
}

fun String.validation() : String {
    return      if (this.trim().isEmpty()) { "" }
    else if (this.trim().isBlank()) { "" }
    else if (this.trim() == "null") { "" }
    else                            { this.trim() }
}

fun String.getNow() : String {
    val ll = Locale("pt", "BR")
    val ff = when(this.trim()) {
        "dma"   -> "dd/MM/yyyy"
        "amd"   -> "yyyyMMdd"
        "a-m-d" -> "yyyy-MM-dd"
        else    -> "dd/MM/yyyy"
    }
    val format = SimpleDateFormat(ff, ll)
    var data     = ""
    GregorianCalendar(ll).also {
        it.timeInMillis = System.currentTimeMillis()
        data = format.format(it.time)
    }
    return data
}

fun String.getDoubleValue(): Double {
    val value = this
        .replace("R$", "")
        .replace("." , "")
        .trim()
    return if (value.contains(",") || value.contains("0")) {
        value.replace(",", ".").toDouble() } else { "0.00".toDouble() }
}

fun Double.setFormatDecimal(): String? {
    val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
    return DecimalFormat("###,###,##0.00", symbols).format(this)
}
