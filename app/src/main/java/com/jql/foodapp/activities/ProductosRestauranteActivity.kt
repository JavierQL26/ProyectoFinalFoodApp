package com.jql.foodapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.jql.foodapp.adapter.ProductosRestauranteAdapter
import com.jql.foodapp.models.Producto
import com.jql.foodapp.models.Restaurante
import foodapp.databinding.ActivityProductosRestauranteBinding

class ProductosRestauranteActivity : AppCompatActivity(), ProductosRestauranteAdapter.ProductoListClickListener {
    private val enlace: ActivityProductosRestauranteBinding by lazy{
        ActivityProductosRestauranteBinding.inflate(layoutInflater)
    }
    private var listaProductosCarro: MutableList<Producto>? = null
    private var totalProductosCarro = 0
    private lateinit var listaProductos: List<Producto>
    private lateinit var productosRestauranteAdapter: ProductosRestauranteAdapter

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        val restauranteModel = intent?.getSerializableExtra("Restaurante") as Restaurante?

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = restauranteModel?.nombre
        actionBar?.subtitle = restauranteModel?.direccion
        actionBar?.setDisplayHomeAsUpEnabled(true)

        listaProductos = restauranteModel?.productos!!

        initRecyclerView(listaProductos)
        enlace.botonConfirmar.setOnClickListener {
            if(listaProductosCarro == null || listaProductosCarro!!.size == 0) {
                Toast.makeText(this, "Selecciona al menos un producto para continuar", Toast.LENGTH_LONG).show()
            }
            else {
                restauranteModel.productos = listaProductosCarro
                val intent = Intent(this, PedidoActivity::class.java)
                intent.putExtra("Restaurante", restauranteModel)
                getResult.launch(intent)
            }
        }

    }
    private fun initRecyclerView(productos: List<Producto>) {
        enlace.productosRecyclerView.layoutManager = GridLayoutManager(this, 2)
        productosRestauranteAdapter = ProductosRestauranteAdapter(productos, this)
        enlace.productosRecyclerView.adapter =productosRestauranteAdapter
    }

    override fun agregarAlCarroClickListener(producto: Producto) {
        if(listaProductosCarro == null) {
            listaProductosCarro = ArrayList()
        }
        listaProductosCarro?.add(producto)
        totalProductosCarro = 0
        for(p in listaProductosCarro!!) {
            totalProductosCarro += p.totalProductosCarro
        }
        enlace.botonConfirmar.text = "Confirmar (" + totalProductosCarro +") productos"

    }

    override fun actualizarCarroClickListener(producto: Producto) {
        val index = listaProductosCarro!!.indexOf(producto)
        listaProductosCarro!!.removeAt(index)
        listaProductosCarro!!.add(producto)
        totalProductosCarro = 0
        for(p in listaProductosCarro!!) {
            totalProductosCarro += p.totalProductosCarro
        }
        enlace.botonConfirmar.text = "Confirmar (" + totalProductosCarro +") productos"
    }

    override fun elimnarDelCarroClickListener(producto: Producto) {
        if(listaProductosCarro!!.contains(producto)) {
            listaProductosCarro?.remove(producto)
            totalProductosCarro = 0
            for(p in listaProductosCarro!!) {
                totalProductosCarro += p.totalProductosCarro
            }
            enlace.botonConfirmar.text = "Confirmar (" + totalProductosCarro +") productos"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}