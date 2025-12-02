package com.example.apptiendaval2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
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



// Funciones de validación
fun isValidEmail(email: String) = email.contains("@") && email.contains(".") &&
        email.indexOf("@") > 0 &&
        email.lastIndexOf(".") > email.indexOf("@") &&
        email.lastIndexOf(".") < email.length - 1

fun isValidPassword(password: String) = password.length >= 6 &&
        password.any { it.isDigit() } && password.any { it.isLetter() }

fun isValidName(name: String) = name.trim().length >= 2 &&
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    colors = blackFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    colors = blackFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    colors = blackFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    colors = blackFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                // Mostrar errores del ViewModel o locales
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                }
                if (!error.isNullOrEmpty()) {
                    Text(error!!, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        errorMessage = ""
                        when {
                            nombre.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                                errorMessage = "Todos los campos son obligatorios"
                            !isValidEmail(email) -> errorMessage = "Email inválido"
                            !isValidPassword(password) -> errorMessage = "Contraseña inválida"
                            password != confirmPassword -> errorMessage = "Las contraseñas no coinciden"
                            !isValidName(nombre) -> errorMessage = "Nombre inválido"
                            else -> {
                                authViewModel.register(nombre, email, password)
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

