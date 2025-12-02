package com.example.apptiendaeval2.model

data class Producto(
    val id: Long? = null,
    val nombre: String? = null,
    val precio: Int? = null,
    val descripcion: String? = null,

    // ✅ IMAGEN DESDE API (URL)
    val imagenUrl: String? = null,

    val categoria: Categoria? = null,

    // ✅ MÚLTIPLES IMÁGENES POR URL (SI QUIERES GALERÍA)
    val imagenesUrl: List<String> = emptyList(),

    val valoraciones: List<Valoracion> = emptyList(),
    val tallas: List<String> = listOf("S","M","L","XL"),
    val medidas: List<String> = emptyList()
)
