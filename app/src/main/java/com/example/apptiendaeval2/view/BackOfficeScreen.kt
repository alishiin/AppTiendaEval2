package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.apptiendaeval2.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptiendaval2.viewmodel.AdminViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.graphics.Color
import com.example.apptiendaeval2.ui.theme.CrimeWaveTitle

@Composable
fun BackOfficeScreen(navController: NavController, adminViewModel: AdminViewModel = viewModel()) {

    // Estado para el menú desplegable del usuario
    var showUserMenu by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        adminViewModel.fetchProductos()
    }

    val productos by adminViewModel.productos.collectAsState()
    val loading by adminViewModel.loading.collectAsState()
    val error by adminViewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CRIMEWAVE",
                        style = CrimeWaveTitle,
                        color = Color.White
                    )
                },
                actions = {
                    // Icono de Agregar Producto (+)
                    IconButton(onClick = { navController.navigate("addProduct") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Producto",
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
                backgroundColor = Color.Black,
                contentColor = Color.White
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

                if (loading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (error != null) {
                    item {
                        Text("Error: $error", color = Color.Red)
                    }
                }

                items(productos) { producto ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Imagen desde URL si existe
                            val painter = rememberAsyncImagePainter(
                                producto.imagenUrl ?: android.R.drawable.ic_menu_gallery
                            )

                            Image(
                                painter = painter,
                                contentDescription = producto.nombre ?: "Producto",
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = producto.nombre ?: "Sin nombre",
                                    style = MaterialTheme.typography.subtitle1,
                                    maxLines = 1
                                )

                                Text(
                                    text = "\$${producto.precio ?: 0}",
                                    style = MaterialTheme.typography.body2,
                                    color = Color(0xFF006400)
                                )

                                Text(
                                    text = producto.categoria?.displayName ?: "",
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Gray
                                )
                            }

                            // Botones de acción: Editar / Eliminar
                            Column {
                                TextButton(onClick = {
                                    producto.id?.let { navController.navigate("addProduct/$it") }
                                }) { Text("Editar") }

                                TextButton(onClick = {
                                    producto.id?.let { adminViewModel.deleteProducto(it) }
                                }) { Text("Eliminar", color = Color.Red) }
                            }
                        }
                    }
                }
            }
        }
    }
}
