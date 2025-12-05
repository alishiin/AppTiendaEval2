package com.example.apptiendaeval2

import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaeval2.model.Categoria
import com.example.apptiendaval2.viewmodel.CartViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para CartViewModel
 * Valida operaciones CRUD del carrito de compras
 */
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private lateinit var productoTest: Producto

    @Before
    fun setup() {
        viewModel = CartViewModel()
        productoTest = Producto(
            id = 1L,
            nombre = "Camiseta Dragon",
            precio = 15990,
            descripcion = "Camiseta con diseño de dragón",
            imagenUrl = "https://example.com/imagen.jpg",
            categoria = Categoria.POLERAS
        )
    }

    @Test
    fun `test agregar producto al carrito`() {
        viewModel.addProduct(productoTest, "M")

        val items = viewModel.items.value
        assertEquals("El carrito debe tener 1 item", 1, items.size)
        assertEquals("El producto debe ser el correcto", productoTest.id, items[0].producto.id)
        assertEquals("La talla debe ser M", "M", items[0].talla)
    }

    @Test
    fun `test agregar mismo producto aumenta cantidad`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.addProduct(productoTest, "M")

        val items = viewModel.items.value
        assertEquals("El carrito debe tener 1 item", 1, items.size)
        assertEquals("La cantidad debe ser 2", 2, items[0].cantidad)
    }

    @Test
    fun `test agregar mismo producto con diferentes tallas`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.addProduct(productoTest, "L")

        val items = viewModel.items.value
        assertEquals("El carrito debe tener 2 items", 2, items.size)
    }

    @Test
    fun `test remover producto del carrito`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.removeProduct(productoTest.id, "M")

        val items = viewModel.items.value
        assertEquals("El carrito debe estar vacío", 0, items.size)
    }

    @Test
    fun `test actualizar cantidad de producto`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.updateQuantity(productoTest.id, "M", 5)

        val items = viewModel.items.value
        assertEquals("La cantidad debe ser 5", 5, items[0].cantidad)
    }

    @Test
    fun `test remover un item cuando cantidad es mayor a 1`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.addProduct(productoTest, "M")
        viewModel.removeOneItem(productoTest.id, "M")

        val items = viewModel.items.value
        assertEquals("El carrito debe tener 1 item", 1, items.size)
        assertEquals("La cantidad debe ser 1", 1, items[0].cantidad)
    }

    @Test
    fun `test limpiar carrito`() {
        viewModel.addProduct(productoTest, "M")
        viewModel.setPaymentMethod("Tarjeta")
        viewModel.clear()

        val items = viewModel.items.value
        val paymentMethod = viewModel.paymentMethod.value

        assertEquals("El carrito debe estar vacío", 0, items.size)
        assertEquals("El método de pago debe estar limpio", "", paymentMethod)
    }

    @Test
    fun `test setShippingData guarda los datos correctamente`() {
        viewModel.setShippingData(
            nombre = "Juan Pérez",
            direccion = "Av. Principal 123",
            ciudad = "Santiago",
            comuna = "Las Condes",
            telefono = "912345678"
        )

        val shippingData = viewModel.shippingData.value
        assertEquals("Juan Pérez", shippingData["nombre"])
        assertEquals("Av. Principal 123", shippingData["direccion"])
        assertEquals("Santiago", shippingData["ciudad"])
        assertEquals("Las Condes", shippingData["comuna"])
        assertEquals("912345678", shippingData["telefono"])
    }

    @Test
    fun `test setPaymentMethod guarda el metodo de pago`() {
        viewModel.setPaymentMethod("Tarjeta de Crédito")
        assertEquals("Tarjeta de Crédito", viewModel.paymentMethod.value)
    }

    @Test
    fun `test setPaymentAmount guarda el monto`() {
        viewModel.setPaymentAmount(25000)
        assertEquals(25000, viewModel.paymentAmount.value)
    }

    @Test
    fun `test selectProduct guarda el producto seleccionado`() {
        viewModel.selectProduct(productoTest)
        assertEquals(productoTest, viewModel.selectedProduct.value)
    }
}

