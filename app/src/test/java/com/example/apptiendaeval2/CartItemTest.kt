package com.example.apptiendaeval2

import com.example.apptiendaval2.viewmodel.CartItem
import com.example.apptiendaeval2.model.Producto
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para CartItem
 * Valida la estructura y comportamiento del data class CartItem
 */
class CartItemTest {

    private val productoTest = Producto(
        id = 1L,
        nombre = "Camiseta Test",
        precio = 15000,
        descripcion = "Camiseta de prueba"
    )

    @Test
    fun `test crear CartItem con valores por defecto`() {
        val cartItem = CartItem(producto = productoTest)

        assertEquals(productoTest, cartItem.producto)
        assertEquals(1, cartItem.cantidad)
        assertEquals("S", cartItem.talla)
    }

    @Test
    fun `test crear CartItem con cantidad personalizada`() {
        val cartItem = CartItem(
            producto = productoTest,
            cantidad = 5,
            talla = "L"
        )

        assertEquals(5, cartItem.cantidad)
        assertEquals("L", cartItem.talla)
    }

    @Test
    fun `test copy de CartItem aumentando cantidad`() {
        val cartItemOriginal = CartItem(producto = productoTest, cantidad = 1)
        val cartItemModificado = cartItemOriginal.copy(cantidad = 3)

        assertEquals(1, cartItemOriginal.cantidad)
        assertEquals(3, cartItemModificado.cantidad)
    }

    @Test
    fun `test copy de CartItem cambiando talla`() {
        val cartItemOriginal = CartItem(producto = productoTest, talla = "S")
        val cartItemModificado = cartItemOriginal.copy(talla = "XL")

        assertEquals("S", cartItemOriginal.talla)
        assertEquals("XL", cartItemModificado.talla)
    }

    @Test
    fun `test igualdad de CartItems con mismo producto y talla`() {
        val cartItem1 = CartItem(producto = productoTest, cantidad = 2, talla = "M")
        val cartItem2 = CartItem(producto = productoTest, cantidad = 2, talla = "M")

        assertEquals(cartItem1, cartItem2)
    }

    @Test
    fun `test calcular subtotal de CartItem`() {
        val cartItem = CartItem(
            producto = productoTest.copy(precio = 10000),
            cantidad = 3
        )

        val subtotal = (cartItem.producto.precio ?: 0) * cartItem.cantidad
        assertEquals(30000, subtotal)
    }
}

