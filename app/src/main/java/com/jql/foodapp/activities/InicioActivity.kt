package com.jql.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.ActionBar
import foodapp.databinding.ActivityInicioBinding

class InicioActivity : AppCompatActivity() {
    private val enlace: ActivityInicioBinding by lazy{
        ActivityInicioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        //Esconder actionBar en la bienvenida
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        //Bienvenida a la app con un delay de 2 segundos.
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@InicioActivity,LogInActivity::class.java))
            finish()
        },2000)
    }
}