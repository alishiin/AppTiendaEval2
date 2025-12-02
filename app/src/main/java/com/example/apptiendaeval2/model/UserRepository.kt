package com.example.apptiendaeval2.repository

import com.example.apptiendaeval2.model.UserResponse
import com.example.apptiendaval2.network.ApiService

class UserRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): UserResponse {
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        return api.login(body).body()!!
    }

    suspend fun register(nombre: String, email: String, password: String): UserResponse {
        val body = mapOf(
            "nombre" to nombre,
            "email" to email,
            "password" to password
        )
        return api.register(body).body()!!
    }
}
