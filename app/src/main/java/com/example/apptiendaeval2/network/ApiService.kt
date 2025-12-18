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

    @POST("auth/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): Response<UserResponse>

    @POST("auth/register")
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
    // ADMIN - PRODUCTOS
    // =======================

    @Multipart
    @POST("api/products")
    suspend fun createProducto(
        @Part("nombre") nombre: okhttp3.RequestBody,
        @Part("descripcion") descripcion: okhttp3.RequestBody,
        @Part("precio") precio: okhttp3.RequestBody,
        @Part("stock") stock: okhttp3.RequestBody,
        @Part("tallasDisponibles") tallasDisponibles: okhttp3.RequestBody,
        @Part imagen: okhttp3.MultipartBody.Part?,
        @Part("categoryId") categoryId: okhttp3.RequestBody? = null
    ): Response<Producto>

    @Multipart
    @PUT("api/products/{id}")
    suspend fun updateProducto(
        @Path("id") id: Long,
        @Part("nombre") nombre: okhttp3.RequestBody,
        @Part("descripcion") descripcion: okhttp3.RequestBody,
        @Part("precio") precio: okhttp3.RequestBody,
        @Part("stock") stock: okhttp3.RequestBody,
        @Part("tallasDisponibles") tallasDisponibles: okhttp3.RequestBody,
        @Part imagen: okhttp3.MultipartBody.Part?,
        @Part("categoryId") categoryId: okhttp3.RequestBody? = null
    ): Response<Producto>

    @DELETE("api/products/{id}")
    suspend fun deleteProducto(
        @Path("id") id: Long
    ): Response<Map<String, String>>

    @GET("api/products")
    suspend fun getAllAdminProductos(): Response<List<Producto>>

    @GET("api/products/{id}/imagen")
    suspend fun getProductImage(@Path("id") id: Long): Response<okhttp3.ResponseBody>

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
            // Crear OkHttpClient con interceptor para headers
            val okHttpClient = okhttp3.OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val requestWithHeaders = originalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build()
                    chain.proceed(requestWithHeaders)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
