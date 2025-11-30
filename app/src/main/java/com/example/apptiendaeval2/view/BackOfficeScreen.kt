package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.apptiendaeval2.model.ProductRepository
import com.example.apptiendaeval2.R
import androidx.compose.ui.graphics.Color

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
        backgroundColor = Color(0xFF2C2C2C),
        contentColor = Color.White,
        elevation = 8.dp
    )
}

@Composable
fun BackOfficeScreen(navController: NavController) {
    val productos = remember { ProductRepository.getAll().take(15) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Panel admin",
                actions = {
                    TextButton(onClick = { navController.navigate("addProduct") }) {
                        Text("Agregar Producto", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("Cerrar Sesión", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo_godines),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(productos.size) { index ->
                    val producto = productos[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = producto.imagenResId),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .size(60.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = producto.nombre,
                                    style = MaterialTheme.typography.subtitle1,
                                    maxLines = 1
                                )
                                Text(
                                    text = "\$${producto.precio}",
                                    style = MaterialTheme.typography.body2,
                                    color = Color(0xFF006400)
                                )
                                Text(
                                    text = producto.categoria.displayName,
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
