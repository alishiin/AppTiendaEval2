package com.example.apptiendaval2.model

import com.example.apptiendaeval2.R

object ProductRepository {
    private val products = listOf(
        Producto(
            id = 1,
            nombre = "Polera Negra Forever",
            precio = 12990,
            descripcion = "Polera de algodon 100%, comoda. aumentara tu aura",
            imagenResId = R.drawable.polera_negra,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_azul, R.drawable.polera_gris),
            valoraciones = listOf(
                Valoracion("MariaUwU", 5, "Muy buena calidad para mi novio jiji."),
                Valoracion("ElJuacoInsano", 4, "Comoda, llego rapido goood.")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 2,
            nombre = "Polera Azul Los Angeles",
            precio = 15000,
            descripcion = "Polera de algodon 100% UnderGround.",
            imagenResId = R.drawable.polera_azul,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_negra, R.drawable.polera_dragon),
            valoraciones = listOf(
                Valoracion("Carlillos777", 4, "Comoda, me quedo de pana mi rey."),
                Valoracion("momo", 4, "muy buena, vaneado pa.")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 3,
            nombre = "Polera Blanca Skyline",
            precio = 12990,
            descripcion = "Polera de poliester de RyF Pegado",
            imagenResId = R.drawable.polera_gris,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_mujer, R.drawable.polera_auto),
            valoraciones = listOf(
                Valoracion("Ferguson",5,"está muy buena, la recomiendo chat"),
                Valoracion("Hater12", 2, "El disenio estamu y mal porke estA pegao dislaics")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 4,
            nombre = "Poleron Cropped Gris",
            precio = 25000,
            descripcion = "poleron con capucha cropped Intervalans",
            imagenResId = R.drawable.poleron_gris,
            categoria = Categoria.POLERONES,
            imagenesResId = listOf(R.drawable.polera_negra, R.drawable.polera_gris),
            valoraciones = listOf(
                Valoracion("Corazun7.7",4,"muy buen producto, y la calidad es un 10"),
                Valoracion("MooaaY",4,"no me quedó bien amiguis, como lo devuelvo?")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 5,
            nombre = "Polera Azul Marino Suki",
            precio = 12990,
            descripcion = "camiseta de manga corta con cuello redondo y estampado",
            imagenResId = R.drawable.polera_auto,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_azul, R.drawable.polera_negro_gris),
            valoraciones = listOf(
                Valoracion("Papi Micky",3, "aceptan canje o no?"),
                Valoracion("Jh", 2, "me quedo grande muchachos")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 6,
            nombre = "Polera Gris de Mujer StayWild",
            precio = 12990,
            descripcion = "camiseta de manga corta casual y vintage",
            imagenResId = R.drawable.polera_mujer,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_mujer, R.drawable.polera_gris, R.drawable.polera_auto),
            valoraciones = listOf(
                Valoracion("RataPeluda",5, "muy bonitaa"),
                Valoracion("NamiPlayer", 4, "me quedo super, la volveria a comprar 100 veces jjij")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 7,
            nombre = "Polera Morada AsiantiK",
            precio = 12990,
            descripcion = "Camiseta de algodon y estampado de dragón",
            imagenResId = R.drawable.polera_dragon,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_dragon, R.drawable.polera_negra, R.drawable.camista_diseno_dragon),
            valoraciones = listOf(
                Valoracion("VichoCo66",6, "tal cual la imagen, me gusto mucho"),
                Valoracion("Emili<3", 5, "justo lo que esperaba y la calidad 10/10")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 8,
            nombre = "Polera Gris y Negro",
            precio = 12990,
            descripcion = "Camiseta de hombre con cuello redondo manga corta",
            imagenResId = R.drawable.polera_negro_gris,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_negro_gris, R.drawable.polera_negra, R.drawable.polera_gris),
            valoraciones = listOf(
                Valoracion("Kakashi69",4, "muy buena calidad me gustoooo"),
                Valoracion("Benjita6.5", 5, "ta entera wena la polera siii")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 9,
            nombre = "Polera Gris y Negro AsiantiK",
            precio = 12990,
            descripcion = "Camiseta de hombre street de manga corta y estampado",
            imagenResId = R.drawable.polera_negra_estampado,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_negra_estampado, R.drawable.polera_dragon, R.drawable.polera_negra_dark),
            valoraciones = listOf(
                Valoracion("Manucraft4",5, "muy linda y comoda"),
                Valoracion("FerguinZ", 4, "la pedí en L y me llego tal cual (soy gei)")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 10,
            nombre = "Polera Grunge Punk Phantom",
            precio = 12990,
            descripcion = "Camiseta con estampado de silueta de hombre",
            imagenResId = R.drawable.polera_negra_dark,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.polera_negra_dark, R.drawable.polera_negra, R.drawable.polera_negra_estampado),
            valoraciones = listOf(
                Valoracion("Alishin",3, "me encanto el diseño y no juego lol con los panas"),
                Valoracion("LaParka", 4, "recomendadaaaa 100%%%%")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 11,
            nombre = "Jeans vaquero Gris",
            precio = 12990,
            descripcion = "Jeans rectos de pierna ancha para hombre, estilo vaquero",
            imagenResId = R.drawable.pantalon_gris,
            categoria = Categoria.PANTALONES,
            imagenesResId = listOf(R.drawable.pantalon_corto, R.drawable.polera_gris),
            valoraciones = listOf(
                Valoracion("MontaCaballos",3, "muy buena pero la calidad meh"),
                Valoracion("HogRiders", 4, "muy buena ropa osi osi")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 12,
            nombre = "Camiseta Casual Gris AsiantiK",
            precio = 12990,
            descripcion = "Camiseta gris de hombre con diseño de dragon",
            imagenResId = R.drawable.camista_diseno_dragon,
            categoria = Categoria.POLERAS,
            imagenesResId = listOf(R.drawable.camista_diseno_dragon, R.drawable.polera_dragon, R.drawable.polera_gris),
            valoraciones = listOf(
                Valoracion("Ironman",3, "fresca la tela, me gusto"),
                Valoracion("Dr.Straño", 3, "esta buena")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 13,
            nombre = "Pantalon Corto",
            precio = 12990,
            descripcion = "Shorts informales con estampado UnderGround",
            imagenResId = R.drawable.pantalon_corto,
            categoria = Categoria.PANTALONES,
            imagenesResId = listOf(R.drawable.pantalon_corto, R.drawable.pantalon_gris, R.drawable.polera_auto),
            valoraciones = listOf(
                Valoracion("Anndy33",5, "El diseño es de lo mejor"),
                Valoracion("Hater2", 1, "el estampao se me salio despue de lavar el shor")
            ),
            tallas = listOf("S","M","L","XL")
        ),
        Producto(
            id = 14,
            nombre = "Cuadro Abstracto Moderno",
            precio = 24990,
            descripcion = "Cuadro abstracto con diseños modernos, impreso en lienzo de alta calidad con marco de madera",
            imagenResId = R.drawable.cuadro1,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro1, R.drawable.cuadro2),
            valoraciones = listOf(
                Valoracion("ArtLover", 5, "Hermoso cuadro, la calidad del lienzo es excelente"),
                Valoracion("ModernDecor", 4, "Perfecto para mi sala, combina con todo")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 15,
            nombre = "Cuadro Paisaje Natural",
            precio = 27990,
            descripcion = "Hermoso paisaje natural, colores vivos y realistas, ideal para crear ambiente relajante",
            imagenResId = R.drawable.cuadro2,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro2, R.drawable.cuadro3),
            valoraciones = listOf(
                Valoracion("NatureLover", 5, "Los colores son increíbles, parece real"),
                Valoracion("HomeDecor", 4, "Llegó perfectamente embalado, muy recomendado")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 16,
            nombre = "Cuadro Minimalista",
            precio = 19990,
            descripcion = "Diseño minimalista elegante, perfecto para espacios modernos y sofisticados",
            imagenResId = R.drawable.cuadro3,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro3, R.drawable.cuadro4),
            valoraciones = listOf(
                Valoracion("MinimalStyle", 5, "Exactamente lo que buscaba para mi oficina"),
                Valoracion("CleanDesign", 4, "Simple pero elegante, me encanta")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 17,
            nombre = "Cuadro Floral Vintage",
            precio = 22990,
            descripcion = "Hermosas flores con estilo vintage, detalles delicados pintados con técnica profesional",
            imagenResId = R.drawable.cuadro4,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro4, R.drawable.cuadro5),
            valoraciones = listOf(
                Valoracion("VintageVibes", 5, "Precioso, le da un toque especial a mi dormitorio"),
                Valoracion("FlowerPower", 4, "Los detalles florales son hermosos")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 18,
            nombre = "Cuadro Geométrico Colorido",
            precio = 25990,
            descripcion = "Formas geométricas vibrantes, diseño contemporáneo que aporta energía a cualquier espacio",
            imagenResId = R.drawable.cuadro5,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro5, R.drawable.cuadro6),
            valoraciones = listOf(
                Valoracion("GeometryFan", 5, "Los colores son súper vibrantes, me encanta"),
                Valoracion("ModernArt", 4, "Perfecto para mi sala de estar moderna")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 19,
            nombre = "Cuadro Urbano Industrial",
            precio = 26990,
            descripcion = "Motivos urbanos con texturas industriales, perfecto para lofts y espacios contemporáneos",
            imagenResId = R.drawable.cuadro6,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro6, R.drawable.cuadro7),
            valoraciones = listOf(
                Valoracion("UrbanStyle", 5, "Queda perfecto en mi loft, muy industrial"),
                Valoracion("CityVibes", 4, "La textura se ve muy real, excelente calidad")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        ),
        Producto(
            id = 20,
            nombre = "Cuadro Retro Vintage",
            precio = 21990,
            descripcion = "Estética retro con tonos cálidos, evoca nostalgia y calidez en tu hogar",
            imagenResId = R.drawable.cuadro7,
            categoria = Categoria.CUADROS,
            imagenesResId = listOf(R.drawable.cuadro7, R.drawable.cuadro1),
            valoraciones = listOf(
                Valoracion("RetroLover", 5, "Me transporta a otra época, hermoso"),
                Valoracion("WarmTones", 4, "Los tonos cálidos crean un ambiente acogedor")
            ),
            medidas = listOf("30cmx30cm", "30cmx45cm", "45cmx60cm", "70cmx55cm")
        )
    )

    fun getAll() = products
    fun getById(id: Int) = products.find { it.id == id }
    fun getByCategory(categoria: Categoria) = products.filter { it.categoria == categoria }
    fun getCategories() = Categoria.entries.toList()
}
