package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptiendaval2.model.UserRepository
import com.example.apptiendaeval2.R
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.6f))
                    .padding(24.dp)
            ) {
                Text(
                    "INICIO DE SESI0N",
                    style = MaterialTheme.typography.h4,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))

                val blackFieldColors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    cursorColor = Color.Black,
                    textColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    colors = blackFieldColors
                )
                Spacer(Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = blackFieldColors
                )
                Spacer(Modifier.height(16.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        errorMessage = ""
                        when {
                            email.isBlank() || password.isBlank() ->
                                errorMessage = "Todos los campos son obligatorios"
                            !email.contains("@") || !email.contains(".") ->
                                errorMessage = "Por favor ingresa un email válido"
                            password.length < 3 ->
                                errorMessage = "Contraseña muy corta"
                            email == "admin@tienda.cl" && password == "admin123" ->
                                navController.navigate("backoffice")
                            email == "a@a.cl" && password == "123123" ->
                                navController.navigate("home")
                            UserRepository.validateUser(email, password) ->
                                navController.navigate("home")
                            else -> errorMessage = "Email o contraseña incorrectos"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "INGRESAR",
                        style = FuturaButtonStyle
                    )
                }

                Spacer(Modifier.height(8.dp))

                TextButton(onClick = { navController.navigate("register") }) {
                    Text(
                        text = "¿NO TIENES CUENTA? REGISTRATE",
                        color = Color.Black,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}
