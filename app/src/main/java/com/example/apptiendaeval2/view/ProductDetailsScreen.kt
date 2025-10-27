package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.model.ProductRepository
import com.example.apptiendaval2.model.Valoracion
import com.example.apptiendaval2.viewmodel.CartViewModel

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    productId: Int,
    cartViewModel: CartViewModel
) {
    val producto = remember(productId) { ProductRepository.getById(productId) }

    if (producto == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Producto no encontrado", color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("catalog") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Volver al catálogo")
            }
        }
        return
    }

    var selectedImage by remember { mutableStateOf(producto.imagenResId) }
    var selectedTalla by remember { mutableStateOf(producto.tallas.firstOrNull() ?: "S") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(producto.nombre, style = MaterialTheme.typography.h6, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                elevation = 4.dp
            ) {
                Image(
                    painter = painterResource(selectedImage),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(12.dp))

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(producto.imagenesResId.size + 1) { index ->
                    val imgRes =
                        if (index == 0) producto.imagenResId else producto.imagenesResId.getOrNull(index - 1)
                    if (imgRes != null) {
                        Card(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(4.dp)
                                .clickable { selectedImage = imgRes },
                            shape = RoundedCornerShape(6.dp),
                            elevation = 2.dp
                        ) {
                            Image(
                                painter = painterResource(imgRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(producto.nombre, style = MaterialTheme.typography.h6, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text("Precio: \$${producto.precio}", style = MaterialTheme.typography.subtitle1, color = Color.Black)

            Spacer(Modifier.height(12.dp))

            Text("Descripción", style = MaterialTheme.typography.subtitle1, color = Color.Black)
            Text(producto.descripcion, modifier = Modifier.padding(top = 6.dp), color = Color.Black)

            Spacer(Modifier.height(12.dp))

            Text("Tallas disponibles", style = MaterialTheme.typography.subtitle1, color = Color.Black)
            LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                items(producto.tallas.size) { index ->
                    val talla = producto.tallas[index]
                    OutlinedButton(
                        onClick = { selectedTalla = talla },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (selectedTalla == talla) Color.White else Color.Black,
                            backgroundColor = if (selectedTalla == talla) Color.Black else Color.Transparent
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(talla)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Valoraciones", style = MaterialTheme.typography.subtitle1, color = Color.Black)
            if (producto.valoraciones.isEmpty()) {
                Text("Aún no hay valoraciones.", color = Color.Black)
            } else {
                producto.valoraciones.forEach { v -> RatingRow(v) }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { cartViewModel.addProduct(producto) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Agregar al carrito")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("cart") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Ir al carrito")
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
                Text(v.usuario, style = MaterialTheme.typography.subtitle2, color = Color.Black)
                Text(v.comentario, style = MaterialTheme.typography.body2, color = Color.Black)
            }
            Text("${v.estrellas} ⭐", modifier = Modifier.padding(start = 8.dp), color = Color.Black)
        }
    }
}
