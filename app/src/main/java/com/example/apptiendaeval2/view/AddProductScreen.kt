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

@Composable
fun AddProductScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("POLERAS") }
    var tallas by remember { mutableStateOf("S,M,L,XL") }
    var medidas by remember { mutableStateOf("") }
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto", color = Color.Black) },
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
                Box {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Categoría") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        colors = blackFieldColors,
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown"
                            )
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
                            // Aquí iría la lógica para agregar el producto
                            navController.navigate("backoffice")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("AGREGAR PRODUCTO (VISUAL)")
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
}
