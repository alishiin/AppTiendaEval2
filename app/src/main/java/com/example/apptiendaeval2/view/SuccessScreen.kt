package com.example.apptiendaval2.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SuccessScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Compra Exitosa") })
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
            Text("Tu compra se ha realizado con éxito", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Gracias por tu compra. Tu pedido será procesado pronto.")
            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { navController.navigate("catalog") }) {
                    Text("Seguir Comprando")
                }
                OutlinedButton(onClick = {
                    navController.navigate("catalog") {
                        popUpTo(0)
                    }
                }) {
                    Text("Volver al Inicio")
                }
            }
        }
    }
}
