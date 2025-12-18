package com.example.apptiendaeval2.utils

import kotlin.math.pow

object SizeCalculator {

    /**
     * Calcula el IMC (Índice de Masa Corporal)
     */
    private fun calculateBMI(weight: Double, height: Double): Double {
        val heightInMeters = height / 100.0
        return weight / (heightInMeters.pow(2))
    }

    /**
     * Recomienda una talla según estatura, peso, edad y tipo de prenda
     */
    fun recommendSize(
        height: Double,  // en cm
        weight: Double,  // en kg
        age: Int,
        garmentType: String  // "POLERAS", "PANTALONES", "POLERONES"
    ): String {
        val bmi = calculateBMI(weight, height)

        return when (garmentType.uppercase()) {
            "POLERAS", "POLERONES" -> recommendTopSize(height, weight, bmi, age)
            "PANTALONES" -> recommendBottomSize(height, weight, bmi, age)
            else -> recommendTopSize(height, weight, bmi, age)
        }
    }

    /**
     * Recomienda talla para parte superior (poleras, polerones)
     */
    private fun recommendTopSize(height: Double, weight: Double, bmi: Double, age: Int): String {
        // Ajuste por edad (personas mayores tienden a necesitar tallas más amplias)
        val ageAdjustment = if (age > 50) 1 else 0

        return when {
            // Persona delgada (BMI < 18.5)
            bmi < 18.5 -> {
                when {
                    height < 160 -> "XS"
                    height < 170 -> "S"
                    height < 180 -> "M"
                    else -> "L"
                }
            }
            // Peso normal (BMI 18.5 - 24.9)
            bmi < 25 -> {
                when {
                    height < 155 -> "XS"
                    height < 165 -> "S"
                    height < 175 -> "M"
                    height < 185 -> "L"
                    else -> "XL"
                }
            }
            // Sobrepeso (BMI 25 - 29.9)
            bmi < 30 -> {
                val baseSize = when {
                    height < 160 -> "M"
                    height < 170 -> "L"
                    height < 180 -> "XL"
                    else -> "XXL"
                }
                if (ageAdjustment > 0) increaseSizeByOne(baseSize) else baseSize
            }
            // Obesidad (BMI >= 30)
            else -> {
                val baseSize = when {
                    height < 165 -> "XL"
                    height < 175 -> "XXL"
                    else -> "XXXL"
                }
                if (ageAdjustment > 0) increaseSizeByOne(baseSize) else baseSize
            }
        }
    }

    /**
     * Recomienda talla para parte inferior (pantalones)
     */
    private fun recommendBottomSize(height: Double, weight: Double, bmi: Double, age: Int): String {
        val waistSize = when {
            bmi < 18.5 -> if (height < 170) 28 else 30
            bmi < 25 -> {
                when {
                    height < 160 -> 28
                    height < 170 -> 30
                    height < 180 -> 32
                    else -> 34
                }
            }
            bmi < 30 -> {
                when {
                    height < 165 -> 32
                    height < 175 -> 34
                    height < 185 -> 36
                    else -> 38
                }
            }
            else -> {
                when {
                    height < 170 -> 36
                    height < 180 -> 38
                    else -> 40
                }
            }
        }

        val lengthType = when {
            height < 165 -> "Corto"
            height < 180 -> "Regular"
            else -> "Largo"
        }

        return "$waistSize ($lengthType)"
    }

    /**
     * Aumenta una talla
     */
    private fun increaseSizeByOne(size: String): String {
        return when (size) {
            "XS" -> "S"
            "S" -> "M"
            "M" -> "L"
            "L" -> "XL"
            "XL" -> "XXL"
            "XXL" -> "XXXL"
            else -> size
        }
    }

    /**
     * Obtiene información adicional sobre la recomendación
     */
    fun getSizeRecommendationInfo(
        height: Double,
        weight: Double,
        age: Int,
        garmentType: String
    ): String {
        val bmi = calculateBMI(weight, height)
        val recommendedSize = recommendSize(height, weight, age, garmentType)

        val bmiCategory = when {
            bmi < 18.5 -> "bajo peso"
            bmi < 25 -> "peso normal"
            bmi < 30 -> "sobrepeso"
            else -> "obesidad"
        }

        val info = buildString {
            append("📏 Tu talla recomendada es: $recommendedSize\n\n")
            append("📊 Basado en:\n")
            append("• Estatura: ${height.toInt()} cm\n")
            append("• Peso: ${weight.toInt()} kg\n")
            append("• Edad: $age años\n")
            append("• IMC: ${"%.1f".format(bmi)} ($bmiCategory)\n\n")
            append("💡 Tip: Si prefieres ropa más ajustada, elige una talla menor. ")
            append("Si prefieres ropa más holgada, elige una talla mayor.")
        }

        return info
    }
}

