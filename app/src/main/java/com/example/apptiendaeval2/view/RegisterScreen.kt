package com.example.apptiendaval2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apptiendaeval2.R
import com.example.apptiendaval2.viewmodel.AuthViewModel
import com.example.apptiendaeval2.utils.RutValidator

// Funciones de validación
private fun isValidEmail(email: String) = email.contains("@") && email.contains(".") &&
        email.indexOf("@") > 0 &&
        email.lastIndexOf(".") > email.indexOf("@") &&
        email.lastIndexOf(".") < email.length - 1

private fun isValidPassword(password: String) = password.length >= 6 &&
        password.any { it.isDigit() } && password.any { it.isLetter() }

private fun isValidName(name: String) = name.trim().length >= 2 &&
        name.trim().matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }           // ✅ Campo RUT
    var direccion by remember { mutableStateOf("") }     // ✅ Campo Dirección
    var errorMessage by remember { mutableStateOf("") }

    val user by authViewModel.user.collectAsState()
    val loading by authViewModel.loading.collectAsState()
    val error by authViewModel.error.collectAsState()

    // Navegar si registro correcto
    LaunchedEffect(user) {
        user?.let {
            navController.navigate("login")
        }
    }

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

        // Usar LazyColumn para que sea scrolleable
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.6f))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Registro de Usuario",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(16.dp))

                    val blackFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color.Black,
                        textColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )

                    // Campo Nombre
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // Campo Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // ✅ Campo RUT
                    OutlinedTextField(
                        value = rut,
                        onValueChange = { rut = it },
                        label = { Text("RUT (12345678-9)") },
                        placeholder = { Text("12345678-9") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // ✅ Campo Dirección
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        placeholder = { Text("Av. Siempre Viva 123") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // Campo Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // Campo Confirmar Contraseña
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar Contraseña") },
                        colors = blackFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))

                    // Mostrar errores
                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage, color = Color.Red)
                        Spacer(Modifier.height(8.dp))
                    }
                    if (!error.isNullOrEmpty()) {
                        Text(error!!, color = Color.Red)
                        Spacer(Modifier.height(8.dp))
                    }

                    // Botón de Registro
                    Button(
                        onClick = {
                            errorMessage = ""
                            when {
                                nombre.isBlank() || email.isBlank() || password.isBlank() ||
                                        confirmPassword.isBlank() || rut.isBlank() || direccion.isBlank() ->
                                    errorMessage = "Todos los campos son obligatorios"
                                !isValidName(nombre) -> errorMessage = "Nombre inválido (mínimo 2 caracteres, solo letras)"
                                !isValidEmail(email) -> errorMessage = "Email inválido"
                                !RutValidator.isValidRut(rut) -> errorMessage = "RUT inválido. Verifica el dígito verificador"
                                direccion.trim().length < 5 -> errorMessage = "Dirección muy corta (mínimo 5 caracteres)"
                                !isValidPassword(password) -> errorMessage = "Contraseña inválida (mínimo 6 caracteres, letras y números)"
                                password != confirmPassword -> errorMessage = "Las contraseñas no coinciden"
                                else -> {
                                    // ✅ Formatear RUT antes de enviar
                                    val formattedRut = RutValidator.formatRut(rut)
                                    authViewModel.register(nombre, email, password, formattedRut, direccion, "")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        if (loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        } else {
                            Text("Registrar y volver al Login")
                        }
                    }
                }
            }
        }
    }
}

