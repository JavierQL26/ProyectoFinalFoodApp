package com.jql.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jql.foodapp.models.Producto
import foodapp.R

class ListaProductoAdapter(private val listaProducto: List<Producto>, val clickListener: ProductoListClickListener): RecyclerView.Adapter<ListaProductoAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaProductoAdapter.Holder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.linea_lista_producto, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: ListaProductoAdapter.Holder, position: Int) {
        holder.bind(listaProducto[position])
    }

    override fun getItemCount(): Int {
        return listaProducto.size
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        private var imagenRestaurante: ImageView = view.findViewById(R.id.imagen)
        val nombre: TextView = view.findViewById(R.id.nombre)
        private val precio: TextView = view.findViewById(R.id.precio)
        private val anadirAlCarro: TextView = view.findViewById(R.id.anadirAlCarro)
        private val anadirProducto: LinearLayout = view.findViewById(R.id.anadirProductoLayout)
        private val restarProducto: ImageView = view.findViewById(R.id.restarProducto)
        private val sumarProducto: ImageView = view.findViewById(R.id.sumarProducto)
        private val tvContador: TextView = view.findViewById(R.id.tvContador)

        fun bind(producto: Producto) {
            nombre.text = producto.nombre
            precio.text = "Precio: ${producto?.precio} €"
            anadirAlCarro.setOnClickListener {
                producto.totalProductosCarro = 1
                clickListener.agregarAlCarroClickListener(producto)
                anadirProducto.visibility = View.VISIBLE
                anadirAlCarro.visibility = View.GONE
                tvContador.text = producto.totalProductosCarro.toString()
            }
            restarProducto.setOnClickListener {
                var total = producto.totalProductosCarro
                total--
                if(total > 0) {
                    producto.totalProductosCarro = total
                    clickListener.actualizarCarroClickListener(producto)
                    tvContador.text = producto.totalProductosCarro.toString()
                } else {
                    producto.totalProductosCarro = total
                    clickListener.elimnarDelCarroClickListener(producto)
                    anadirProducto.visibility = View.GONE
                    anadirAlCarro.visibility = View.VISIBLE
                }
            }
            sumarProducto.setOnClickListener {
                var total: Int  = producto.totalProductosCarro
                total++
                if(total <= 10) {
                    producto.totalProductosCarro = total
                    clickListener.actualizarCarroClickListener(producto)
                    tvContador.text = total.toString()
                }
            }

            Glide.with(imagenRestaurante)
                .load(producto.url)
                .into(imagenRestaurante)
        }
    }

    interface ProductoListClickListener {
        fun agregarAlCarroClickListener(producto: Producto)
        fun actualizarCarroClickListener(producto: Producto)
        fun elimnarDelCarroClickListener(producto: Producto)
    }
}