package com.example.apptiendaval2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptiendaeval2.model.Anime
import com.example.apptiendaeval2.network.JikanRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    private val apiService = JikanRetrofitClient.apiService

    // Lista de animes
    private val _animes = MutableStateFlow<List<Anime>>(emptyList())
    val animes: StateFlow<List<Anime>> = _animes

    // Anime seleccionado para ver detalles
    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime: StateFlow<Anime?> = _selectedAnime

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Mensajes de error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchTopAnimes()
    }

    // Obtener top animes
    fun fetchTopAnimes() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.getTopAnime(page = 1, limit = 25)
                _animes.value = response.data
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar animes: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener animes de la temporada actual
    fun fetchCurrentSeasonAnimes() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.getCurrentSeasonAnime(page = 1)
                _animes.value = response.data
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar animes: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Buscar animes
    fun searchAnimes(query: String) {
        if (query.isEmpty()) {
            fetchTopAnimes()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.searchAnime(query, page = 1, limit = 25)
                _animes.value = response.data
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar animes: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener detalles completos de un anime
    fun fetchAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.getAnimeDetails(animeId)
                _selectedAnime.value = response.data
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar detalles: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Seleccionar un anime
    fun selectAnime(anime: Anime) {
        _selectedAnime.value = anime
    }

    // Limpiar selección
    fun clearSelectedAnime() {
        _selectedAnime.value = null
    }
}

