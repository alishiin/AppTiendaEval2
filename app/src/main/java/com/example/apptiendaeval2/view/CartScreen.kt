package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.apptiendaval2.viewmodel.CartViewModel
import com.example.apptiendaval2.viewmodel.CartItem
import com.example.apptiendaeval2.ui.theme.FuturaProductTitle
import com.example.apptiendaeval2.ui.theme.FuturaPrice
import com.example.apptiendaeval2.ui.theme.FuturaButtonStyle

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = com.example.apptiendaeval2.R.drawable.fondo_godines),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.35f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("TU CARRITO ESTÁ VACÍO", style = MaterialTheme.typography.h5, color = Color.Black)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("catalog") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("VOLVER A LA TIENDA", style = FuturaButtonStyle, color = Color.White)
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems.size) { index ->
                        val item = cartItems[index]
                        CartItemRow(item, onQuantityChange = { newQty ->
                            cartViewModel.updateQuantity(item.producto.id, newQty)
                        })
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "TOTAL: \$${total}",
                    style = FuturaPrice,
                    color = Color(0xFF006400),
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { cartViewModel.clear() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("VACIAR", style = MaterialTheme.typography.button, color = Color.Black)
                    }
                    Button(
                        onClick = {
                            val totalItems = cartItems.sumOf { it.cantidad }
                            if (totalItems > 15) {
                                navController.navigate("error")
                            } else {
                                if (total > 0) {
                                    navController.navigate("checkout")
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White)
                    ) {
                        Text("FINALIZAR COMPRA", style = FuturaButtonStyle)
                    }

                }
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onQuantityChange: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.producto.imagenResId),
                contentDescription = item.producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(70.dp)
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.producto.nombre.uppercase(), style = FuturaProductTitle, color = Color.Black)
                Text("PRECIO: \$${item.producto.precio}", style = FuturaPrice, color = Color(0xFF006400))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = { if (item.cantidad > 1) onQuantityChange(item.cantidad - 1) },
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) { Text("-", style = MaterialTheme.typography.button, color = Color.Black) }

                    Text(text = item.cantidad.toString(), style = MaterialTheme.typography.h6, modifier = Modifier.padding(horizontal = 8.dp), color = Color.Black)

                    OutlinedButton(
                        onClick = { onQuantityChange(item.cantidad + 1) },
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) { Text("+", style = MaterialTheme.typography.button, color = Color.Black) }
                }
            }
        }
    }
}
