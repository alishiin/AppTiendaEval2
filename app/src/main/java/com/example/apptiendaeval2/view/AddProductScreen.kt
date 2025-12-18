package com.example.apptiendaval2.view

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptiendaval2.viewmodel.AdminViewModel
import com.example.apptiendaeval2.model.Producto
import com.example.apptiendaeval2.network.ImageUploadService
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun AddProductScreen(
    navController: NavController,
    adminViewModel: AdminViewModel = viewModel(),
    productId: Long? = null
) {
    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("POLERAS") }
    val tallasList = remember { mutableStateListOf<String>() }
    var medidasText by remember { mutableStateOf("") }
    var imagenesAdicionales by remember { mutableStateOf("") }

    // Estados para imágenes
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var uploadingImage by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Error local para validaciones (declarado antes de los launchers)
    val _error = remember { mutableStateOf<String?>(null) }

    // Estado para permisos de cámara
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    var showPermissionRationale by remember { mutableStateOf(false) }
    var shouldOpenCamera by remember { mutableStateOf(false) }

    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            selectedImageBitmap = null
            uploadingImage = true

            scope.launch {
                val fileName = ImageUploadService.getFileNameFromUri(context, it)
                imagenUrl = fileName
                uploadingImage = false
            }
        }
    }

    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: android.graphics.Bitmap? ->
        bitmap?.let {
            selectedImageBitmap = it
            selectedImageUri = null
            uploadingImage = true

            scope.launch {
                val fileName = "camera_${System.currentTimeMillis()}.jpg"
                imagenUrl = fileName
                uploadingImage = false
            }
        }
    }

    // Launcher para solicitar permiso de cámara (debe ir después de cameraLauncher)
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionGranted = isGranted
        if (isGranted) {
            // Si el permiso fue otorgado, abrir la cámara automáticamente
            cameraLauncher.launch(null)
        } else {
            showPermissionRationale = true
            _error.value = "Se requiere permiso de cámara para tomar fotos. Ve a Configuración → Apps → CrimeWave → Permisos para habilitarlo."
        }
    }

    var expanded by remember { mutableStateOf(false) }
    val categorias = listOf("POLERAS", "PANTALONES", "POLERONES")

    val blackFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        cursorColor = Color.Black,
        textColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black
    )

    // Estado del ViewModel
    val loading by adminViewModel.loading.collectAsState()
    val error by adminViewModel.error.collectAsState()

    // Coleccionar la lista de productos para poder precargar el producto por id
    val productos by adminViewModel.productos.collectAsState()

    // Flag para evitar reescribir campos si el usuario ya empezó a editar
    val prefilledState = remember { mutableStateOf(false) }

    // Si estamos editando, prellenar campos cuando el producto esté disponible.
    // Si aún no está en la lista, lanzar fetchProductos() y esperar a que llegue.
    LaunchedEffect(productId, productos) {
        if (productId != null && !prefilledState.value) {
            var prod = adminViewModel.getProductoById(productId)
            if (prod == null) {
                // Intentar obtener la lista desde el backend
                adminViewModel.fetchProductos()
            }
            // Reintentar obtener el producto (la lista podría haberse actualizado)
            prod = adminViewModel.getProductoById(productId)
            prod?.let {
                nombre = it.nombre ?: ""
                precio = (it.precio ?: 0.0).toString()
                descripcion = it.descripcion ?: ""
                imagenUrl = it.imagenUrl ?: ""
                stock = ""
                categoria = it.categoria ?: "POLERAS"  // ✅ Ya es String
                tallasList.clear()
                tallasList.addAll(it.tallas)
                medidasText = it.medidas.joinToString(",")
                imagenesAdicionales = it.imagenesUrl.joinToString(",")
                prefilledState.value = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Agregar Producto" else "Editar Producto", color = Color.Black) },
                backgroundColor = Color.White
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "INFORMACIÓN BÁSICA",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors
                )
            }

            item {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio (sin puntos ni comas)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("Ejemplo: 15990") }
                )
            }

            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock disponible") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("Ejemplo: 25") }
                )
            }

            item {
                Text(
                    "CATEGORÍA Y TIPO",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                // Dropdown estándar: OutlinedTextField readOnly + IconButton para desplegar
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { /* readOnly */ },
                        readOnly = true,
                        label = { Text("Categoría") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        colors = blackFieldColors,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Toggle"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(onClick = {
                                categoria = cat
                                expanded = false
                            }) {
                                Text(cat)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    "TALLAS DISPONIBLES",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    "Presiona para seleccionar/deseleccionar (máximo 1 de cada talla)",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Tallas predefinidas disponibles
                val tallasDisponibles = listOf("S", "M", "L", "XL", "XXL", "XXXL")

                // Mostrar botones en dos filas de 3 columnas
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Primera fila: S, M, L
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tallasDisponibles.take(3).forEach { talla ->
                            val isSelected = tallasList.contains(talla)
                            Button(
                                onClick = {
                                    if (isSelected) {
                                        tallasList.remove(talla)
                                    } else {
                                        tallasList.add(talla)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Black else Color.White,
                                    contentColor = if (isSelected) Color.White else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(talla, style = MaterialTheme.typography.button)
                            }
                        }
                    }

                    // Segunda fila: XL, XXL, XXXL
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tallasDisponibles.drop(3).forEach { talla ->
                            val isSelected = tallasList.contains(talla)
                            Button(
                                onClick = {
                                    if (isSelected) {
                                        tallasList.remove(talla)
                                    } else {
                                        tallasList.add(talla)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Black else Color.White,
                                    contentColor = if (isSelected) Color.White else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(talla, style = MaterialTheme.typography.button)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Resumen de tallas seleccionadas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp,
                    backgroundColor = if (tallasList.isEmpty()) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Tallas seleccionadas:",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.Black
                            )
                            Text(
                                "${tallasList.size}/6",
                                style = MaterialTheme.typography.caption,
                                color = if (tallasList.isEmpty()) Color.Red else Color(0xFF4CAF50)
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        if (tallasList.isEmpty()) {
                            Text(
                                "⚠️ No has seleccionado ninguna talla",
                                style = MaterialTheme.typography.body2,
                                color = Color.Red
                            )
                        } else {
                            // Ordenar tallas por tamaño real, no alfabéticamente
                            val ordenTallas = listOf("S", "M", "L", "XL", "XXL", "XXXL")
                            val tallasOrdenadas = tallasList.sortedBy { ordenTallas.indexOf(it) }
                            Text(
                                tallasOrdenadas.joinToString(", "),
                                style = MaterialTheme.typography.body1,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    "IMÁGENES",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                Text(
                    "Selecciona una imagen desde galería o toma una foto",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            item {
                // Botones para seleccionar imagen
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        enabled = !uploadingImage
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_gallery),
                            contentDescription = "Galería",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Galería")
                    }

                    Button(
                        onClick = {
                            // Verificar si el permiso está otorgado
                            if (context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(null)
                            } else {
                                // Solicitar permiso
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2196F3),
                            contentColor = Color.White
                        ),
                        enabled = !uploadingImage
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_camera),
                            contentDescription = "Cámara",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Cámara")
                    }
                }
            }

            // Preview de la imagen seleccionada
            item {
                if (uploadingImage) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 2.dp,
                        backgroundColor = Color(0xFFFFF9C4)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(Modifier.width(12.dp))
                            Text("Procesando imagen...", color = Color.Black)
                        }
                    }
                }

                if (selectedImageUri != null || selectedImageBitmap != null) {
                    Spacer(Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 2.dp
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Vista previa de la imagen:",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(8.dp))

                            // Mostrar imagen
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    selectedImageUri != null -> {
                                        Image(
                                            painter = rememberAsyncImagePainter(selectedImageUri),
                                            contentDescription = "Imagen seleccionada",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                    selectedImageBitmap != null -> {
                                        Image(
                                            bitmap = selectedImageBitmap!!.asImageBitmap(),
                                            contentDescription = "Foto tomada",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Nombre: $imagenUrl",
                                style = MaterialTheme.typography.caption,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("Nombre de la imagen (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = blackFieldColors,
                    placeholder = { Text("polera_negra.jpg") },
                    enabled = !uploadingImage
                )
            }



            item {
                Text(
                    "INFORMACIÓN ADICIONAL",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 1.dp,
                    backgroundColor = Color.Yellow.copy(alpha = 0.1f)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            "RESUMEN DEL PRODUCTO",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        val ordenTallas = listOf("S", "M", "L", "XL", "XXL", "XXXL")
                        val tallasOrdenadas = tallasList.sortedBy { ordenTallas.indexOf(it) }

                        Text("• Nombre: $nombre", color = Color.Black)
                        Text("• Precio: \$$precio", color = Color.Black)
                        Text("• Categoría: $categoria", color = Color.Black)
                        Text("• Stock: $stock unidades", color = Color.Black)
                        Text("• Tallas: ${tallasOrdenadas.joinToString(", ")}", color = Color.Black)
                        Text("• Medidas: $medidasText", color = Color.Black)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            // Validaciones
                            if (nombre.isBlank()) {
                                _error.value = "El nombre del producto es obligatorio"
                                return@Button
                            }

                            val precioInt = precio.toIntOrNull()
                            if (precioInt == null || precioInt <= 0) {
                                _error.value = "El precio debe ser un número válido mayor a 0"
                                return@Button
                            }

                            val stockInt = stock.toIntOrNull() ?: 0

                            if (selectedImageUri == null && selectedImageBitmap == null) {
                                _error.value = "Debes seleccionar una imagen"
                                return@Button
                            }

                            // Crear MultipartBody.Part desde imagen
                            val imagePart = when {
                                selectedImageUri != null ->
                                    ImageUploadService.createImagePart(context, selectedImageUri!!)
                                selectedImageBitmap != null ->
                                    ImageUploadService.createImagePartFromBitmap(context, selectedImageBitmap!!)
                                else -> null
                            }

                            // Convertir tallas a formato "S,M,L,XL"
                            val tallasString = tallasList.sorted().joinToString(",")

                            if (productId == null) {
                                adminViewModel.createProducto(
                                    nombre = nombre.trim(),
                                    descripcion = descripcion.trim().ifBlank { "Sin descripción" },
                                    precio = precioInt,
                                    stock = stockInt,
                                    tallas = tallasString,
                                    imagePart = imagePart,
                                    onSuccess = {
                                        navController.navigate("backoffice")
                                    }
                                )
                            } else {
                                adminViewModel.updateProducto(
                                    id = productId,
                                    nombre = nombre.trim(),
                                    descripcion = descripcion.trim().ifBlank { "Sin descripción" },
                                    precio = precioInt,
                                    stock = stockInt,
                                    tallas = tallasString,
                                    imagePart = imagePart,
                                    onSuccess = {
                                        navController.navigate("backoffice")
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        ),
                        enabled = !loading && !uploadingImage
                    ) {
                        Text(if (productId == null) "CREAR PRODUCTO" else "ACTUALIZAR PRODUCTO")
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("backoffice") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text("CANCELAR")
                    }
                }
            }
        }
    }

    // Mostrar loading y errores
    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    // Mostrar errores del ViewModel o locales
    val errorToShow = error ?: _error.value
    errorToShow?.let { err ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                backgroundColor = Color(0xFFFFEBEE),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚠️ $err",
                        color = Color.Red,
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { _error.value = null }) {
                        Text("OK", color = Color.Red)
                    }
                }
            }
        }
    }
}
