package com.example.finaldesafio.repository.extensions

import android.view.View
import android.widget.EditText
import androidx.annotation.IdRes

import com.google.android.material.textfield.TextInputEditText

fun View.editText(@IdRes res: Int) : EditText = this.findViewById(res)

fun EditText.editTableText() : String = this.editableText.toString().trim()
fun TextInputEditText.editTableText() : String = this.editableText.toString().trim()
