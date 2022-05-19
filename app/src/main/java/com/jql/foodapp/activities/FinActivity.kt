package com.jql.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.jql.foodapp.models.Restaurante
import foodapp.databinding.ActivityFinBinding

class FinActivity : AppCompatActivity() {
    private val enlace: ActivityFinBinding by lazy{
        ActivityFinBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        val restaurante: Restaurante? = intent.getParcelableExtra("Restaurante")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurante?.nombre
        actionbar?.subtitle = restaurante?.direccion
        actionbar?.setDisplayHomeAsUpEnabled(false)

        enlace.botonAceptar.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}