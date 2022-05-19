package com.jql.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import foodapp.R
import com.jql.foodapp.models.Horario
import com.jql.foodapp.models.Restaurante
import java.text.SimpleDateFormat
import java.util.*

class ListaRestauranteAdapter(private val listaRestaurante: List<Restaurante>, private val clickListener: RestauranteListClickListener): RecyclerView.Adapter<ListaRestauranteAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaRestauranteAdapter.Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.linea_lista_restaurante, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: ListaRestauranteAdapter.Holder, position: Int) {
        holder.bind(listaRestaurante[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listaRestaurante[position])
        }
    }

    override fun getItemCount(): Int {
        return listaRestaurante.size
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val imagen: ImageView = view.findViewById(R.id.imagen)
        private val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        private val tvDireccion: TextView = view.findViewById(R.id.tvDireccion)
        private val tvHorario: TextView = view.findViewById(R.id.tvHorario)

        fun bind(restaurante: Restaurante?) {
            tvNombre.text = restaurante?.nombre
            tvDireccion.text = "Dirección: "+restaurante?.direccion
            tvHorario.text = "Horario: " + getHorario(restaurante?.horario!!)

            Glide.with(imagen)
                .load(restaurante?.imagen)
                .into(imagen)
        }
    }

    private fun getHorario(horario: Horario): String? {
        val calendario : Calendar =  Calendar.getInstance()
        val fecha : Date = calendario.time
        val dia : String = SimpleDateFormat("EEEE", Locale.forLanguageTag("ES")).format(fecha.time)
        return when(dia) {
            "lunes" -> horario.lunes
            "martes" -> horario.martes
            "miercoles" -> horario.miercoles
            "jueves" -> horario.jueves
            "viernes" -> horario.viernes
            "sabado" -> horario.sabado
            "domingo" -> horario.domingo
            else -> horario.domingo
        }
    }

    interface RestauranteListClickListener {
        fun onItemClick(restaurante: Restaurante)
    }

}