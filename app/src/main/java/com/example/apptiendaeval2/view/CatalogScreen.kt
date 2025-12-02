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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaval2.viewmodel.ProductViewModel
import com.example.apptiendaeval2.model.Categoria
import com.example.apptiendaeval2.R
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CatalogScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel = viewModel()
) {
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }

    val productos by productViewModel.productos.collectAsState()
    val loading by productViewModel.loading.collectAsState()
    val error by productViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.fetchProductos()
    }

    val filteredProductos = remember(productos, selectedCategory) {
        selectedCategory?.let { cat -> productos.filter { it.categoria == cat } } ?: productos
    }

    val categorias = remember(productos) {
        productos.mapNotNull { it.categoria }.distinct()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CATÁLOGO DE PRODUCTOS", color = Color.White) },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) { Text("INICIO", color = Color.White) }
                    TextButton(onClick = { navController.navigate("cart") }) { Text("CARRITO", color = Color.White) }
                    TextButton(onClick = { navController.navigate("login") }) { Text("CERRAR SESIÓN", color = Color.White) }
                },
                backgroundColor = Color.Black
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // Fondo
            Image(
                painter = rememberAsyncImagePainter(R.drawable.fondo_godines),
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
                    .padding(8.dp)
            ) {

                // 🔹 FILTRO POR CATEGORÍA
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    item {
                        Button(
                            onClick = { selectedCategory = null },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedCategory == null) Color.Black else Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text("TODOS", color = Color.White)
                        }
                    }

                    items(categorias.size) { index ->
                        val cat = categorias[index]
                        Button(
                            onClick = { selectedCategory = cat },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedCategory == cat) Color.Black else Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(cat.displayName.uppercase(), color = Color.White)
                        }
                    }
                }

                when {
                    loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Error: $error", color = Color.Red)
                        }
                    }

                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                            items(filteredProductos.size, key = { filteredProductos[it].id ?: it }) { i ->

                                val p = filteredProductos[i]

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            p.id?.let { navController.navigate("productDetails/$it") }
                                        },
                                    elevation = 2.dp
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        // ✅ IMAGEN DESDE URL CON PLACEHOLDER
                                        Image(
                                            painter = rememberAsyncImagePainter(p.imagenUrl ?: R.drawable.ic_placeholder),
                                            contentDescription = p.nombre ?: "Producto",
                                            modifier = Modifier.size(70.dp),
                                            contentScale = ContentScale.Crop
                                        )

                                        Spacer(Modifier.width(10.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = (p.nombre ?: "Sin nombre").uppercase(),
                                                style = MaterialTheme.typography.subtitle1,
                                                maxLines = 1
                                            )

                                            Text(
                                                text = "\$${p.precio ?: 0}",
                                                style = MaterialTheme.typography.body2,
                                                color = Color(0xFF006400)
                                            )

                                            Text(
                                                text = p.descripcion ?: "Sin descripción",
                                                style = MaterialTheme.typography.caption,
                                                maxLines = 1
                                            )

                                            Spacer(Modifier.height(6.dp))

                                            Button(
                                                onClick = { cartViewModel.addProduct(p) },
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color.Black,
                                                    contentColor = Color.White
                                                )
                                            ) {
                                                Text("Agregar al carrito")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
