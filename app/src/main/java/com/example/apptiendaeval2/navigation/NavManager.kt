package com.example.apptiendaval2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaval2.view.LoginScreen
import com.example.apptiendaval2.view.RegisterScreen
import com.example.apptiendaval2.view.CatalogScreen
import com.example.apptiendaval2.view.ProductDetailsScreen
import com.example.apptiendaval2.view.CartScreen
import com.example.apptiendaval2.view.SuccessScreen
import com.example.apptiendaval2.view.ErrorScreen
import com.example.apptiendaval2.view.BackOfficeScreen
import com.example.apptiendaval2.view.AddProductScreen
import com.example.apptiendaval2.view.HomeScreen
import com.example.apptiendaval2.view.CheckoutScreen

@Composable
fun NavManager(navController: NavHostController, cartViewModel: CartViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("catalog") { CatalogScreen(navController, cartViewModel = cartViewModel) }
        composable("home") { HomeScreen(navController) }
        composable("checkout") { CheckoutScreen(navController, cartViewModel) }

        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailsScreen(navController, productId = id, cartViewModel = cartViewModel)
        }
        composable("cart") { CartScreen(navController, cartViewModel = cartViewModel) }
        composable("success") { SuccessScreen(navController) }
        composable("error") { ErrorScreen(navController) }
        composable("backoffice") { BackOfficeScreen(navController) }
        composable("addProduct") { AddProductScreen(navController) }
    }
}
