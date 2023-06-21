package com.example.finaldesafio.ui.components

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.finaldesafio.R
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class DateDialog : DialogFragment() {
    private var year   = 0
    private var month  = 0
    private var day    = 0
    private val locale = Locale("pt", "BR")
    private var dateField: TextInputEditText? = null
    private var calendar: Calendar? = null
    private var df = SimpleDateFormat("dd/MM/yyyy", locale)

    override fun onCreateDialog(savedInstanceState: Bundle?) : DatePickerDialog =
        context?.let { DatePickerDialog(it, listener, year, month, day) }!!


    fun set() {
        calendar = GregorianCalendar(locale).also {
            it.timeInMillis = System.currentTimeMillis()
            it.get(Calendar.YEAR).also         { it1 -> year  = it1 }
            it.get(Calendar.MONTH).also        { it1 -> month = it1 }
            it.get(Calendar.DAY_OF_MONTH).also { it1 -> day   = it1 }
        }
    }

    fun set(date: String) {
        val vDate  = date.split("/")
        year       = vDate[2].toInt()
        month      = vDate[1].toInt() - 1
        day        = vDate[0].toInt()
        calendar   = GregorianCalendar(locale).also { it.set(year, month, day) }
    }

    /**
     * Seta o Listener
     */
    private val listener =
        OnDateSetListener { _, year, month, day ->
            calendar?.let {
                it.set(year, month, day)
                dateField?.setText(df.format(it.time))
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        calendar?.let { outState.putLong("cal", it.timeInMillis) }
        super.onSaveInstanceState(outState)
    }

    companion object {
        fun newInstance(dateField: TextInputEditText?) : DateDialog =
            DateDialog().also {
                it.dateField = dateField
            }
    }
}