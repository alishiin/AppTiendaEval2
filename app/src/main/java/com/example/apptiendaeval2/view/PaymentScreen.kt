package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun PaymentScreen(navController: NavController, cartViewModel: CartViewModel) {
    var montoIngresado by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var paymentSuccess by remember { mutableStateOf(false) }
    var faltante by remember { mutableStateOf(0) }

    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { (it.producto.precio ?: 0) * it.cantidad }
    val paymentMethod = cartViewModel.paymentMethod.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "REALIZAR PAGO",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Información del método de pago
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "MÉTODO DE PAGO SELECCIONADO",
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = paymentMethod.uppercase(),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Total a pagar
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color(0xFFFFF9C4)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "TOTAL A PAGAR",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "\$${total}",
                            style = MaterialTheme.typography.h4,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006400)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Campo para ingresar monto
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Ingrese el monto a pagar",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "El monto debe ser igual o mayor al total de la compra",
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = montoIngresado,
                            onValueChange = {
                                montoIngresado = it
                                errorMessage = ""
                            },
                            label = { Text("Monto \$") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black
                            ),
                            singleLine = true
                        )

                        if (errorMessage.isNotEmpty()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Instrucciones según método de pago
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp,
                    backgroundColor = Color(0xFFE3F2FD)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "ℹ️ Instrucciones:",
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        when (paymentMethod) {
                            "transferencia" -> {
                                Text(
                                    "• Realice la transferencia al banco indicado\n" +
                                    "• Ingrese el monto transferido\n" +
                                    "• Este es un sistema de pago simulado",
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Black
                                )
                            }
                            "webpay" -> {
                                Text(
                                    "• Ingrese el monto a pagar con su tarjeta\n" +
                                    "• El sistema validará el monto ingresado\n" +
                                    "• Este es un sistema de pago simulado",
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Botón Procesar Pago
                Button(
                    onClick = {
                        val monto = montoIngresado.text.toIntOrNull()
                        if (monto == null || monto <= 0) {
                            errorMessage = "Por favor ingrese un monto válido"
                        } else if (monto < total) {
                            faltante = total - monto
                            paymentSuccess = false
                            showDialog = true
                        } else {
                            paymentSuccess = true
                            cartViewModel.setPaymentAmount(monto)
                            showDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("PROCESAR PAGO", style = FuturaButtonStyle)
                }
            }
        }

        // Dialog de resultado
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = if (paymentSuccess) "✅ PAGO EXITOSO" else "❌ PAGO RECHAZADO",
                        color = if (paymentSuccess) Color(0xFF006400) else Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        if (paymentSuccess) {
                            Text(
                                "Su pago ha sido procesado exitosamente.",
                                color = Color.Black
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Monto pagado: \$${montoIngresado.text}",
                                color = Color.Black
                            )
                            if (montoIngresado.text.toInt() > total) {
                                Text(
                                    "Vuelto: \$${montoIngresado.text.toInt() - total}",
                                    color = Color(0xFF006400),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Text(
                                "El monto ingresado es insuficiente.",
                                color = Color.Black
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Total a pagar: \$${total}",
                                color = Color.Black
                            )
                            Text(
                                "Monto ingresado: \$${montoIngresado.text}",
                                color = Color.Black
                            )
                            Text(
                                "Falta: \$${faltante}",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (paymentSuccess) {
                                navController.navigate("orderConfirmation") {
                                    popUpTo("cart") { inclusive = true }
                                }
                            } else {
                                showDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (paymentSuccess) Color(0xFF006400) else Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(if (paymentSuccess) "VER CONFIRMACIÓN" else "INTENTAR DE NUEVO")
                    }
                },
                backgroundColor = Color.White
            )
        }
    }
}

