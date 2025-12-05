package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun PaymentMethodScreen(navController: NavController, cartViewModel: CartViewModel) {
    var selectedMethod by remember { mutableStateOf("") }

    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { (it.producto.precio ?: 0) * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MÉTODO DE PAGO",
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Seleccione un método de pago",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(16.dp))

                        // Opción Transferencia
                        PaymentMethodOption(
                            title = "TRANSFERENCIA BANCARIA",
                            description = "Pago mediante transferencia electrónica",
                            isSelected = selectedMethod == "transferencia",
                            onClick = { selectedMethod = "transferencia" }
                        )

                        Spacer(Modifier.height(12.dp))

                        // Opción Webpay
                        PaymentMethodOption(
                            title = "WEBPAY",
                            description = "Pago con tarjetas de crédito o débito",
                            isSelected = selectedMethod == "webpay",
                            onClick = { selectedMethod = "webpay" }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Resumen
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "TOTAL A PAGAR",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "\$${total}",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006400)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Botón Continuar
                Button(
                    onClick = {
                        if (selectedMethod.isNotEmpty()) {
                            cartViewModel.setPaymentMethod(selectedMethod)
                            navController.navigate("payment")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedMethod.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White,
                        disabledBackgroundColor = Color.Gray
                    )
                ) {
                    Text("CONTINUAR", style = FuturaButtonStyle)
                }
            }
        }
    }
}

@Composable
fun PaymentMethodOption(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color(0xFF006400) else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        elevation = if (isSelected) 8.dp else 2.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (isSelected) Color(0xFFE8F5E9) else Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Seleccionado",
                    tint = Color(0xFF006400),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

