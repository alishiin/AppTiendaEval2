package com.example.apptiendaeval2

import com.example.apptiendaeval2.utils.ChileData
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para ChileData
 * Valida las listas de comunas y ciudades de Chile
 */
class ChileDataTest {

    @Test
    fun `test comunas de Santiago no esta vacia`() {
        assertTrue("La lista de comunas debe tener elementos", ChileData.comunasSantiago.isNotEmpty())
    }

    @Test
    fun `test comunas de Santiago contiene Las Condes`() {
        assertTrue(
            "Las Condes debe estar en la lista",
            ChileData.comunasSantiago.contains("Las Condes")
        )
    }

    @Test
    fun `test comunas de Santiago contiene Providencia`() {
        assertTrue(
            "Providencia debe estar en la lista",
            ChileData.comunasSantiago.contains("Providencia")
        )
    }

    @Test
    fun `test comunas de Santiago contiene Maipu`() {
        assertTrue(
            "Maipú debe estar en la lista",
            ChileData.comunasSantiago.contains("Maipú")
        )
    }

    @Test
    fun `test comunas de Santiago esta ordenada alfabeticamente`() {
        val comunasOrdenadas = ChileData.comunasSantiago.sorted()
        assertEquals(
            "Las comunas deben estar ordenadas alfabéticamente",
            comunasOrdenadas,
            ChileData.comunasSantiago
        )
    }

    @Test
    fun `test comunas de Santiago no tiene duplicados`() {
        val comunasUnicas = ChileData.comunasSantiago.distinct()
        assertEquals(
            "No debe haber comunas duplicadas",
            comunasUnicas.size,
            ChileData.comunasSantiago.size
        )
    }

    @Test
    fun `test todas las comunas son strings no vacios`() {
        ChileData.comunasSantiago.forEach { comuna ->
            assertTrue("Cada comuna debe tener texto", comuna.isNotBlank())
        }
    }

    @Test
    fun `test cantidad minima de comunas`() {
        assertTrue(
            "Debe haber al menos 30 comunas en Santiago",
            ChileData.comunasSantiago.size >= 30
        )
    }
}

