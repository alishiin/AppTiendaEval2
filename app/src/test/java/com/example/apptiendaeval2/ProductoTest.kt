package com.example.apptiendaeval2

import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaeval2.model.Categoria
import com.example.apptiendaeval2.model.Valoracion
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para el modelo Producto
 * Valida la estructura y comportamiento del data class Producto
 */
class ProductoTest {

    @Test
    fun `test crear producto con todos los campos`() {
        val producto = Producto(
            id = 1L,
            nombre = "Camiseta Test",
            precio = 10000,
            descripcion = "Descripción de prueba",
            imagenUrl = "https://example.com/imagen.jpg",
            categoria = Categoria.POLERAS,
            imagenesUrl = listOf("url1", "url2"),
            tallas = listOf("S", "M", "L"),
            medidas = listOf("Ancho: 50cm", "Largo: 70cm")
        )

        assertEquals(1L, producto.id)
        assertEquals("Camiseta Test", producto.nombre)
        assertEquals(10000, producto.precio)
        assertEquals("Descripción de prueba", producto.descripcion)
        assertEquals("https://example.com/imagen.jpg", producto.imagenUrl)
        assertEquals(3, producto.tallas.size)
    }

    @Test
    fun `test producto con valores por defecto`() {
        val producto = Producto()

        assertNull(producto.id)
        assertNull(producto.nombre)
        assertNull(producto.precio)
        assertEquals(emptyList<String>(), producto.imagenesUrl)
        assertEquals(emptyList<Valoracion>(), producto.valoraciones)
    }

    @Test
    fun `test producto con tallas por defecto`() {
        val producto = Producto(
            id = 1L,
            nombre = "Camiseta"
        )

        assertEquals(listOf("S", "M", "L", "XL"), producto.tallas)
    }

    @Test
    fun `test copy de producto cambiando precio`() {
        val productoOriginal = Producto(
            id = 1L,
            nombre = "Camiseta",
            precio = 10000
        )

        val productoModificado = productoOriginal.copy(precio = 15000)

        assertEquals(10000, productoOriginal.precio)
        assertEquals(15000, productoModificado.precio)
        assertEquals(productoOriginal.nombre, productoModificado.nombre)
    }

    @Test
    fun `test igualdad de productos con mismo ID`() {
        val producto1 = Producto(id = 1L, nombre = "Camiseta A")
        val producto2 = Producto(id = 1L, nombre = "Camiseta A")

        assertEquals(producto1, producto2)
    }

    @Test
    fun `test productos diferentes con distinto ID`() {
        val producto1 = Producto(id = 1L, nombre = "Camiseta")
        val producto2 = Producto(id = 2L, nombre = "Camiseta")

        assertNotEquals(producto1, producto2)
    }
}

