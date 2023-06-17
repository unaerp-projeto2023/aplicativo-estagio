package com.example.finaldesafio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class VerVaga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vervaga)

        val button = findViewById<Button>(R.id.botao_voltarconsultavagas)


        button.setOnClickListener {
            val botao = Intent(this, Cadastro::class.java)
            startActivity(botao)
        }
    }
}