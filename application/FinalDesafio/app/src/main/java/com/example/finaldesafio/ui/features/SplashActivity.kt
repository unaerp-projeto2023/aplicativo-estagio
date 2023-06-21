package com.example.finaldesafio.ui.features

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.finaldesafio.R

import com.example.finaldesafio.repository.core.Constants
import com.example.finaldesafio.repository.extensions.rightToLeft

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_splash)
        load()
    }

    private fun load() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
                this@SplashActivity.rightToLeft()
            }
        }, SECONDS)
    }

    companion object {
        private const val SECONDS: Long = 2 * 1000
    }
}