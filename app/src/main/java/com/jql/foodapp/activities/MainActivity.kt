package com.jql.foodapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jql.foodapp.adapter.ListaRestauranteAdapter
import com.jql.foodapp.models.Restaurante
import com.google.gson.Gson
import foodapp.R
import foodapp.databinding.ActivityMainBinding
import java.io.*


class MainActivity : AppCompatActivity(), ListaRestauranteAdapter.RestauranteListClickListener {
    private val enlace: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Restaurantes"

        val restauranteModel = getListaRestaurantes()
        initRecyclerView(restauranteModel)
    }

    private fun initRecyclerView(listaRestaurante: List<Restaurante>) {
        val recyclerViewRestaurant = enlace.recyclerViewRestaurant
        recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
        val adapter = ListaRestauranteAdapter(listaRestaurante, this)
        recyclerViewRestaurant.adapter =adapter
    }

    private fun getListaRestaurantes(): List<Restaurante> {
        val inputStream: InputStream = resources.openRawResource(R.raw.restaurante)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n : Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)

            }

        }catch (e: Exception){}
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val restauranteModel = gson.fromJson(jsonStr, Array<Restaurante>::class.java).toList()

        return restauranteModel
    }

    override fun onItemClick(restaurante: Restaurante) {
       val intent = Intent(this, ProductosRestauranteActivity::class.java)
        intent.putExtra("Restaurante", restaurante)
        startActivity(intent)
    }
}