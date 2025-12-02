package com.example.apptiendaval2.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptiendaval2.viewmodel.AdminViewModel
import com.example.apptiendaeval2.model.Producto

@Composable
fun AddProductScreen(
    navController: NavController,
    adminViewModel: AdminViewModel = viewModel(),
    productId: Long? = null
) {
    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("POLERAS") }
    var tallas by remember { mutableStateOf("S,M,L,XL") }
    var medidasText by remember { mutableStateOf("") }
    var imagenesAdicionales by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val categorias = listOf("POLERAS", "PANTALONES", "POLERONES")

    val blackFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        cursorColor = Color.Black,
        textColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black
    )

    // Estado del ViewModel
    val loading by adminViewModel.loading.collectAsState()
    val error by adminViewModel.error.collectAsState()

    // Coleccionar la lista de productos para poder precargar el producto por id
    val productos by adminViewModel.productos.collectAsState()

    // Flag para evitar reescribir campos si el usuario ya empezó a editar
    val prefilledState = remember { mutableStateOf(false) }

    // Si estamos editando, prellenar campos cuando el producto esté disponible.
    // Si aún no está en la lista, lanzar fetchProductos() y esperar a que llegue.
    LaunchedEffect(productId, productos) {
        if (productId != null && !prefilledState.value) {
            var prod = adminViewModel.getProductoById(productId)
            if (prod == null) {
                // Intentar obtener la lista desde el backend
                adminViewModel.fetchProductos()
            }
            // Reintentar obtener el producto (la lista podría haberse actualizado)
            prod = adminViewModel.getProductoById(productId)
            prod?.let {
                nombre = it.nombre ?: ""
                precio = (it.precio ?: 0).toString()
                descripcion = it.descripcion ?: ""
                imagenUrl = it.imagenUrl ?: ""
                // stock no está en el modelo actual del backend -> mantener campo por compatibilidad
                stock = ""
                categoria = it.categoria?.name ?: "POLERAS"
                tallas = it.tallas.joinToString(",")
                medidasText = it.medidas.joinToString(",")
                imagenesAdicionales = it.imagenesUrl.joinToString(",")
                prefilledState.value = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Agregar Producto" else "Editar Producto", color = Color.Black) },
                backgroundColor = Color.White
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "INFORMACIÓN BÁSICA",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors
                )
            }

            item {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio (sin puntos ni comas)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("Ejemplo: 15990") }
                )
            }

            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock disponible") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("Ejemplo: 25") }
                )
            }

            item {
                Text(
                    "CATEGORÍA Y TIPO",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                // Dropdown estándar: OutlinedTextField readOnly + IconButton para desplegar
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { /* readOnly */ },
                        readOnly = true,
                        label = { Text("Categoría") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        colors = blackFieldColors,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Toggle"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(onClick = {
                                categoria = cat
                                expanded = false
                            }) {
                                Text(cat)
                            }
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = tallas,
                    onValueChange = { tallas = it },
                    label = { Text("Tallas disponibles (separadas por coma)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("S,M,L,XL") }
                )
            }

            item {
                Text(
                    "IMÁGENES",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("Imagen principal (nombre del archivo)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("polera_negra") }
                )
            }

            item {
                OutlinedTextField(
                    value = imagenesAdicionales,
                    onValueChange = { imagenesAdicionales = it },
                    label = { Text("Imágenes adicionales (separadas por coma)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("polera_azul,polera_gris") }
                )
            }



            item {
                Text(
                    "INFORMACIÓN ADICIONAL",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 1.dp,
                    backgroundColor = Color.Yellow.copy(alpha = 0.1f)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            "RESUMEN DEL PRODUCTO",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("• Nombre: $nombre", color = Color.Black)
                        Text("• Precio: \$$precio", color = Color.Black)
                        Text("• Categoría: $categoria", color = Color.Black)
                        Text("• Stock: $stock unidades", color = Color.Black)
                        Text("• Tallas: $tallas", color = Color.Black)
                        Text("• Medidas: $medidasText", color = Color.Black)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            // Validaciones mínimas
                            val precioInt = precio.toIntOrNull() ?: 0

                            val producto = Producto(
                                id = if (productId != null) productId else null,
                                nombre = nombre,
                                precio = precioInt,
                                descripcion = descripcion,
                                imagenUrl = imagenUrl.ifBlank { null },
                                // categoría: intentar mapear a enum si corresponde
                                categoria = try {
                                    com.example.apptiendaeval2.model.Categoria.valueOf(categoria)
                                } catch (_: Exception) {
                                    com.example.apptiendaeval2.model.Categoria.POLERAS
                                },
                                imagenesUrl = if (imagenesAdicionales.isBlank()) emptyList() else imagenesAdicionales.split(",").map { it.trim() },
                                tallas = if (tallas.isBlank()) listOf("S","M","L","XL") else tallas.split(",").map { it.trim() },
                                medidas = if (medidasText.isBlank()) emptyList() else medidasText.split(",").map { it.trim() }
                            )

                            if (productId == null) {
                                adminViewModel.createProducto(producto) {
                                    navController.navigate("backoffice")
                                }
                            } else {
                                adminViewModel.updateProducto(productId, producto) {
                                    navController.navigate("backoffice")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        ),
                        enabled = !loading
                    ) {
                        Text(if (productId == null) "AGREGAR PRODUCTO" else "ACTUALIZAR PRODUCTO")
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("backoffice") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text("CANCELAR")
                    }
                }
            }
        }
    }

    // Mostrar loading y errores
    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    error?.let { err ->
        // Simple snackbar inline; en un app real usa ScaffoldState
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Text(text = "Error: $err", color = Color.Red)
        }
    }
}
