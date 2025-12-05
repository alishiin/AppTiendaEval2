package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun OrderConfirmationScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { (it.producto.precio ?: 0) * it.cantidad }

    val shippingData = cartViewModel.shippingData.value
    val paymentMethod = cartViewModel.paymentMethod.value
    val paymentAmount = cartViewModel.paymentAmount.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CONFIRMACIÓN DE PEDIDO",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = com.example.apptiendaeval2.R.drawable.fondo_godines),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.35f))
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Mensaje de éxito
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 8.dp,
                        backgroundColor = Color(0xFF4CAF50),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Éxito",
                                tint = Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "¡PEDIDO CONFIRMADO!",
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Gracias por su compra",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Datos de envío
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                        backgroundColor = Color.White.copy(alpha = 0.95f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = com.example.apptiendaeval2.R.drawable.ic_launcher_foreground),
                                    contentDescription = "Envío",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "DATOS DE ENVÍO",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            InfoRow("Nombre:", shippingData["nombre"] ?: "")
                            InfoRow("Dirección:", shippingData["direccion"] ?: "")
                            InfoRow("Ciudad:", shippingData["ciudad"] ?: "")
                            InfoRow("Comuna:", shippingData["comuna"] ?: "")
                            InfoRow("Teléfono:", shippingData["telefono"] ?: "")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Productos
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                        backgroundColor = Color.White.copy(alpha = 0.95f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Productos",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "PRODUCTOS",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            cartItems.forEach { item ->
                                ProductConfirmationRow(item)
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Resumen de pago
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                        backgroundColor = Color(0xFFFFF9C4)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "RESUMEN DE PAGO",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            InfoRow("Método de pago:", paymentMethod.uppercase())
                            InfoRow("Total de productos:", "\$${total}")
                            InfoRow("Monto pagado:", "\$${paymentAmount}")

                            if (paymentAmount > total) {
                                Spacer(Modifier.height(8.dp))
                                Card(
                                    backgroundColor = Color(0xFF4CAF50).copy(alpha = 0.2f),
                                    elevation = 0.dp
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Vuelto:",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF006400)
                                        )
                                        Text(
                                            "\$${paymentAmount - total}",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF006400)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Información adicional
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 2.dp,
                        backgroundColor = Color(0xFFE3F2FD)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "ℹ️ Información importante:",
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "• Su pedido será procesado en las próximas 24 horas\n" +
                                "• Recibirá un correo de confirmación\n" +
                                "• El tiempo de entrega es de 3-5 días hábiles\n" +
                                "• Número de orden: #${System.currentTimeMillis() / 1000}",
                                style = MaterialTheme.typography.caption,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Botones
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                cartViewModel.clear()
                                navController.navigate("catalog") {
                                    popUpTo("login") { inclusive = false }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Text("SEGUIR COMPRANDO", style = FuturaButtonStyle)
                        }

                        Spacer(Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = {
                                cartViewModel.clear()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = false }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Black
                            )
                        ) {
                            Text("VOLVER AL INICIO")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1.5f)
        )
    }
}

@Composable
fun ProductConfirmationRow(item: com.example.apptiendaval2.viewmodel.CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.producto.imagenUrl),
            contentDescription = item.producto.nombre,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.producto.nombre?.uppercase() ?: "SIN NOMBRE",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Talla: ${item.talla} | Cant: ${item.cantidad}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }

        Text(
            text = "\$${(item.producto.precio ?: 0) * item.cantidad}",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006400)
        )
    }
}

