package com.example.apptiendaval2.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaeval2.model.Valoracion
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaProductTitle
import com.example.apptiendaeval2.ui.theme.FuturaPrice
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    producto: Producto,
    cartViewModel: CartViewModel
) {
    var selectedImage by remember { mutableStateOf(producto.imagenUrl) }

    var selectedTalla by remember {
        mutableStateOf(
            when {
                producto.medidas.isNotEmpty() -> producto.medidas.first()
                producto.tallas.isNotEmpty() -> producto.tallas.first()
                else -> "S"
            }
        )
    }

    var cantidad by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        producto.nombre?.uppercase() ?: "",
                        style = FuturaProductTitle,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {

            item {

                // ✅ IMAGEN PRINCIPAL (URL)
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    elevation = 4.dp
                ) {
                    AsyncImage(
                        model = selectedImage,
                        contentDescription = producto.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(Modifier.height(12.dp))

                // ✅ GALERÍA DE IMÁGENES
                if (producto.imagenesUrl.isNotEmpty()) {
                    val allImages = listOf(producto.imagenUrl) + producto.imagenesUrl

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        allImages.forEach { imgUrl ->
                            Card(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable { selectedImage = imgUrl },
                                shape = RoundedCornerShape(6.dp),
                                elevation = 2.dp
                            ) {
                                AsyncImage(
                                    model = imgUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(producto.nombre ?: "", style = FuturaProductTitle)
                Text("PRECIO: \$${producto.precio ?: 0}", style = FuturaPrice)

                Spacer(Modifier.height(12.dp))

                Text("DESCRIPCIÓN", style = MaterialTheme.typography.h6)
                Text(producto.descripcion ?: "")

                Spacer(Modifier.height(16.dp))

                if (producto.tallas.isNotEmpty()) {
                    Text("TALLAS", style = MaterialTheme.typography.h6)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        producto.tallas.forEach { talla ->
                            OutlinedButton(
                                onClick = { selectedTalla = talla }
                            ) {
                                Text(talla)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text("CANTIDAD", style = MaterialTheme.typography.h6)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = { if (cantidad > 1) cantidad-- }) {
                        Text("-")
                    }

                    Text(
                        text = cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    OutlinedButton(onClick = { if (cantidad < 99) cantidad++ }) {
                        Text("+")
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text("VALORACIONES", style = MaterialTheme.typography.h6)

                if (producto.valoraciones.isEmpty()) {
                    Text("Aún no hay valoraciones.")
                } else {
                    producto.valoraciones.forEach { v ->
                        RatingRow(v)
                    }
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        repeat(cantidad) {
                            cartViewModel.addProduct(producto, selectedTalla)
                        }
                        cantidad = 1
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("AGREGAR AL CARRITO", style = FuturaButtonStyle)
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate("cart") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("IR AL CARRITO", style = FuturaButtonStyle)
                }
            }
        }
    }
}

@Composable
private fun RatingRow(v: Valoracion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(v.usuario, style = MaterialTheme.typography.subtitle2)
                Text(v.comentario, style = MaterialTheme.typography.body2)
            }
            Text("${v.estrellas} ⭐")
        }
    }
}
