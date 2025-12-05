package com.example.apptiendaval2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaval2.viewmodel.ProductViewModel
import com.example.apptiendaval2.view.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator

@Composable
fun NavManager(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    // Creamos el ProductViewModel usando el default constructor
    val productViewModel: ProductViewModel = viewModel()

    // Creamos el AnimeViewModel
    val animeViewModel: com.example.apptiendaval2.viewmodel.AnimeViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(navController) }
        composable ("register"){ RegisterScreen(navController) }
        composable("catalog") { CatalogScreen(navController, cartViewModel = cartViewModel) }
        composable("home") { HomeScreen(navController) }
        composable("checkout") { CheckoutScreen(navController, cartViewModel) }

        // Nuevo flujo de checkout
        composable("shippingData") { ShippingDataScreen(navController, cartViewModel) }
        composable("paymentMethod") { PaymentMethodScreen(navController, cartViewModel) }
        composable("payment") { PaymentScreen(navController, cartViewModel) }
        composable("orderConfirmation") { OrderConfirmationScreen(navController, cartViewModel) }

        // Rutas para Animes
        composable("animeList") { AnimeListScreen(navController, animeViewModel) }
        composable(
            route = "animeDetails/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AnimeDetailsScreen(navController, animeId, animeViewModel)
        }

        // PRODUCT DETAILS CON VIEWMODEL REAL (usar Long para el id)
        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->

            // Recuperar id de la ruta: obtener Long directamente si existe
            val args = backStackEntry.arguments
            val id: Long? = if (args?.containsKey("productId") == true) args.getLong("productId") else null

            if (id == null) {
                ErrorScreen(navController)
                return@composable
            }

            // Observamos la lista de productos y, si está vacía, solicitamos fetch
            val productos by productViewModel.productos.collectAsState()

            // Si la lista aún no está cargada, disparar fetch (no bloqueante) y mostrar loader
            LaunchedEffect(id) {
                if (productos.isEmpty()) {
                    productViewModel.fetchProductos()
                }
            }

            if (productos.isEmpty()) {
                // Mostrar loading hasta que los productos lleguen
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@composable
            }

            val producto = productos.find { it.id == id }

            if (producto != null) {
                ProductDetailsScreen(
                    navController = navController,
                    producto = producto,
                    cartViewModel = cartViewModel
                )
            } else {
                ErrorScreen(navController)
            }
        }

        composable("cart") { CartScreen(navController, cartViewModel = cartViewModel) }
        composable("success") { SuccessScreen(navController) }
        composable("error") { ErrorScreen(navController) }
        composable("backoffice") { BackOfficeScreen(navController) }

        // Rutas para AddProduct: creación y edición (con id Long)
        composable("addProduct") { AddProductScreen(navController) }
        composable(
            route = "addProduct/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            AddProductScreen(navController = navController, productId = id)
        }
    }
}
