package com.example.apptiendaeval2.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,

    // ✅ IMAGEN DESDE API (URL)
    val imagenUrl: String,

    val categoria: Categoria,

    // ✅ MÚLTIPLES IMÁGENES POR URL (SI QUIERES GALERÍA)
    val imagenesUrl: List<String> = emptyList(),

    val valoraciones: List<Valoracion> = emptyList(),
    val tallas: List<String> = listOf("S","M","L","XL"),
    val medidas: List<String> = emptyList()
)
