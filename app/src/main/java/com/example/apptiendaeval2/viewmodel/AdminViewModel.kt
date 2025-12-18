package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptiendaval2.network.ApiService
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaval2.events.ProductEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

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

    fun createProducto(
        nombre: String,
        descripcion: String,
        precio: Int,
        stock: Int,
        tallas: String,
        imagePart: okhttp3.MultipartBody.Part?,
        categoryId: Long? = 1L,  // Por defecto categoria 1 (Ropa)
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
                val descripcionBody = descripcion.toRequestBody("text/plain".toMediaTypeOrNull())
                val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockBody = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val tallasBody = tallas.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryIdBody = categoryId?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = apiService.createProducto(
                    nombre = nombreBody,
                    descripcion = descripcionBody,
                    precio = precioBody,
                    stock = stockBody,
                    tallasDisponibles = tallasBody,
                    imagen = imagePart,
                    categoryId = categoryIdBody
                )

                if (response.isSuccessful) {
                    fetchProductos()
                    viewModelScope.launch { ProductEvents.refresh.emit(Unit) }
                    onSuccess()
                } else {
                    _error.value = "Error al crear producto: ${response.code()} - ${response.message()}"
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

    fun updateProducto(
        id: Long,
        nombre: String,
        descripcion: String,
        precio: Int,
        stock: Int,
        tallas: String,
        imagePart: okhttp3.MultipartBody.Part?,
        categoryId: Long? = 1L,  // Por defecto categoria 1 (Ropa)
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
                val descripcionBody = descripcion.toRequestBody("text/plain".toMediaTypeOrNull())
                val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockBody = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val tallasBody = tallas.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryIdBody = categoryId?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = apiService.updateProducto(
                    id = id,
                    nombre = nombreBody,
                    descripcion = descripcionBody,
                    precio = precioBody,
                    stock = stockBody,
                    tallasDisponibles = tallasBody,
                    imagen = imagePart,
                    categoryId = categoryIdBody
                )

                if (response.isSuccessful) {
                    fetchProductos()
                    viewModelScope.launch { ProductEvents.refresh.emit(Unit) }
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    _error.value = "Error al actualizar: ${response.code()}\n${errorBody ?: response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar producto: ${e.message}\n${e.javaClass.simpleName}"
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
