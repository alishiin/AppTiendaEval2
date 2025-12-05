package com.example.apptiendaeval2.utils

object RutValidator {

    /**
     * Valida que el RUT chileno sea válido
     * Formato esperado: 12345678-9 o 12.345.678-9
     */
    fun isValidRut(rut: String): Boolean {
        if (rut.isBlank()) return false

        // Limpiar el RUT (quitar puntos y guiones)
        val cleanRut = rut.replace(".", "").replace("-", "").trim()

        // Verificar que tenga al menos 2 caracteres (número + dígito verificador)
        if (cleanRut.length < 2) return false

        // Separar número y dígito verificador
        val rutNumber = cleanRut.substring(0, cleanRut.length - 1)
        val dv = cleanRut.substring(cleanRut.length - 1).uppercase()

        // Verificar que el número sea válido
        if (!rutNumber.all { it.isDigit() }) return false

        // Calcular dígito verificador
        val calculatedDv = calculateDv(rutNumber.toInt())

        return dv == calculatedDv
    }

    /**
     * Calcula el dígito verificador de un RUT
     */
    private fun calculateDv(rut: Int): String {
        var suma = 0
        var multiplicador = 2
        var rutTemp = rut

        while (rutTemp > 0) {
            suma += (rutTemp % 10) * multiplicador
            rutTemp /= 10
            multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
        }

        val resto = suma % 11
        val dv = 11 - resto

        return when (dv) {
            11 -> "0"
            10 -> "K"
            else -> dv.toString()
        }
    }

    /**
     * Formatea un RUT al formato 12.345.678-9
     */
    fun formatRut(rut: String): String {
        val cleanRut = rut.replace(".", "").replace("-", "").trim()
        if (cleanRut.length < 2) return rut

        val rutNumber = cleanRut.substring(0, cleanRut.length - 1)
        val dv = cleanRut.substring(cleanRut.length - 1)

        // Formatear con puntos
        val formatted = StringBuilder()
        var count = 0
        for (i in rutNumber.length - 1 downTo 0) {
            if (count == 3) {
                formatted.insert(0, ".")
                count = 0
            }
            formatted.insert(0, rutNumber[i])
            count++
        }

        return "$formatted-$dv"
    }

    /**
     * Valida el formato del RUT sin verificar el dígito
     */
    fun hasValidFormat(rut: String): Boolean {
        val cleanRut = rut.replace(".", "").replace("-", "").trim()
        if (cleanRut.length < 2) return false

        val rutNumber = cleanRut.substring(0, cleanRut.length - 1)
        val dv = cleanRut.substring(cleanRut.length - 1).uppercase()

        return rutNumber.all { it.isDigit() } && (dv.all { it.isDigit() } || dv == "K")
    }
}

