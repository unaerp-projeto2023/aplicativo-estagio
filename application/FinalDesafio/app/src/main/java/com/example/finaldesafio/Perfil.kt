package com.example.finaldesafio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.finaldesafio.ui.features.AccountActivity

class Perfil:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfilusuario)

        val button = findViewById<Button>(R.id.botao_editar_perfil)
        val perfil = findViewById<Button>(R.id.buttonvoltar)
        val sair = findViewById<Button>(R.id.buttonsair)


        perfil.setOnClickListener {
            val botao = Intent(this, ConsultaVagas::class.java)
            startActivity(botao)
        }

        button.setOnClickListener {
            val botao = Intent(this, EditarPerfil::class.java)
            startActivity(botao)
        }

        sair.setOnClickListener {
            val botao = Intent(this, AccountActivity::class.java)
            startActivity(botao)
        }

    }
}