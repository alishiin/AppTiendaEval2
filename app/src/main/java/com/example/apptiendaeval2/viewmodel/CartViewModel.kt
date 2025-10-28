package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.apptiendaval2.model.Producto
import com.example.apptiendaval2.viewmodel.CartViewModel

data class CartItem(val producto: Producto, val cantidad: Int = 1)

class CartViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items

    fun addProduct(producto: Producto) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst { it.producto.id == producto.id }
        if (index != -1) {
            val existing = current[index]
            current[index] = existing.copy(cantidad = existing.cantidad + 1)
        } else {
            current.add(CartItem(producto, 1))
        }
        _items.value = current
    }

    fun updateQuantity(productId: Int, cantidad: Int) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst { it.producto.id == productId }
        if (index != -1) {
            if (cantidad <= 0) current.removeAt(index)
            else current[index] = current[index].copy(cantidad = cantidad)
        }
        _items.value = current
    }

    fun clear() {
        _items.value = emptyList()
    }
}
