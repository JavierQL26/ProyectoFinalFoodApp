package com.jql.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jql.foodapp.models.Producto
import foodapp.R

class PedidoAdapter(private val listaProducto: List<Producto>?): RecyclerView.Adapter<PedidoAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoAdapter.Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.linea_producto_pedido, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: PedidoAdapter.Holder, position: Int) {
        holder.bind(listaProducto?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return listaProducto?.size ?: return 0
        //return if(listaProducto == null)return 0 else listaProducto.size
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val imagenProducto: ImageView = view.findViewById(R.id.imagen)
        private val nombre: TextView = view.findViewById(R.id.nombre)
        private val precio: TextView = view.findViewById(R.id.precio)
        private val cantidad: TextView = view.findViewById(R.id.cantidad)

        fun bind(producto: Producto) {
            nombre.text = producto.nombre!!
            precio.text = "Precio: " + String.format("%.2f €", producto.precio?.toFloat()!! * producto.totalProductosCarro)
            cantidad.text = "Cantidad :" + producto.totalProductosCarro

            Glide.with(imagenProducto)
                .load(producto.url)
                .into(imagenProducto)
        }
    }
}