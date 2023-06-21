package com.example.finaldesafio.repository.extensions

import androidx.appcompat.app.AppCompatActivity
import com.example.finaldesafio.R

fun AppCompatActivity.leftToRight() {
    overridePendingTransition(R.anim.lefttoright, R.anim.stable)
}

fun AppCompatActivity.rightToLeft() {
    overridePendingTransition(R.anim.righttoleft, R.anim.stable)
}
