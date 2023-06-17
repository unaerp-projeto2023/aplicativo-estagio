package com.example.finaldesafio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EditarPerfil: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarperfil)

        val button = findViewById<Button>(R.id.botao_voltar)
        val editarPerfil = findViewById<Button>(R.id.botao_editar)


        editarPerfil.setOnClickListener {
            val botao = Intent(this, Perfil::class.java)
            startActivity(botao)
        }

        button.setOnClickListener {
            val botao = Intent(this, Perfil::class.java)
            startActivity(botao)
        }




    }
}