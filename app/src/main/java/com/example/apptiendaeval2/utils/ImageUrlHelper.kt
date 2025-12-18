package com.example.apptiendaeval2.utils

/**
 * Utilidades para manejar URLs de imágenes de productos
 */
object ImageUrlHelper {
    private const val BASE_URL = "https://api-moviles-mg5l.onrender.com"

    /**
     * Obtiene la URL completa de la imagen de un producto
     * @param productId ID del producto
     * @return URL completa para cargar la imagen
     */
    fun getProductImageUrl(productId: Long?): String? {
        return if (productId != null) {
            "$BASE_URL/api/products/$productId/imagen"
        } else {
            null
        }
    }

    /**
     * Obtiene la URL de imagen con fallback
     * @param productId ID del producto
     * @param imagenUrl URL alternativa (si existe)
     * @return URL para cargar
     */
    fun getImageUrlOrDefault(productId: Long?, imagenUrl: String?): String {
        // Si hay imagenUrl y ya es una URL completa, usarla
        if (!imagenUrl.isNullOrBlank() && imagenUrl.startsWith("http")) {
            return imagenUrl
        }

        // Si hay ID, usar endpoint de la API
        return productId?.let {
            "$BASE_URL/api/products/$it/imagen"
        } ?: ""
    }
}

