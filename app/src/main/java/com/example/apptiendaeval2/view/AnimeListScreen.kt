package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.apptiendaeval2.model.Anime
import com.example.apptiendaval2.viewmodel.AnimeViewModel

@Composable
fun AnimeListScreen(
    navController: NavController,
    animeViewModel: AnimeViewModel = viewModel()
) {
    val animes by animeViewModel.animes.collectAsState()
    val isLoading by animeViewModel.isLoading.collectAsState()
    val errorMessage by animeViewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearch) {
                        TextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                animeViewModel.searchAnimes(it)
                            },
                            placeholder = { Text("Buscar anime...", color = Color.White.copy(0.7f)) },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                backgroundColor = Color.Transparent,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "ANIMES",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (showSearch) {
                            showSearch = false
                            searchQuery = ""
                            animeViewModel.fetchTopAnimes()
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo
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

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = errorMessage ?: "Error desconocido",
                                color = Color.Red,
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { animeViewModel.fetchTopAnimes() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("REINTENTAR")
                            }
                        }
                    }
                }
                animes.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron animes",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(animes) { anime ->
                            AnimeCard(
                                anime = anime,
                                onClick = {
                                    animeViewModel.selectAnime(anime)
                                    navController.navigate("animeDetails/${anime.malId}")
                                }
                            )
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeCard(anime: Anime, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White.copy(alpha = 0.95f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Imagen del anime
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier.size(120.dp, 160.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        anime.images?.jpg?.imageUrl ?: ""
                    ),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(12.dp))

            // Información del anime
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = anime.title ?: "Sin título",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                // Calificación
                if (anime.score != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "${anime.score}",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = " / 10",
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Tipo y episodios
                Row {
                    anime.type?.let {
                        Chip(text = it, backgroundColor = Color(0xFF2196F3))
                        Spacer(Modifier.width(4.dp))
                    }
                    anime.episodes?.let {
                        Chip(text = "$it eps", backgroundColor = Color(0xFF4CAF50))
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Estado
                anime.status?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Synopsis corto
                anime.synopsis?.let { synopsis ->
                    Text(
                        text = synopsis,
                        style = MaterialTheme.typography.caption,
                        color = Color.DarkGray,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun Chip(text: String, backgroundColor: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

