package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.apptiendaval2.model.ProductRepository
import com.example.apptiendaeval2.R
import androidx.compose.ui.graphics.Color

// TopAppBar personalizado con estilo urbano
@Composable
fun MyTopAppBar(
    title: String,
    navIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.h6.copy(
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        navigationIcon = navIcon,
        actions = actions,
        backgroundColor = Color(0xFF2C2C2C), // Gris oscuro urbano
        contentColor = Color.White,
        elevation = 8.dp
    )
}

@Composable
fun BackOfficeScreen(navController: NavController) {
    val productos = ProductRepository.getAll()

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Panel admin",
                actions = {
                    TextButton(onClick = { navController.navigate("addProduct") }) {
                        Text("Agregar Producto", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo
            Image(
                painter = painterResource(id = R.drawable.fondo_godines),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Contenido encima del fondo
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = producto.imagenResId),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(producto.nombre, style = MaterialTheme.typography.h6)
                            Text("Precio: \$${producto.precio}")
                            Text("Descripción: ${producto.descripcion}")
                        }
                    }
                }
            }
        }
    }
}
