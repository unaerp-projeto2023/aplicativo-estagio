package com.example.finaldesafio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConsultaVagas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consultavagas)

        val button = findViewById<Button>(R.id.botao_vervaga)
        val perfil = findViewById<Button>(R.id.botaoPerfil)


        button.setOnClickListener {
            val botao = Intent(this, Cadastro::class.java)
            startActivity(botao)
        }



        perfil.setOnClickListener {
            val botao = Intent(this, Perfil::class.java)
            startActivity(botao)
        }
    }
}