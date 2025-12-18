package com.example.apptiendaeval2.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {

    /**
     * Formatea un número a pesos chilenos
     * Ejemplo: 15990 -> "$15.990"
     */
    fun formatChileanPesos(amount: Int): String {
        val format = NumberFormat.getNumberInstance(Locale("es", "CL"))
        return "$${format.format(amount)}"
    }

    /**
     * Formatea un número double a pesos chilenos
     */
    fun formatChileanPesos(amount: Double): String {
        val format = NumberFormat.getNumberInstance(Locale("es", "CL"))
        return "$${format.format(amount.toInt())}"
    }
}
