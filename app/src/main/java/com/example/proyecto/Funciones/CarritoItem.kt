package com.example.proyecto.Funciones

data class CarritoItem(
    val nombre: String,
    val tipo: String,
    val precioClp: Double,
    val cantidad: Int = 1,
    val id: Long? = null
)

