package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.model.ProductRepository
import com.example.apptiendaval2.model.Valoracion
import com.example.apptiendaval2.viewmodel.CartViewModel
import androidx.compose.ui.graphics.Color

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
            Text("Producto no encontrado")
            Spacer(Modifier.height(8.dp))
            Button(onClick = { navController.navigate("catalog") }) {
                Text("Volver al catálogo")
            }
        }
        return
    }

    var selectedImage by remember { mutableStateOf(producto.imagenResId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(producto.nombre, style = MaterialTheme.typography.h6) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
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

            // Imagen principal
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(200.dp),
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

            // Carrusel de miniaturas
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(producto.imagenesResId.size + 1) { index ->
                    val imgRes = if (index == 0) producto.imagenResId else producto.imagenesResId.getOrNull(index - 1)
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

            // Nombre y precio
            Text(producto.nombre, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(4.dp))
            Text("Precio: \$${producto.precio}", style = MaterialTheme.typography.subtitle1)

            Spacer(Modifier.height(12.dp))

            // Descripción
            Text("Descripción", style = MaterialTheme.typography.subtitle1)
            Text(producto.descripcion, modifier = Modifier.padding(top = 6.dp))

            Spacer(Modifier.height(12.dp))

            // Valoraciones
            Text("Valoraciones", style = MaterialTheme.typography.subtitle1)
            if (producto.valoraciones.isEmpty()) {
                Text("Aún no hay valoraciones.")
            } else {
                producto.valoraciones.forEach { v ->
                    RatingRow(v)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botones: Agregar al carrito + Ir al carrito
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(modifier = Modifier.weight(1f), onClick = {
                    cartViewModel.addProduct(producto)
                }) {
                    Text("Agregar al carrito")
                }
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate("cart")
                }) {
                    Text("Ir al carrito")
                }
            }
        }
    }
}

@Composable
private fun RatingRow(v: Valoracion) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(v.usuario, style = MaterialTheme.typography.subtitle2)
                Text(v.comentario, style = MaterialTheme.typography.body2)
            }
            Text("${v.estrellas} ⭐", modifier = Modifier.padding(start = 8.dp))
        }
    }
}
