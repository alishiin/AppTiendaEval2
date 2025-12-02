package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptiendaeval2.model.UserResponse
import com.example.apptiendaval2.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val apiService: ApiService = ApiService.create()
) : ViewModel() {

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // LOGIN
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.login(mapOf("email" to email, "password" to password))
                if (response.isSuccessful && response.body() != null) {
                    _user.value = response.body()
                } else {
                    _error.value = "Email o contraseña incorrectos"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // REGISTER
    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.register(
                    mapOf(
                        "nombre" to nombre,
                        "email" to email,
                        "password" to password
                    )
                )
                if (response.isSuccessful && response.body() != null) {
                    _user.value = response.body()
                } else {
                    _error.value = "Error al registrar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
