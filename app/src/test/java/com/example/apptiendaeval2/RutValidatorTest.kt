package com.example.apptiendaeval2

import com.example.apptiendaeval2.utils.RutValidator
import org.junit.Test
import org.junit.Assert.*

/**
 * Test unitarios para RutValidator
 * Valida formato, dígito verificador y formateo de RUT chileno
 */
class RutValidatorTest {

    @Test
    fun `test RUT valido con guion`() {
        val rutValido = "12345678-5"
        assertTrue("El RUT $rutValido debería ser válido", RutValidator.isValidRut(rutValido))
    }

    @Test
    fun `test RUT valido con puntos y guion`() {
        val rutValido = "12.345.678-5"
        assertTrue("El RUT $rutValido debería ser válido", RutValidator.isValidRut(rutValido))
    }

    @Test
    fun `test RUT invalido digito verificador incorrecto`() {
        val rutInvalido = "12345678-9"
        assertFalse("El RUT $rutInvalido debería ser inválido", RutValidator.isValidRut(rutInvalido))
    }

    @Test
    fun `test RUT valido con K mayuscula`() {
        val rutValido = "11111111-K"
        assertTrue("El RUT $rutValido debería ser válido", RutValidator.isValidRut(rutValido))
    }

    @Test
    fun `test RUT vacio es invalido`() {
        assertFalse("Un RUT vacío debería ser inválido", RutValidator.isValidRut(""))
    }

    @Test
    fun `test formateo de RUT`() {
        val rutSinFormato = "123456785"
        val rutFormateado = RutValidator.formatRut(rutSinFormato)
        assertEquals("El RUT debe formatearse correctamente", "12.345.678-5", rutFormateado)
    }

    @Test
    fun `test hasValidFormat con formato correcto`() {
        val rut = "12345678-5"
        assertTrue("El formato del RUT debería ser válido", RutValidator.hasValidFormat(rut))
    }

    @Test
    fun `test hasValidFormat con letras en numero es invalido`() {
        val rut = "1234ABC8-5"
        assertFalse("El RUT con letras en el número debería ser inválido", RutValidator.hasValidFormat(rut))
    }

    @Test
    fun `test RUT muy corto es invalido`() {
        val rutCorto = "1-9"
        assertFalse("Un RUT muy corto debería ser inválido", RutValidator.isValidRut(rutCorto))
    }
}

