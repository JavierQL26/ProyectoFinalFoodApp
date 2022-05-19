package com.jql.foodapp.models

import android.os.Parcel
import android.os.Parcelable

data class Restaurante(
    val nombre: String?, val direccion: String?, val costes_envio: String?,
    val imagen: String?, val horario: Horario?, var productos: List<Producto>?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Horario::class.java.classLoader),
        parcel.createTypedArrayList(Producto)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(costes_envio)
        parcel.writeString(imagen)
        parcel.writeParcelable(horario, flags)
        parcel.writeTypedList(productos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurante> {
        override fun createFromParcel(parcel: Parcel): Restaurante {
            return Restaurante(parcel)
        }

        override fun newArray(size: Int): Array<Restaurante?> {
            return arrayOfNulls(size)
        }
    }
}

data class Horario(
    val lunes: String?, val martes: String?, val miercoles: String?,
    val jueves: String?, val viernes: String?, val sabado: String?, val domingo: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lunes)
        parcel.writeString(martes)
        parcel.writeString(miercoles)
        parcel.writeString(jueves)
        parcel.writeString(viernes)
        parcel.writeString(sabado)
        parcel.writeString(domingo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Horario> {
        override fun createFromParcel(parcel: Parcel): Horario {
            return Horario(parcel)
        }

        override fun newArray(size: Int): Array<Horario?> {
            return arrayOfNulls(size)
        }
    }
}


data class Producto(val nombre: String?, val precio: Float, val url: String?, var totalProductosCarro: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeFloat(precio)
        parcel.writeString(url)
        parcel.writeInt(totalProductosCarro)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}