package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.apptiendaeval2.model.Producto
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

// --------------------
// ITEM DEL CARRITO
// --------------------
data class CartItem(
    val producto: Producto,
    val cantidad: Int = 1,
    val talla: String = "S"
)

// --------------------
// VIEWMODEL PRINCIPAL
// --------------------
class CartViewModel : ViewModel() {

    // 🛒 CARRITO
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items

    // ✅ PRODUCTO SELECCIONADO PARA DETAILS
    private val _selectedProduct = mutableStateOf<Producto?>(null)
    val selectedProduct: State<Producto?> = _selectedProduct

    fun selectProduct(producto: Producto) {
        _selectedProduct.value = producto
    }

    // 📦 DATOS DE ENVÍO
    private val _shippingData = mutableStateOf<Map<String, String>>(emptyMap())
    val shippingData: State<Map<String, String>> = _shippingData

    // 💳 MÉTODO DE PAGO
    private val _paymentMethod = mutableStateOf("")
    val paymentMethod: State<String> = _paymentMethod

    // 💰 MONTO PAGADO
    private val _paymentAmount = mutableStateOf(0)
    val paymentAmount: State<Int> = _paymentAmount

    fun setShippingData(nombre: String, direccion: String, ciudad: String, comuna: String, telefono: String) {
        _shippingData.value = mapOf(
            "nombre" to nombre,
            "direccion" to direccion,
            "ciudad" to ciudad,
            "comuna" to comuna,
            "telefono" to telefono
        )
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun setPaymentAmount(amount: Int) {
        _paymentAmount.value = amount
    }

    // --------------------
    // CRUD CARRITO
    // --------------------
    fun addProduct(producto: Producto, talla: String = "S") {
        val current = _items.value.toMutableList()

        val index = current.indexOfFirst {
            it.producto.id == producto.id && it.talla == talla
        }

        if (index != -1) {
            val existing = current[index]
            current[index] = existing.copy(cantidad = existing.cantidad + 1)
        } else {
            current.add(CartItem(producto, 1, talla))
        }

        _items.value = current
    }

    // Cambié los tipos de productId de Int a Long? para coincidir con Producto.id
    fun updateQuantity(productId: Long?, talla: String, cantidad: Int) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst {
            it.producto.id == productId && it.talla == talla
        }

        if (index != -1) {
            if (cantidad <= 0) current.removeAt(index)
            else current[index] = current[index].copy(cantidad = cantidad)
        }

        _items.value = current
    }

    fun removeProduct(productId: Long?, talla: String) {
        val current = _items.value.toMutableList()
        current.removeAll {
            it.producto.id == productId && it.talla == talla
        }
        _items.value = current
    }

    fun removeOneItem(productId: Long?, talla: String) {
        val current = _items.value.toMutableList()
        val index = current.indexOfFirst {
            it.producto.id == productId && it.talla == talla
        }

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
        _shippingData.value = emptyMap()
        _paymentMethod.value = ""
        _paymentAmount.value = 0
    }
}
