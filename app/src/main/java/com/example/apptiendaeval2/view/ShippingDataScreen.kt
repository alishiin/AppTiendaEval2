package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle
import com.example.apptiendaeval2.utils.ChileData

@Composable
fun ShippingDataScreen(navController: NavController, cartViewModel: CartViewModel) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var ciudad by remember { mutableStateOf("Santiago") } // Valor por defecto
    var comuna by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }

    // Estados para los menús desplegables
    var expandedCiudad by remember { mutableStateOf(false) }
    var expandedComuna by remember { mutableStateOf(false) }

    // Obtener comunas según la ciudad seleccionada
    val comunasDisponibles = remember(ciudad) {
        ChileData.getComunasPorCiudad(ciudad)
    }

    // Actualizar comuna si cambia la ciudad
    LaunchedEffect(ciudad) {
        if (comuna.isNotEmpty() && comuna !in comunasDisponibles) {
            comuna = ""
        }
    }

    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { (it.producto.precio ?: 0) * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DATOS DE ENVÍO",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = com.example.apptiendaeval2.R.drawable.fondo_godines),
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Complete los datos de envío",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(16.dp))

                        // Campo Nombre Completo
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre Completo") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        // Campo Dirección
                        OutlinedTextField(
                            value = direccion,
                            onValueChange = { direccion = it },
                            label = { Text("Dirección") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        // Campo Ciudad - Menú desplegable
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = ciudad,
                                onValueChange = { },
                                label = { Text("Ciudad") },
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth(),
                                trailingIcon = {
                                    IconButton(onClick = { expandedCiudad = !expandedCiudad }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Seleccionar ciudad"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Black,
                                    disabledTextColor = Color.Black
                                )
                            )

                            DropdownMenu(
                                expanded = expandedCiudad,
                                onDismissRequest = { expandedCiudad = false },
                                modifier = Modifier.fillMaxWidth(0.88f)
                            ) {
                                ChileData.ciudadesChile.forEach { ciudadOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            ciudad = ciudadOption
                                            expandedCiudad = false
                                        }
                                    ) {
                                        Text(ciudadOption, color = Color.Black)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Campo Comuna - Menú desplegable dinámico
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = comuna,
                                onValueChange = { },
                                label = { Text("Comuna") },
                                readOnly = true,
                                placeholder = { Text("Selecciona una comuna") },
                                modifier = Modifier.fillMaxWidth(),
                                trailingIcon = {
                                    IconButton(onClick = { expandedComuna = !expandedComuna }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Seleccionar comuna"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Black,
                                    disabledTextColor = Color.Black
                                )
                            )

                            DropdownMenu(
                                expanded = expandedComuna,
                                onDismissRequest = { expandedComuna = false },
                                modifier = Modifier.fillMaxWidth(0.88f)
                            ) {
                                comunasDisponibles.forEach { comunaOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            comuna = comunaOption
                                            expandedComuna = false
                                        }
                                    ) {
                                        Text(comunaOption, color = Color.Black)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Campo Teléfono
                        OutlinedTextField(
                            value = telefono,
                            onValueChange = { telefono = it },
                            label = { Text("Teléfono de Contacto") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black
                            )
                        )

                        if (errorMessage.isNotEmpty()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Resumen del pedido
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    backgroundColor = Color.White.copy(alpha = 0.95f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "RESUMEN DEL PEDIDO",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Total de productos: ${cartItems.sumOf { it.cantidad }}",
                            color = Color.Black
                        )
                        Text(
                            "Total a pagar: \$${total}",
                            style = MaterialTheme.typography.h6,
                            color = Color(0xFF006400)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Botón Continuar
                Button(
                    onClick = {
                        errorMessage = ""
                        when {
                            nombre.text.trim().isEmpty() ->
                                errorMessage = "El nombre es obligatorio"
                            nombre.text.trim().length < 3 ->
                                errorMessage = "El nombre debe tener al menos 3 caracteres"
                            !nombre.text.trim().matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) ->
                                errorMessage = "El nombre solo debe contener letras"
                            direccion.text.trim().isEmpty() ->
                                errorMessage = "La dirección es obligatoria"
                            direccion.text.trim().length < 5 ->
                                errorMessage = "La dirección debe tener al menos 5 caracteres"
                            ciudad.isEmpty() ->
                                errorMessage = "Debe seleccionar una ciudad"
                            comuna.isEmpty() ->
                                errorMessage = "Debe seleccionar una comuna"
                            telefono.text.trim().isEmpty() ->
                                errorMessage = "El teléfono es obligatorio"
                            !telefono.text.trim().matches(Regex("^[0-9+\\-() ]+$")) ->
                                errorMessage = "El teléfono solo debe contener números"
                            telefono.text.trim().replace(Regex("[^0-9]"), "").length < 8 ->
                                errorMessage = "El teléfono debe tener al menos 8 dígitos"
                            else -> {
                                errorMessage = ""
                                // Guardar datos de envío en el ViewModel
                                cartViewModel.setShippingData(
                                    nombre = nombre.text.trim(),
                                    direccion = direccion.text.trim(),
                                    ciudad = ciudad,
                                    comuna = comuna,
                                    telefono = telefono.text.trim()
                                )
                                navController.navigate("paymentMethod")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("CONTINUAR AL PAGO", style = FuturaButtonStyle)
                }
            }
        }
    }
}
