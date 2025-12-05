package com.example.apptiendaeval2.utils

object ChileData {

    // Comunas de Santiago
    val comunasSantiago = listOf(
        "Cerrillos",
        "Cerro Navia",
        "Conchalí",
        "El Bosque",
        "Estación Central",
        "Huechuraba",
        "Independencia",
        "La Cisterna",
        "La Florida",
        "La Granja",
        "La Pintana",
        "La Reina",
        "Las Condes",
        "Lo Barnechea",
        "Lo Espejo",
        "Lo Prado",
        "Macul",
        "Maipú",
        "Ñuñoa",
        "Pedro Aguirre Cerda",
        "Peñalolén",
        "Providencia",
        "Pudahuel",
        "Quilicura",
        "Quinta Normal",
        "Recoleta",
        "Renca",
        "San Joaquín",
        "San Miguel",
        "San Ramón",
        "Santiago Centro",
        "Vitacura"
    ).sorted()

    // Ciudades/Regiones principales de Chile
    val ciudadesChile = listOf(
        "Arica",
        "Iquique",
        "Antofagasta",
        "Calama",
        "Copiapó",
        "La Serena",
        "Coquimbo",
        "Valparaíso",
        "Viña del Mar",
        "Quilpué",
        "Villa Alemana",
        "Rancagua",
        "Santiago",
        "Talca",
        "Curicó",
        "Chillán",
        "Concepción",
        "Talcahuano",
        "Los Ángeles",
        "Temuco",
        "Valdivia",
        "Osorno",
        "Puerto Montt",
        "Puerto Varas",
        "Coyhaique",
        "Punta Arenas"
    ).sorted()

    /**
     * Obtiene las comunas según la ciudad seleccionada
     */
    fun getComunasPorCiudad(ciudad: String): List<String> {
        return when (ciudad) {
            "Santiago" -> comunasSantiago
            "Valparaíso" -> listOf(
                "Valparaíso",
                "Viña del Mar",
                "Quilpué",
                "Villa Alemana",
                "Concón"
            ).sorted()
            "Concepción" -> listOf(
                "Concepción",
                "Talcahuano",
                "Hualpén",
                "San Pedro de la Paz",
                "Chiguayante"
            ).sorted()
            else -> listOf(ciudad) // Por defecto, la misma ciudad
        }
    }
}

