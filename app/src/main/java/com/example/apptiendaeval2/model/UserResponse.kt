package com.example.apptiendaeval2.model

data class UserResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val password: String? = null,
    val rut: String,           // ✅ Campo RUT agregado
    val direccion: String,     // ✅ Campo Dirección agregado
    val comuna: String         // ✅ Campo Comuna agregado
)
