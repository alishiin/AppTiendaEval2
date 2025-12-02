package com.example.apptiendaval2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import coil.compose.rememberAsyncImagePainter

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.items.collectAsState()
    val total = cartItems.sumOf { (it.producto.precio ?: 0) * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CARRITO DE COMPRAS",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                actions = {
                    TextButton(onClick = { navController.navigate("catalog") }) {
                        Text("CATÁLOGO", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("INICIO", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("CERRAR SESIÓN", color = Color.White)
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }
    ) { padding ->
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
                    .padding(padding)
                    .padding(16.dp)
            ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("TU CARRITO ESTÁ VACIO", style = MaterialTheme.typography.h5, color = Color.Black)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("catalog") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("VOLVER A LA TIENDA", style = FuturaButtonStyle, color = Color.White)
                        }
                    }
                }
            } else {
                // Agrupar items por producto
                val groupedItems = cartItems.groupBy { it.producto.id }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(groupedItems.size) { groupIndex ->
                        val productGroup = groupedItems.values.toList()[groupIndex]
                        ProductGroupCard(
                            productGroup = productGroup,
                            onQuantityChange = { talla, newQty ->
                                val productId = productGroup.first().producto.id
                                cartViewModel.updateQuantity(productId, talla, newQty)
                            }
                        )
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
}

@Composable
fun ProductGroupCard(
    productGroup: List<CartItem>,
    onQuantityChange: (String, Int) -> Unit
) {
    val producto = productGroup.first().producto

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Encabezado del producto
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(producto.imagenUrl),
                    contentDescription = producto.nombre,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre?.uppercase() ?: "SIN NOMBRE",
                        style = FuturaProductTitle,
                        color = Color.Black
                    )
                    Text(
                        text = "PRECIO: \$${producto.precio ?: 0}",
                        style = FuturaPrice,
                        color = Color(0xFF006400)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Mostrar cada talla por separado
            productGroup.forEach { item ->
                TallaRow(
                    item = item,
                    onQuantityChange = onQuantityChange
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TallaRow(
    item: CartItem,
    onQuantityChange: (String, Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.Gray.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Mostrar talla/medida
            Text(
                text = "TALLA: ${item.talla}",
                style = MaterialTheme.typography.subtitle2,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Controles de cantidad
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onQuantityChange(item.talla, item.cantidad - 1) },
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text("-", style = MaterialTheme.typography.button, color = Color.Black)
                }

                Text(
                    text = "Cant: ${item.cantidad}",
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = { onQuantityChange(item.talla, item.cantidad + 1) },
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text("+", style = MaterialTheme.typography.button, color = Color.Black)
                }
            }

            Spacer(Modifier.height(8.dp))

            // Botón de eliminar talla
            OutlinedButton(
                onClick = { onQuantityChange(item.talla, 0) },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ELIMINAR TALLA", style = MaterialTheme.typography.caption, color = Color.Red)
            }
        }
    }
}
