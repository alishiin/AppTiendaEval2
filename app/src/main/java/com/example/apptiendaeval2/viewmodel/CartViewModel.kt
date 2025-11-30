package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaval2.viewmodel.CartViewModel

data class CartItem(
    val producto: Producto,
    val cantidad: Int = 1,
    val talla: String = "S" // Talla o medida seleccionada
)

class CartViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items

    fun addProduct(producto: Producto, talla: String = "S") {
        val current = _items.value.toMutableList()
        // Buscar por producto ID Y talla
        val index = current.indexOfFirst { it.producto.id == producto.id && it.talla == talla }
        if (index != -1) {
            val existing = current[index]
            current[index] = existing.copy(cantidad = existing.cantidad + 1)
        } else {
            current.add(CartItem(producto, 1, talla))
        }
        _items.value = current
    }

    fun updateQuantity(productId: Int, talla: String, cantidad: Int) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst { it.producto.id == productId && it.talla == talla }
        if (index != -1) {
            if (cantidad <= 0) current.removeAt(index)
            else current[index] = current[index].copy(cantidad = cantidad)
        }
        _items.value = current
    }

    fun removeProduct(productId: Int, talla: String) {
        val current = _items.value.toMutableList()
        current.removeAll { it.producto.id == productId && it.talla == talla }
        _items.value = current
    }

    fun removeOneItem(productId: Int, talla: String) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst { it.producto.id == productId && it.talla == talla }
        if (index != -1) {
            val item = current[index]
            if (item.cantidad > 1) {
                current[index] = item.copy(cantidad = item.cantidad - 1)
            } else {
                current.removeAt(index)
            }
        }
        _items.value = current
    }

    fun clear() {
        _items.value = emptyList()
    }
}
