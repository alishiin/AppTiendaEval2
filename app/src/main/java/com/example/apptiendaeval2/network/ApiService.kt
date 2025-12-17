package com.example.apptiendaval2.network

import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaeval2.model.UserResponse
import com.example.apptiendaeval2.model.Valoracion
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    // =======================
    // AUTH
    // =======================

    @POST("api/auth/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): Response<UserResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body body: Map<String, String>
    ): Response<UserResponse>

    // =======================
    // PRODUCTS (CLIENTE)
    // =======================

    @GET("api/products")
    suspend fun getProducts(): Response<List<Producto>>

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Response<Producto>

    @GET("api/products/categoria/{categoria}")
    suspend fun getByCategoria(
        @Path("categoria") categoria: String
    ): Response<List<Producto>>

    @GET("api/products/{id}/valoraciones")
    suspend fun getValoraciones(
        @Path("id") id: Long
    ): Response<List<Valoracion>>

    @POST("api/products/{id}/valoraciones")
    suspend fun addValoracion(
        @Path("id") id: Long,
        @Body valoracion: Valoracion
    ): Response<Valoracion>

    // =======================
    // ADMIN
    // =======================

    @POST("api/admin/productos")
    suspend fun createProducto(
        @Body producto: Producto
    ): Response<Producto>

    @PUT("api/admin/productos/{id}")
    suspend fun updateProducto(
        @Path("id") id: Long,
        @Body producto: Producto
    ): Response<Producto>

    @DELETE("api/admin/productos/{id}")
    suspend fun deleteProducto(
        @Path("id") id: Long
    ): Response<Map<String, String>>

    @GET("api/admin/productos")
    suspend fun getAllAdminProductos(): Response<List<Producto>>

    // =======================
    // CART
    // =======================

    @GET("api/cart")
    suspend fun getCart(): Response<List<Producto>>

    @POST("api/cart/add/{productoId}")
    suspend fun addToCart(
        @Path("productoId") productoId: Long
    ): Response<String>

    @DELETE("api/cart/remove/{productoId}")
    suspend fun removeFromCart(
        @Path("productoId") productoId: Long
    ): Response<String>

    @DELETE("api/cart/clear")
    suspend fun clearCart(): Response<String>

    @GET("api/cart/total")
    suspend fun getTotal(): Response<Double>

    // =======================
    // COMPANION OBJECT PARA CREAR EL SERVICIO
    // =======================

    companion object {
        private const val BASE_URL = "https://api-moviles-mg5l.onrender.com/" // API AWS

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
