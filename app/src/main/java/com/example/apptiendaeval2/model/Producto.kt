package com.example.apptiendaval2.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagenResId: Int,
    val imagenesResId: List<Int> = emptyList(),
    val valoraciones: List<Valoracion> = emptyList()
)

