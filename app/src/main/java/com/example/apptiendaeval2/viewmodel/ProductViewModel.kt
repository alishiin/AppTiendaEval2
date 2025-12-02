package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaval2.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val apiService: ApiService = ApiService.create() // usamos tu RetrofitClient
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchProductos() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    _productos.value = response.body() ?: emptyList()
                    println("Productos recibidos: ${_productos.value}") // 👈 Para debug
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
                println("Error al obtener productos: $e")
            } finally {
                _loading.value = false
            }
        }
    }
}
