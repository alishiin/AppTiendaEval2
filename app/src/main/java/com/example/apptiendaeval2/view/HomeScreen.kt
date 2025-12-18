package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import com.example.apptiendaeval2.R
import com.example.apptiendaeval2.ui.theme.CrimeWaveTitle
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun HomeScreen(navController: NavController) {
    // Estado para el menú desplegable del usuario
    var showUserMenu by remember { mutableStateOf(false) }

    val productos = listOf(
        Pair("GuilirRecs", R.drawable.polera_negra),
        Pair("Vegetta777", R.drawable.polera_azul),
        Pair("FarganxX", R.drawable.poleron_gris)
    )

    val productosNombres = listOf(
        "Polera Negra Forever",
        "Polera Azul Los Angeles",
        "Poleron Cropped Gris"
    )

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
                .background(Color.White.copy(alpha = 0.3f))
        )

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = "CRIMEWAVE",
                        style = CrimeWaveTitle,
                        color = Color.White
                    )
                },
                actions = {
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
                backgroundColor = Color.Black,
                contentColor = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("catalog") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "IR A COMPRAR",
                    style = FuturaButtonStyle
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para ver Animes
            Button(
                onClick = { navController.navigate("animeList") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "ANIMES",
                    style = FuturaButtonStyle
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "PRODUCTOS MÁS VALORADOS",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(productos.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = productos[index].second),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(end = 8.dp)
                                )
                                Text(
                                    productos[index].first,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                productosNombres[index],
                                style = MaterialTheme.typography.body1,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = productos[index].second),
                                contentDescription = productosNombres[index],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}



