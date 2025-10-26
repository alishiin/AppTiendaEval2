package com.example.apptiendaval2.model

import com.example.apptiendaeval2.R

object ProductRepository {
    private val products = listOf(
        Producto(
            id = 1,
            nombre = "Polera Negra",
            precio = 12990,
            descripcion = "Polera de algodón 100%, cómoda. aumentará tu aura ",
            imagenResId = R.drawable.polera_negra,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("María", 5, "Muy buena calidad."),
                Valoracion("Joaquín", 4, "Cómoda, llegó rápido goood.")
            )
        ),
        Producto(
            id = 2,
            nombre = "Polera azul",
            precio = 15000,
            descripcion = "Polera de algodon 100% y muy comoda.",
            imagenResId = R.drawable.polera_azul,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Carlos", 4, "Cómoda, me quedó de pana mi rey."),
                Valoracion("momo", 4, "muy buena, vaneado pa.")
            )
        ),
        Producto(
            id = 3,
            nombre = "polera blanca",
            precio = 12990,
            descripcion = "Polera de poliester y comoda para usar",
            imagenResId = R.drawable.polera_gris,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("martin",5,"está muy buena, la recomiendo chat"),
                Valoracion("santiago", 4, "el material muy bueno y la polera fiel a al foto.")
            )
        ),
        Producto(
            id = 4,
            nombre = "poleron cropped gris",
            precio = 25000,
            descripcion = "poleron con gorro corte cropped",
            imagenResId = R.drawable.poleron_gris,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("daniel",6,"muy buen producto, y la calidad es un 10"),
                Valoracion("moaay",4,"no me quedó bien amiguis, como lo devuelvo?")
            )
        ),
        Producto(
            id = 5,
            nombre = "Polera azul marino",
            precio = 12990,
            descripcion = "camiseta de manga corta con cuello redondo y estampado",
            imagenResId = R.drawable.polera_auto,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Papi Micky",4, "aceptan canje o no?"),
                Valoracion("Jh", 2, "me quedó grande muchachos")
            )
        ),
        Producto(
            id = 6,
            nombre = "Polera gris de mujer",
            precio = 12990,
            descripcion = "camiseta de manga corta casual y vintage",
            imagenResId = R.drawable.polera_mujer,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("María",6, "muy bonitaa"),
                Valoracion("constanza", 5, "me quedó super, la volveria a comprar")
            )
        ),
        Producto(
            id = 7,
            nombre = "Polera morada",
            precio = 12990,
            descripcion = "Camiseta de algodón y estampado de dragón",
            imagenResId = R.drawable.polera_dragon,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Vicente",6, "tal cual la imagen, me gustó mucho"),
                Valoracion("Emilia", 5, "justo lo que esperaba, como siempre la calidad 10/10")
            )
        ),
        Producto(
            id = 8,
            nombre = "Polera gris y negro",
            precio = 12990,
            descripcion = "Camiseta de hombre con cuello redondo manga corta",
            imagenResId = R.drawable.polera_negro_gris,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Rodrigo",6, "muy buena calidad"),
                Valoracion("Benjamin", 5, "la tela me gustó")
            )
        ),
        Producto(
            id = 9,
            nombre = "Polera gris y negro",
            precio = 12990,
            descripcion = "Camiseta de hombre street de manga corta y estampado",
            imagenResId = R.drawable.polera_negra_estampado,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Manuel",6, "muy linda y comoda"),
                Valoracion("Carlos", 5, "la pedí en L y me quedó super")
            )
        ),
        Producto(
            id = 10,
            nombre = "Polera Grunge Punk",
            precio = 12990,
            descripcion = "Camiseta con estampado de silueta de hombre",
            imagenResId = R.drawable.polera_negra_dark,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Mateo",6, "me encantó el diseño, está muy bonita la verdad"),
                Valoracion("Lucas", 5, "recomendadaaaa")
            )
        ),
        Producto(
            id = 11,
            nombre = "Jeans grises",
            precio = 12990,
            descripcion = "Jeans rectos de pierna ancha para hombre, estilo casual de calle",
            imagenResId = R.drawable.pantalon_gris,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("José",6, "un 10/10"),
                Valoracion("Thiago", 5, "muy buena ropa")
            )
        ),
        Producto(
            id = 12,
            nombre = "Camiseta casual",
            precio = 12990,
            descripcion = "Camiseta gris de hombre con diseño de dragon",
            imagenResId = R.drawable.camista_diseno_dragon,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Hector",6, "fresca la tela, me gustó"),
                Valoracion("Maxi", 5, "está buena")
            )
        ),
        Producto(
            id = 13,
            nombre = "Pantalon corto",
            precio = 12990,
            descripcion = "Shorts informales con estampado",
            imagenResId = R.drawable.pantalon_corto,
            imagenesResId = listOf(
            ),
            valoraciones = listOf(
                Valoracion("Gabriel",5, "El diseño es de lo mejor"),
                Valoracion("Javier", 6, "calidad 10/10")
            )
        )




    )

    fun getAll() = products
    fun getById(id: Int) = products.find { it.id == id }
}
