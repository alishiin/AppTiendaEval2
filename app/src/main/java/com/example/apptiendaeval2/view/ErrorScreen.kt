package com.example.apptiendaval2.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ErrorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Error en la Compra") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Hubo un problema con tu compra.",
                style = MaterialTheme.typography.h6 // <-- cambiado de headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Puede ser por falta de stock o error en el método de pago.")
            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { navController.navigate("cart") }) {
                    Text("Revisar Carrito")
                }
                OutlinedButton(onClick = { navController.navigate("catalog") }) {
                    Text("Volver al Catálogo")
                }
            }
        }
    }
}
