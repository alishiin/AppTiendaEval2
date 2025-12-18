package com.example.apptiendaeval2.model

import com.google.gson.annotations.SerializedName

data class Producto(
    val id: Long? = null,
    val nombre: String? = null,
    val precio: Int? = null,  // ✅ INT como espera la API
    val descripcion: String? = null,
    val stock: Int? = null,  // ✅ Stock agregado

    @SerializedName("tallasDisponibles")
    val tallasDisponibles: String? = null,  // ✅ Como String "S,M,L,XL"

    @SerializedName("categoryId")
    val categoryId: Long? = null,

    @SerializedName("categoryName")
    val categoryName: String? = null,

    @SerializedName("hasImagen")
    val hasImagen: Boolean? = null,

    // ✅ CAMPOS ADICIONALES PARA APP
    val categoria: String? = null,
    val imagenUrl: String? = null,
    val imagenesUrl: List<String> = emptyList(),

    val valoraciones: List<Valoracion> = emptyList(),
    val tallas: List<String> = listOf("S","M","L","XL"),
    val medidas: List<String> = emptyList()
)
