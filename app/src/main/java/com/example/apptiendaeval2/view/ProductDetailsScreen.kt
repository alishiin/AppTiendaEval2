package com.example.apptiendaval2.view

import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.apptiendaeval2.model.ProductRepository
import com.example.apptiendaeval2.model.Valoracion
import com.example.apptiendaeval2.model.Categoria
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaProductTitle
import com.example.apptiendaeval2.ui.theme.FuturaPrice
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

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
                Text("VOLVER AL CATALOGO", style = FuturaButtonStyle)
            }
        }
        return
    }

    var selectedImage by remember { mutableStateOf(producto.imagenResId) }
    var selectedTalla by remember(producto.id) {
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
                title = { Text(producto.nombre.uppercase(), style = FuturaProductTitle, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atras", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
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

            if (producto.imagenesResId.isNotEmpty()) {
                val allImages = remember(producto.id) {
                    listOf(producto.imagenResId) + producto.imagenesResId.take(2)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    allImages.forEach { imgRes ->
                        Card(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { selectedImage = imgRes },
                            shape = RoundedCornerShape(6.dp),
                            elevation = 1.dp
                        ) {
                            Image(
                                painter = painterResource(imgRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(12.dp))

            Text(producto.nombre.uppercase(), style = FuturaProductTitle, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text("PRECIO: \$${producto.precio}", style = FuturaPrice, color = Color(0xFF006400))

            Spacer(Modifier.height(12.dp))

            Text("DESCRIPCION", style = MaterialTheme.typography.h5, color = Color.Black)
            Text(producto.descripcion.uppercase(), style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 6.dp), color = Color.Black)

            Spacer(Modifier.height(12.dp))

            if (producto.medidas.isNotEmpty()) {
                Text("MEDIDAS DISPONIBLES", style = MaterialTheme.typography.h5, color = Color.Black)
                val medidas = remember(producto.id) { producto.medidas }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    medidas.forEach { medida ->
                        OutlinedButton(
                            onClick = { selectedTalla = medida },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (selectedTalla == medida) Color.White else Color.Black,
                                backgroundColor = if (selectedTalla == medida) Color.Black else Color.Transparent
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = medida,
                                style = MaterialTheme.typography.caption,
                                maxLines = 1
                            )
                        }
                    }
                }
            } else if (producto.tallas.isNotEmpty()) {
                Text("TALLAS DISPONIBLES", style = MaterialTheme.typography.h5, color = Color.Black)
                val tallas = remember(producto.id) { producto.tallas }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tallas.forEach { talla ->
                        OutlinedButton(
                            onClick = { selectedTalla = talla },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (selectedTalla == talla) Color.White else Color.Black,
                                backgroundColor = if (selectedTalla == talla) Color.Black else Color.Transparent
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(talla, style = MaterialTheme.typography.button)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Selector de cantidad
            Text("CANTIDAD", style = MaterialTheme.typography.h5, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón menos
                OutlinedButton(
                    onClick = { if (cantidad > 1) cantidad-- },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text("-", style = MaterialTheme.typography.h5)
                }

                // Mostrar cantidad actual
                Card(
                    modifier = Modifier.weight(1f),
                    elevation = 2.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cantidad.toString(),
                            style = MaterialTheme.typography.h5,
                            color = Color.Black
                        )
                    }
                }

                // Botón más
                OutlinedButton(
                    onClick = { if (cantidad < 99) cantidad++ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text("+", style = MaterialTheme.typography.h5)
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("VALORACIONES", style = MaterialTheme.typography.h5, color = Color.Black)
            if (producto.valoraciones.isEmpty()) {
                Text("AUN NO HAY VALORACIONES.", style = MaterialTheme.typography.body1, color = Color.Black)
            } else {
                producto.valoraciones.take(2).forEach { v -> RatingRow(v) }
            }


            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        repeat(cantidad) {
                            cartViewModel.addProduct(producto, selectedTalla)
                        }
                        cantidad = 1 // Reset cantidad después de agregar
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("AGREGAR AL CARRITO", style = FuturaButtonStyle)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("cart") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("IR AL CARRITO", style = FuturaButtonStyle)
                }
            }

            // Agregar espacio adicional al final para asegurar que los botones sean visibles
            Spacer(Modifier.height(32.dp))
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
