package com.example.apptiendaval2.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagenResId: Int, // Cambiado de String a Int
    val imagenesResId: List<Int> = emptyList(), // Lista de imágenes locales
    val valoraciones: List<Valoracion> = emptyList()
)

