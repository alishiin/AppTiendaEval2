package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.apptiendaeval2.model.Anime
import com.example.apptiendaval2.viewmodel.AnimeViewModel

@Composable
fun AnimeDetailsScreen(
    navController: NavController,
    animeId: Int,
    animeViewModel: AnimeViewModel = viewModel()
) {
    val selectedAnime by animeViewModel.selectedAnime.collectAsState()
    val isLoading by animeViewModel.isLoading.collectAsState()

    LaunchedEffect(animeId) {
        if (selectedAnime == null || selectedAnime?.malId != animeId) {
            animeViewModel.fetchAnimeDetails(animeId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedAnime?.title ?: "Detalles",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
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
                selectedAnime != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        item {
                            AnimeDetailsContent(selectedAnime!!)
                        }
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se pudieron cargar los detalles",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDetailsContent(anime: Anime) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Imagen principal y info básica
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.95f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    // Imagen
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp,
                        modifier = Modifier.size(150.dp, 220.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                anime.images?.jpg?.largeImageUrl
                                    ?: anime.images?.jpg?.imageUrl
                            ),
                            contentDescription = anime.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    // Info básica
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = anime.title ?: "Sin título",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(8.dp))

                        // Calificación
                        anime.score?.let { score ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "$score",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = " / 10",
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Gray
                                )
                            }
                        }

                        anime.scoredBy?.let {
                            Text(
                                text = "$it usuarios",
                                style = MaterialTheme.typography.caption,
                                color = Color.Gray
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // Ranking y popularidad
                        anime.rank?.let { rank ->
                            AnimeInfoRow("Ranking:", "#$rank")
                        }
                        anime.popularity?.let { pop ->
                            AnimeInfoRow("Popularidad:", "#$pop")
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Tipo, episodios, estado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    anime.type?.let {
                        Chip(text = it, backgroundColor = Color(0xFF2196F3))
                    }
                    anime.episodes?.let {
                        Chip(text = "$it Episodios", backgroundColor = Color(0xFF4CAF50))
                    }
                    anime.status?.let {
                        Chip(
                            text = it,
                            backgroundColor = when (it.lowercase()) {
                                "airing" -> Color(0xFF4CAF50)
                                "finished airing" -> Color(0xFF9E9E9E)
                                else -> Color(0xFFFF9800)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Información adicional
                anime.season?.let { season: String ->
                    AnimeInfoRow("Temporada:", "${season.replaceFirstChar { it.uppercase() }} ${anime.year ?: ""}")
                }
                anime.aired?.string?.let { aired: String ->
                    AnimeInfoRow("Emisión:", aired)
                }
                anime.duration?.let { duration: String ->
                    AnimeInfoRow("Duración:", duration)
                }
                anime.rating?.let { rating: String ->
                    AnimeInfoRow("Clasificación:", rating)
                }
            }
        }

        // Géneros
        if (!anime.genres.isNullOrEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White.copy(alpha = 0.95f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GÉNEROS",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(anime.genres) { genre ->
                            Chip(
                                text = genre.name ?: "",
                                backgroundColor = Color(0xFF9C27B0)
                            )
                        }
                    }
                }
            }
        }

        // Studios
        if (!anime.studios.isNullOrEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White.copy(alpha = 0.95f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ESTUDIOS",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(anime.studios) { studio ->
                            Chip(
                                text = studio.name ?: "",
                                backgroundColor = Color(0xFFFF5722)
                            )
                        }
                    }
                }
            }
        }

        // Sinopsis
        anime.synopsis?.let { synopsis ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White.copy(alpha = 0.95f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SINOPSIS",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = synopsis,
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray
                    )
                }
            }
        }

        // Background
        anime.background?.let { background ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White.copy(alpha = 0.95f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INFORMACIÓN ADICIONAL",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = background,
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun AnimeInfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            color = Color.Black
        )
    }
}

