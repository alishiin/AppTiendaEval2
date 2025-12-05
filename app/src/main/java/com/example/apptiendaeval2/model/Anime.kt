package com.example.apptiendaeval2.model

import com.google.gson.annotations.SerializedName

// Modelo para la respuesta de la lista de animes
data class AnimeResponse(
    @SerializedName("data")
    val data: List<Anime>,
    @SerializedName("pagination")
    val pagination: Pagination?
)

// Modelo principal de Anime
data class Anime(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String?,
    @SerializedName("images")
    val images: AnimeImages?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("aired")
    val aired: Aired?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("scored_by")
    val scoredBy: Int?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("popularity")
    val popularity: Int?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("background")
    val background: String?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("studios")
    val studios: List<Studio>?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("duration")
    val duration: String?
)

// Imágenes del anime
data class AnimeImages(
    @SerializedName("jpg")
    val jpg: ImageUrl?,
    @SerializedName("webp")
    val webp: ImageUrl?
)

data class ImageUrl(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

// Fecha de emisión
data class Aired(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("string")
    val string: String?
)

// Género
data class Genre(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

// Studio
data class Studio(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

// Paginación
data class Pagination(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int?,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean?,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("items")
    val items: PaginationItems?
)

data class PaginationItems(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("per_page")
    val perPage: Int?
)

