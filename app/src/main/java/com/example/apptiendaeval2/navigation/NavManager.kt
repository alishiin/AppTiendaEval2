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

@Composable
fun NavManager(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    // Creamos el ProductViewModel usando el default constructor
    val productViewModel: ProductViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(navController) }
        composable ("register"){ RegisterScreen(navController) }
        composable("catalog") { CatalogScreen(navController, cartViewModel = cartViewModel) }
        composable("home") { HomeScreen(navController) }
        composable("checkout") { CheckoutScreen(navController, cartViewModel) }

        // PRODUCT DETAILS CON VIEWMODEL REAL
        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("productId")

            if (id == null) {
                ErrorScreen(navController)
                return@composable
            }

            val productos by productViewModel.productos.collectAsState()
            val producto = productos.find { it.id.toInt() == id } // convertir a Int

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
        composable("addProduct") { AddProductScreen(navController) }
    }
}
