package com.jql.foodapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jql.foodapp.models.Restaurante
import com.jql.foodapp.models.Usuario
import foodapp.databinding.ActivityFinBinding


class FinActivity : AppCompatActivity() {
    private val enlace: ActivityFinBinding by lazy{
        ActivityFinBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)




        val restaurante: Restaurante? = intent.getSerializableExtra("Restaurante") as Restaurante?
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurante?.nombre
        actionbar?.subtitle = restaurante?.direccion
        actionbar?.setDisplayHomeAsUpEnabled(false)


        enlace.botonAceptar.setOnClickListener {
            setResult(RESULT_OK)
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}