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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jql.foodapp.adapter.PedidoAdapter
import com.jql.foodapp.models.Pedido
import com.jql.foodapp.models.Restaurante
import com.jql.foodapp.models.Usuario
import foodapp.databinding.ActivityPedidoBinding

class PedidoActivity : AppCompatActivity() {
    private val enlace: ActivityPedidoBinding by lazy{
        ActivityPedidoBinding.inflate(layoutInflater)
    }
    private val db = FirebaseFirestore.getInstance()
    private var pedidoAdapter: PedidoAdapter? = null
    private var alDomicilio: Boolean = true
    private var subTotalPrecio = 0f

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

        val restaurante : Restaurante? = intent.getSerializableExtra("Restaurante") as Restaurante?
        val actionbar: ActionBar? = supportActionBar

        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val emailUsuario = auth.currentUser?.email

        lateinit var nombre : String
        lateinit var direccion : String
        lateinit var telefono :String
        lateinit var apellidos : String

        actionbar?.title = restaurante?.nombre
        actionbar?.subtitle = restaurante?.direccion
        actionbar?.setDisplayHomeAsUpEnabled(true)


        db.collection("Usuario").document(emailUsuario!!).get().addOnSuccessListener {
            nombre = it.get("usuario.nombre") as String
            direccion = it.get("usuario.direccion") as String
            telefono = it.get("usuario.telefono") as String
            apellidos = it.get("usuario.apellidos") as String
        }

        enlace.botonConfimarPedido.setOnClickListener {
            val usuario = Usuario(nombre,apellidos, direccion, emailUsuario, telefono)
            val pedido = if(alDomicilio){
                Pedido(restaurante?.productos!!.toList(),restaurante.costesEnvio, String.format("%.2f",
                        subTotalPrecio))
                }else{
                    Pedido(
                        restaurante?.productos!!.toList(), String.format("%.2f",
                            subTotalPrecio))
                }
            db.collection("Pedido").document().set(
                hashMapOf( "usuario" to usuario,
                    "detallePedido" to pedido)
            )
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
        subTotalPrecio = 0f
        for(producto in restaurante?.productos!!) {
            subTotalPrecio += producto.precio?.toFloat()!! * producto.totalProductosCarro
        }
        enlace.tvSubtotalPrecio.text = String.format("%.2f €", subTotalPrecio)
        if(alDomicilio) {
            enlace.tvCostesDeEnvioPrecio.text = String.format("%.2f €", restaurante.costesEnvio?.toFloat())
            subTotalPrecio += restaurante.costesEnvio?.toFloat()!!
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