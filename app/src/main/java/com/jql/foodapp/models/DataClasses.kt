package com.jql.foodapp.models


import java.io.Serializable

data class Restaurante(val nombre: String?, val direccion: String?, var costesEnvio: String?,
                       val imagen: String?, val horario: Horario?, var productos: List<Producto>?) : Serializable

data class Horario(val lunes: String?, val martes: String?, val miercoles: String?,
    val jueves: String?, val viernes: String?, val sabado: String?, val domingo: String?) : Serializable

data class Producto(val nombre: String?, val precio: String?, val url: String?, var totalProductosCarro: Int) : Serializable

data class Usuario(val nombre:String?,val apellidos:String?,val direccion: String?,val email:String?, val telefono:String?):Serializable

data class Pedido(val productos: List<Producto>?,val costesEnvio: String?,val precioTotal: String?){
    constructor(productos: List<Producto>?,precioTotal: String?) : this(productos,"0",precioTotal) {
    }
}
