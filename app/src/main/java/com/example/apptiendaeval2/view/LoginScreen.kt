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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apptiendaeval2.R
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle
import com.example.apptiendaval2.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val user by authViewModel.user.collectAsState()
    val error by authViewModel.error.collectAsState()
    val loading by authViewModel.loading.collectAsState()

    // Navegar si login correcto
    LaunchedEffect(user) {
        user?.let {
            if (it.email == "admin@tienda.cl") navController.navigate("backoffice")
            else navController.navigate("home")
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
                    "INICIO DE SESIÓN",
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

                if (!error.isNullOrEmpty()) {
                    Text(error!!, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) return@Button
                        authViewModel.login(email, password)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "INGRESAR",
                            style = FuturaButtonStyle
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                TextButton(onClick = { navController.navigate("register") }) {
                    Text(
                        text = "¿NO TIENES CUENTA? REGÍSTRATE",
                        color = Color.Black,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}
