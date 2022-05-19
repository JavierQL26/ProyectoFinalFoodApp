package com.jql.foodapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.jql.foodapp.adapter.PedidoAdapter
import com.jql.foodapp.models.Restaurante
import foodapp.databinding.ActivityPedidoBinding

class PedidoActivity : AppCompatActivity() {
    private val enlace: ActivityPedidoBinding by lazy{
        ActivityPedidoBinding.inflate(layoutInflater)
    }
    private var pedidoAdapter: PedidoAdapter? = null
    private var alDomicilio: Boolean = true

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

        val restaurante: Restaurante? = intent.getParcelableExtra("Restaurante")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurante?.nombre
        actionbar?.subtitle = restaurante?.direccion
        actionbar?.setDisplayHomeAsUpEnabled(true)

        enlace.botonConfimarPedido.setOnClickListener {
            botonConfimarPedidoButtonCLick(restaurante)
        }

        enlace.switchOpcionPedido.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked) {
                enlace.tvCostesDeEnvio.visibility = View.GONE
                enlace.tvCostesDeEnvioPrecio.visibility = View.GONE
                alDomicilio = false
                calcularPrecio(restaurante)
            } else {
                enlace.tvCostesDeEnvio.visibility = View.VISIBLE
                enlace.tvCostesDeEnvioPrecio.visibility = View.VISIBLE
                alDomicilio = true
                calcularPrecio(restaurante)
            }
        }

        initRecyclerView(restaurante)
        calcularPrecio(restaurante)
    }

    private fun initRecyclerView(restaurante: Restaurante?) {
        enlace.productosCarroRecyclerView.layoutManager = LinearLayoutManager(this)
        pedidoAdapter = PedidoAdapter(restaurante?.productos)
        enlace.productosCarroRecyclerView.adapter =pedidoAdapter
    }

    private fun calcularPrecio(restaurante: Restaurante?) {
        var subTotalPrecio = 0f
        for(producto in restaurante?.productos!!) {
            subTotalPrecio += producto.precio * producto.totalProductosCarro

        }
        enlace.tvSubtotalPrecio.text = String.format("%.2f €", subTotalPrecio)
        if(alDomicilio) {
            enlace.tvCostesDeEnvioPrecio.text = String.format("%.2f €", restaurante.costes_envio?.toFloat())
            subTotalPrecio += restaurante.costes_envio?.toFloat()!!
        }

        enlace.tvTotalPrecio.text = String.format("%.2f €", subTotalPrecio)
    }

    private fun botonConfimarPedidoButtonCLick(restaurante: Restaurante?) {
        val intent = Intent(this, FinActivity::class.java)
        intent.putExtra("Restaurante", restaurante)
        getResult.launch(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}