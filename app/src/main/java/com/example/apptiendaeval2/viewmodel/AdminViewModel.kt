package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptiendaval2.network.ApiService
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaval2.events.ProductEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel(
    private val apiService: ApiService = ApiService.create()
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
                val response = apiService.getAllAdminProductos()
                if (response.isSuccessful && response.body() != null) {
                    _productos.value = response.body()!!
                } else {
                    _error.value = "Error al obtener productos: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun createProducto(producto: Producto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.createProducto(producto)
                if (response.isSuccessful) {
                    fetchProductos() // refresca la lista
                    // Notificar a listeners que deben refrescar (ej: catálogo)
                    viewModelScope.launch { ProductEvents.refresh.emit(Unit) }
                    onSuccess()
                } else {
                    _error.value = "Error al crear producto: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProducto(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.deleteProducto(id)
                if (response.isSuccessful) {
                    fetchProductos()
                    viewModelScope.launch { ProductEvents.refresh.emit(Unit) }
                } else {
                    _error.value = "Error al eliminar producto: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateProducto(id: Long, producto: Producto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.updateProducto(id, producto)
                if (response.isSuccessful) {
                    fetchProductos()
                    viewModelScope.launch { ProductEvents.refresh.emit(Unit) }
                    onSuccess()
                } else {
                    _error.value = "Error al actualizar producto: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // Nuevo: obtener producto por id desde la lista en memoria
    fun getProductoById(id: Long): Producto? {
        return _productos.value.firstOrNull { it.id == id }
    }
}
