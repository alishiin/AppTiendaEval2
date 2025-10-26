package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaval2.viewmodel.CartItem

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo igual al Login
        Image(
            painter = painterResource(id = com.example.apptiendaeval2.R.drawable.fondo_godines),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Capa semi-transparente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.35f))
        )

        // Contenido del carrito
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                // Carrito vacío con botón para volver
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Tu carrito está vacío",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            // ✅ Ruta corregida: vuelve al catálogo correctamente
                            onClick = { navController.navigate("catalog") },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black.copy(alpha = 0.7f),
                                contentColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Volver a la tienda")
                        }
                    }
                }
            } else {
                // Lista de productos
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems.size) { index ->
                        val item = cartItems[index]
                        CartItemRow(item, onQuantityChange = { newQty ->
                            cartViewModel.updateQuantity(item.producto.id, newQty)
                        })
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "Total: \$${total}",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { cartViewModel.clear() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Vaciar carrito")
                        Spacer(Modifier.width(4.dp))
                        Text("Vaciar")
                    }

                    Button(
                        onClick = {
                            if (total > 0) {
                                if (total < 100000) {
                                    navController.navigate("success")
                                    cartViewModel.clear()
                                } else {
                                    navController.navigate("error")
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Finalizar compra")
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onQuantityChange: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
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
                modifier = Modifier.size(70.dp)
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.producto.nombre, style = MaterialTheme.typography.h6)
                Text("Precio: \$${item.producto.precio}")

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = {
                            if (item.cantidad > 1) onQuantityChange(item.cantidad - 1)
                        },
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) { Text("-") }

                    Text(
                        text = item.cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    OutlinedButton(
                        onClick = { onQuantityChange(item.cantidad + 1) },
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) { Text("+") }
                }
            }
        }
    }
}
