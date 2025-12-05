package com.example.apptiendaeval2.network

import com.example.apptiendaeval2.model.Anime
import com.example.apptiendaeval2.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {

    // Obtener top animes
    @GET("v4/top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse

    // Obtener animes por temporada actual
    @GET("v4/seasons/now")
    suspend fun getCurrentSeasonAnime(
        @Query("page") page: Int = 1
    ): AnimeResponse

    // Buscar animes
    @GET("v4/anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse

    // Obtener detalles de un anime específico
    @GET("v4/anime/{id}/full")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): AnimeDetailResponse
}

// Respuesta para detalles completos
data class AnimeDetailResponse(
    val data: Anime
)

