package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.apptiendaeval2.model.ProductRepository
import com.example.apptiendaeval2.model.Categoria
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.R
import androidx.compose.ui.layout.ContentScale
import com.example.apptiendaeval2.ui.theme.FuturaProductTitle
import com.example.apptiendaeval2.ui.theme.FuturaCategoryButton

@Composable
fun CatalogScreen(navController: NavController, cartViewModel: CartViewModel) {
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }

    val productos by remember {
        derivedStateOf {
            val category = selectedCategory
            when (category) {
                null -> ProductRepository.getAll().take(15)
                else -> ProductRepository.getByCategory(category)
            }
        }
    }

    val categorias = remember { ProductRepository.getCategories() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CATÁLOGO DE PRODUCTOS",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("INICIO", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate("cart") }) {
                        Text("CARRITO", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("CERRAR SESIÓN", color = Color.White)
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo_godines),
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
                    .padding(8.dp)
            ) {
                LazyRow(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Button(
                            onClick = { selectedCategory = null },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedCategory == null) Color.Black else Color.Gray
                            )
                        ) {
                            Text(
                                text = "TODOS",
                                color = Color.White,
                                style = FuturaCategoryButton
                            )
                        }
                    }
                    items(categorias.size) { index ->
                        val categoria = categorias[index]
                        Button(
                            onClick = { selectedCategory = categoria },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedCategory == categoria) Color.Black else Color.Gray
                            )
                        ) {
                            Text(
                                text = categoria.displayName.uppercase(),
                                color = Color.White,
                                style = FuturaCategoryButton
                            )
                        }
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        count = productos.size,
                        key = { index -> productos[index].id }
                    ) { i ->
                        val p = productos[i]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("productDetails/${p.id}") },
                            elevation = 2.dp
                        ) {
                            Row(modifier = Modifier.padding(6.dp)) {
                                Image(
                                    painter = painterResource(p.imagenResId),
                                    contentDescription = p.nombre,
                                    modifier = Modifier.size(50.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(8.dp))
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = p.nombre.uppercase(),
                                        style = MaterialTheme.typography.subtitle1,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "\$${p.precio}",
                                        style = MaterialTheme.typography.body2,
                                        color = Color(0xFF006400)
                                    )
                                    Text(
                                        text = p.descripcion.uppercase(),
                                        style = MaterialTheme.typography.caption,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
