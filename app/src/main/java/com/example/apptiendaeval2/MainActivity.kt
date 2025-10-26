package com.example.apptiendaval2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.apptiendaval2.navigation.NavManager
import com.example.apptiendaval2.viewmodel.CartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val cartViewModel: CartViewModel = viewModel()
            NavManager(navController, cartViewModel)
        }
    }
}
