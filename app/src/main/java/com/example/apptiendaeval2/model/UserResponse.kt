package com.example.apptiendaeval2.model

data class UserResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val password: String? = null,
    val rut: String,
    val direccion: String,
    val comuna: String,
    val region: String? = null,    // ✅ Campo Región agregado (opcional)
    val rol: String = "USER"       // ✅ Campo Rol (coincide con backend)
)
