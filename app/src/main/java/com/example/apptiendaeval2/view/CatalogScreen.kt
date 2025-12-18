package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.apptiendaeval2.utils.CurrencyFormatter
import com.example.apptiendaeval2.ui.theme.CrimeWaveTitle
import com.example.apptiendaeval2.ui.theme.FuturaProductTitle
import com.example.apptiendaeval2.ui.theme.FuturaPrice
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.Home

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

    // Estado para el menú desplegable del usuario
    var showUserMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CATÁLOGO",
                        style = CrimeWaveTitle,
                        color = Color.White
                    )
                },
                actions = {
                    // Icono de Casa (Inicio)
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.White
                        )
                    }

                    // Icono de Carrito
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )
                    }

                    // Icono de Usuario con menú desplegable
                    Box {
                        IconButton(onClick = { showUserMenu = !showUserMenu }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = showUserMenu,
                            onDismissRequest = { showUserMenu = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                showUserMenu = false
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }) {
                                Text("Cerrar Sesión")
                            }
                        }
                    }
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
                                                style = FuturaProductTitle,
                                                maxLines = 1
                                            )

                                            Text(
                                                text = CurrencyFormatter.formatChileanPesos(p.precio ?: 0),
                                                style = FuturaPrice,
                                                color = Color(0xFF006400)
                                            )

                                            Text(
                                                text = p.descripcion ?: "Sin descripción",
                                                style = MaterialTheme.typography.body2,
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
                                                Text("Agregar al carrito", style = FuturaButtonStyle)
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
