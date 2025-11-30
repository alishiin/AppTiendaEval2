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
import androidx.navigation.NavController
import com.example.apptiendaeval2.model.User
import com.example.apptiendaeval2.model.UserRepository
import com.example.apptiendaeval2.R

// Funciones de validación
fun isValidEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".") &&
           email.indexOf("@") > 0 &&
           email.lastIndexOf(".") > email.indexOf("@") &&
           email.lastIndexOf(".") < email.length - 1
}

fun isValidRut(rut: String): Boolean {
    if (rut.isBlank()) return false

    // Limpiar RUT: remover puntos, guiones y espacios
    val cleanRut = rut.replace(".", "").replace("-", "").replace(" ", "").uppercase()

    // Validar longitud mínima (7-8 dígitos + 1 dígito verificador)
    if (cleanRut.length < 8 || cleanRut.length > 9) return false

    // Separar número y dígito verificador
    val rutNumber = cleanRut.dropLast(1)
    val dv = cleanRut.last()

    // Validar que el número sea solo dígitos
    if (!rutNumber.all { it.isDigit() }) return false

    // Validar que el RUT tenga al menos 1 millón (formato real chileno)
    val rutInt = rutNumber.toIntOrNull() ?: return false
    if (rutInt < 1000000) return false

    // Calcular dígito verificador
    var suma = 0
    var multiplicador = 2

    for (i in rutNumber.length - 1 downTo 0) {
        suma += rutNumber[i].digitToInt() * multiplicador
        multiplicador++
        if (multiplicador > 7) multiplicador = 2
    }

    val resto = suma % 11
    val dvCalculado = when (resto) {
        0 -> '0'
        1 -> 'K'
        else -> (11 - resto + '0'.code).toChar()
    }

    return dv == dvCalculado
}

fun isValidName(name: String): Boolean {
    return name.trim().length >= 2 && name.trim().matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))
}

fun isValidAddress(address: String): Boolean {
    return address.trim().length >= 5
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6 && password.any { it.isDigit() } && password.any { it.isLetter() }
}

@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Estados de validación en tiempo real
    var nombreError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var direccionError by remember { mutableStateOf("") }
    var rutError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

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
                // Campo Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = when {
                            it.isBlank() -> ""
                            !isValidName(it) -> "Nombre debe tener al menos 2 caracteres y solo letras"
                            else -> ""
                        }
                    },
                    label = { Text("Nombre completo") },
                    colors = blackFieldColors,
                    isError = nombreError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (nombreError.isNotEmpty()) {
                    Text(nombreError, color = Color.Red, style = MaterialTheme.typography.caption)
                }

                Spacer(Modifier.height(8.dp))

                // Campo Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = when {
                            it.isBlank() -> ""
                            !isValidEmail(it) -> "Email debe contener @ y un punto válido"
                            else -> ""
                        }
                    },
                    label = { Text("Email") },
                    colors = blackFieldColors,
                    isError = emailError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError.isNotEmpty()) {
                    Text(emailError, color = Color.Red, style = MaterialTheme.typography.caption)
                }

                Spacer(Modifier.height(8.dp))

                // Campo Dirección
                OutlinedTextField(
                    value = direccion,
                    onValueChange = {
                        direccion = it
                        direccionError = when {
                            it.isBlank() -> ""
                            !isValidAddress(it) -> "Dirección debe tener al menos 5 caracteres"
                            else -> ""
                        }
                    },
                    label = { Text("Dirección") },
                    colors = blackFieldColors,
                    isError = direccionError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (direccionError.isNotEmpty()) {
                    Text(direccionError, color = Color.Red, style = MaterialTheme.typography.caption)
                }

                Spacer(Modifier.height(8.dp))

                // Campo RUT
                OutlinedTextField(
                    value = rut,
                    onValueChange = {
                        rut = it
                        rutError = when {
                            it.isBlank() -> ""
                            !isValidRut(it) -> "RUT inválido (ej: 12.345.678-9)"
                            else -> ""
                        }
                    },
                    label = { Text("RUT") },
                    colors = blackFieldColors,
                    isError = rutError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (rutError.isNotEmpty()) {
                    Text(rutError, color = Color.Red, style = MaterialTheme.typography.caption)
                }

                Spacer(Modifier.height(8.dp))

                // Campo Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = when {
                            it.isBlank() -> ""
                            !isValidPassword(it) -> "Contraseña debe tener al menos 6 caracteres, letras y números"
                            else -> ""
                        }
                        // Revalidar confirmación si ya se ingresó
                        if (confirmPassword.isNotBlank()) {
                            confirmPasswordError = if (it != confirmPassword) "Las contraseñas no coinciden" else ""
                        }
                    },
                    label = { Text("Contraseña") },
                    colors = blackFieldColors,
                    isError = passwordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (passwordError.isNotEmpty()) {
                    Text(passwordError, color = Color.Red, style = MaterialTheme.typography.caption)
                }

                Spacer(Modifier.height(8.dp))

                // Campo Confirmar Contraseña
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = when {
                            it.isBlank() -> ""
                            it != password -> "Las contraseñas no coinciden"
                            else -> ""
                        }
                    },
                    label = { Text("Confirmar Contraseña") },
                    colors = blackFieldColors,
                    isError = confirmPasswordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (confirmPasswordError.isNotEmpty()) {
                    Text(confirmPasswordError, color = Color.Red, style = MaterialTheme.typography.caption)
                }
                Spacer(Modifier.height(16.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        errorMessage = ""

                        // Validar todos los campos
                        val hasErrors = listOf(nombreError, emailError, direccionError, rutError, passwordError, confirmPasswordError)
                            .any { it.isNotEmpty() }

                        when {
                            nombre.isBlank() || email.isBlank() || direccion.isBlank() || rut.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                                errorMessage = "Todos los campos son obligatorios"
                            hasErrors -> errorMessage = "Por favor corrige los errores antes de continuar"
                            !isValidName(nombre) -> errorMessage = "Nombre inválido"
                            !isValidEmail(email) -> errorMessage = "Email inválido"
                            !isValidAddress(direccion) -> errorMessage = "Dirección inválida"
                            !isValidRut(rut) -> errorMessage = "RUT inválido"
                            !isValidPassword(password) -> errorMessage = "Contraseña inválida"
                            password != confirmPassword -> errorMessage = "Las contraseñas no coinciden"
                            else -> {
                                UserRepository.addUser(User(nombre, email, password))
                                navController.navigate("login")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Registrar y volver al Login")
                }
            }
        }
    }
}
