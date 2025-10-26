package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.apptiendaval2.model.ProductRepository
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.R
import androidx.compose.ui.layout.ContentScale

@Composable
fun CatalogScreen(navController: NavController, cartViewModel: CartViewModel) {
    val productos = ProductRepository.getAll() // Productos del repositorio

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo igual al Login
        Image(
            painter = painterResource(id = R.drawable.fondo_godines),
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

        // Contenido del catálogo
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(productos.size) { i ->
                val p = productos[i]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("productDetails/${p.id}") },
                    elevation = 4.dp
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = painterResource(p.imagenResId),
                            contentDescription = p.nombre,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(p.nombre, style = MaterialTheme.typography.h6)
                            Text("Precio: $${p.precio}", style = MaterialTheme.typography.body1)
                            Text(p.descripcion, style = MaterialTheme.typography.body2)
                        }
                    }
                }
            }
        }
    }
}
