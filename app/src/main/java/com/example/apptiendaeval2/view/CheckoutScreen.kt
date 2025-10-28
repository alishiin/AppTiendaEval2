package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.apptiendaval2.viewmodel.CartViewModel

@Composable
fun CheckoutScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Boleta de Compra", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atras", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.95f))
        ) {
            Text("Resumen de la compra", style = MaterialTheme.typography.h6, color = Color.Black)
            Spacer(Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems.size) { index ->
                    val item = cartItems[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(6.dp),
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = item.producto.imagenResId),
                                contentDescription = item.producto.nombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(end = 8.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.producto.nombre, color = Color.Black)
                                Text("Cantidad: ${item.cantidad}", color = Color.Black)
                            }

                            Text("\$${item.producto.precio * item.cantidad}", color = Color.Black)
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", style = MaterialTheme.typography.h6, color = Color.Black)
                Text("\$${total}", style = MaterialTheme.typography.h6, color = Color.Black)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    cartViewModel.clear()
                    navController.navigate("success")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Confirmar Compra")
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Gracias por su compra!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        }
    }
}
