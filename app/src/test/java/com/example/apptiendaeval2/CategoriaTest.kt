package com.example.apptiendaeval2

import com.example.apptiendaeval2.model.Categoria
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para el enum Categoria
 * Valida la estructura y comportamiento del enum Categoria
 */
class CategoriaTest {

    @Test
    fun `test categoria POLERAS tiene displayName correcto`() {
        assertEquals("Poleras", Categoria.POLERAS.displayName)
    }

    @Test
    fun `test categoria PANTALONES tiene displayName correcto`() {
        assertEquals("Pantalones", Categoria.PANTALONES.displayName)
    }

    @Test
    fun `test categoria POLERONES tiene displayName correcto`() {
        assertEquals("Polerones", Categoria.POLERONES.displayName)
    }

    @Test
    fun `test obtener categoria por nombre`() {
        val categoria = Categoria.valueOf("POLERAS")
        assertEquals(Categoria.POLERAS, categoria)
    }

    @Test
    fun `test todas las categorias estan presentes`() {
        val categorias = Categoria.values()
        assertEquals(3, categorias.size)
        assertTrue(categorias.contains(Categoria.POLERAS))
        assertTrue(categorias.contains(Categoria.PANTALONES))
        assertTrue(categorias.contains(Categoria.POLERONES))
    }

    @Test
    fun `test toString de categoria devuelve nombre`() {
        val categoriaString = Categoria.POLERAS.toString()
        assertEquals("POLERAS", categoriaString)
    }

    @Test
    fun `test comparacion de categorias`() {
        val categoria1 = Categoria.POLERAS
        val categoria2 = Categoria.POLERAS
        val categoria3 = Categoria.PANTALONES

        assertEquals(categoria1, categoria2)
        assertNotEquals(categoria1, categoria3)
    }
}

