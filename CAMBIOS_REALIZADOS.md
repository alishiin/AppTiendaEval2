# Cambios Realizados para Conectar la App con AWS

## ✅ Problemas Corregidos

### 1. **Conflicto de Nombres de Paquete**
- **Problema**: MainActivity.kt usaba el paquete `com.example.apptiendaval2` mientras que AndroidManifest.xml y build.gradle.kts usaban `com.example.apptiendaeval2`
- **Solución**: Se corrigieron todos los archivos para usar consistentemente `com.example.apptiendaeval2`

### 2. **MainActivity No Declarado en AndroidManifest**
- **Problema**: El AndroidManifest.xml referenciaba `com.example.apptiendaval2.MainActivity`
- **Solución**: Se cambió a `com.example.apptiendaeval2.MainActivity`

### 3. **URL de API Incorrecta**
- **Problema**: Los archivos de red usaban `http://10.0.2.2:8080/` (localhost del emulador)
- **Solución**: Se actualizó a `http://18.217.254.148:8080/` (tu servidor AWS)

## 📝 Archivos Modificados

### Archivos de Red:
- ✅ `network/ApiService.kt` - BASE_URL actualizada a AWS
- ✅ `network/RetrofitClient.kt` - BASE_URL actualizada a AWS

### MainActivity:
- ✅ `MainActivity.kt` - Paquete corregido

### Manifest:
- ✅ `AndroidManifest.xml` - Referencia a MainActivity corregida

### ViewModels:
- ✅ `viewmodel/ProductViewModel.kt` - Paquete e imports corregidos
- ✅ `viewmodel/CartViewModel.kt` - Paquete corregido
- ✅ `viewmodel/AdminViewModel.kt` - Paquete e imports corregidos
- ✅ `model/AuthViewModel.kt` - Paquete e imports corregidos

### Views:
- ✅ `view/LoginScreen.kt` - Paquete e imports corregidos
- ✅ `view/RegisterScreen.kt` - Paquete e imports corregidos
- ✅ `view/CatalogScreen.kt` - Paquete e imports corregidos
- ✅ `view/ProductDetailsScreen.kt` - Paquete e imports corregidos
- ✅ `view/CartScreen.kt` - Paquete corregido
- ✅ `view/CheckoutScreen.kt` - Paquete e imports corregidos
- ✅ `view/HomeScreen.kt` - Paquete corregido
- ✅ `view/ErrorScreen.kt` - Paquete corregido
- ✅ `view/SuccessScreen.kt` - Paquete corregido
- ✅ `view/BackOfficeScreen.kt` - Paquete corregido
- ✅ `view/AddProductScreen.kt` - Paquete corregido

### Otros:
- ✅ `navigation/NavManager.kt` - Paquete e imports corregidos
- ✅ `events/ProductEvents.kt` - Paquete corregido
- ✅ `model/UserRepository.kt` - Imports corregidos

## 🌐 Configuración de Red

### URL de la API:
```
http://18.217.254.148:8080/
```

### Permisos Configurados:
- ✅ `INTERNET` - Para hacer llamadas HTTP
- ✅ `usesCleartextTraffic="true"` - Para permitir HTTP (no HTTPS)

## 📋 Próximos Pasos

### 1. Limpiar y Compilar el Proyecto:
```bash
gradlew clean
gradlew build
```

### 2. O desde Android Studio:
- **Build** → **Clean Project**
- **Build** → **Rebuild Project**

### 3. Ejecutar la App:
- Conecta tu dispositivo Android o inicia un emulador
- Haz clic en el botón **Run** (▶️)

## ⚠️ Notas Importantes

### Conectividad:
- Si usas un **emulador Android**, la URL `http://18.217.254.148:8080/` debería funcionar correctamente
- Si usas un **dispositivo físico**, asegúrate de que:
  - El dispositivo esté en la misma red o tenga acceso a internet
  - La IP de AWS sea accesible públicamente (parece que sí lo es)

### Verificar la API:
Antes de ejecutar la app, verifica que tu API esté funcionando:
```bash
curl http://18.217.254.148:8080/api/products
```

### Si hay problemas de conexión:
1. Verifica que el servidor AWS esté ejecutándose
2. Verifica que el puerto 8080 esté abierto en el grupo de seguridad de AWS
3. Verifica que la app tenga permisos de Internet en el dispositivo

## 🔍 Endpoints Disponibles en la API

### Autenticación:
- `POST /api/auth/login`
- `POST /api/auth/register`

### Productos (Cliente):
- `GET /api/products`
- `GET /api/products/{id}`
- `GET /api/products/categoria/{categoria}`
- `GET /api/products/{id}/valoraciones`
- `POST /api/products/{id}/valoraciones`

### Admin:
- `GET /api/admin/productos`
- `POST /api/admin/productos`
- `PUT /api/admin/productos/{id}`
- `DELETE /api/admin/productos/{id}`

### Carrito:
- `GET /api/cart`
- `POST /api/cart/add/{productoId}`
- `DELETE /api/cart/remove/{productoId}`
- `DELETE /api/cart/clear`
- `GET /api/cart/total`

## ✨ Estado del Proyecto

**Todo está listo para compilar y ejecutar la app.** Los errores de paquete están resueltos y la app ahora apunta correctamente a tu servidor AWS.

---

**Fecha**: 2025-12-04
**Configuración**: API AWS en http://18.217.254.148:8080/

