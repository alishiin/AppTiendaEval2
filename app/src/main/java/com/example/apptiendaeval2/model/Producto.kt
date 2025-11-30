package com.example.apptiendaeval2.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagenResId: Int,
    val categoria: Categoria,
    val imagenesResId: List<Int> = emptyList(),
    val valoraciones: List<Valoracion> = emptyList(),
    val tallas: List<String> = listOf("S","M","L","XL"),
    val medidas: List<String> = emptyList()
)
