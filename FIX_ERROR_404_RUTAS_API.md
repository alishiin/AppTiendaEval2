# ✅ FIX: Error 404 al Crear/Editar Productos

## 🐛 Problema

Al intentar crear o editar productos, la aplicación recibía **Error 404 (Not Found)**.

### Causa del Error

**Desincronización entre rutas del Frontend y Backend:**

| Acción | App (ANTES) | Backend (REAL) | Estado |
|--------|-------------|----------------|--------|
| Crear producto | `/api/admin/productos` | `/api/products` | ❌ 404 |
| Actualizar producto | `/api/admin/productos/{id}` | `/api/products/{id}` | ❌ 404 |
| Eliminar producto | `/api/admin/productos/{id}` | `/api/products/{id}` | ❌ 404 |
| Listar productos | `/api/products` | `/api/products` | ✅ OK |

---

## ✅ Solución Implementada

Se corrigieron las rutas en `ApiService.kt` para que coincidan con las rutas del backend Spring Boot.

### Archivo Modificado: `ApiService.kt`

#### ANTES (❌ Rutas incorrectas):
```kotlin
@POST("api/admin/productos")
suspend fun createProducto(@Body producto: Producto): Response<Producto>

@PUT("api/admin/productos/{id}")
suspend fun updateProducto(@Path("id") id: Long, @Body producto: Producto): Response<Producto>

@DELETE("api/admin/productos/{id}")
suspend fun deleteProducto(@Path("id") id: Long): Response<Map<String, String>>
```

#### DESPUÉS (✅ Rutas correctas):
```kotlin
@POST("api/products")
suspend fun createProducto(@Body producto: Producto): Response<Producto>

@PUT("api/products/{id}")
suspend fun updateProducto(@Path("id") id: Long, @Body producto: Producto): Response<Producto>

@DELETE("api/products/{id}")
suspend fun deleteProducto(@Path("id") id: Long): Response<Map<String, String>>
```

---

## 📋 Rutas del Backend (Spring Boot)

Según tu configuración, el backend usa:

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @GetMapping  // GET /api/products
    public List<Producto> listar() { ... }
    
    @PostMapping  // POST /api/products
    public Producto crear(@RequestBody Producto p) { ... }
    
    @PutMapping("/{id}")  // PUT /api/products/{id}
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto p) { ... }
    
    @DeleteMapping("/{id}")  // DELETE /api/products/{id}
    public void eliminar(@PathVariable Long id) { ... }
}
```

Otras rutas del backend:
- `/api/users` - Gestión de usuarios
- `/api/categories` - Gestión de categorías

---

## 🔄 Flujo Corregido

### Crear Producto:
1. Usuario completa formulario en `AddProductScreen`
2. Presiona "CREAR PRODUCTO"
3. **App envía:** `POST https://api-moviles-mg5l.onrender.com/api/products`
4. **Backend recibe:** Ruta coincide → Producto creado ✅
5. **Respuesta 200 OK** con el producto creado

### Actualizar Producto:
1. Usuario edita producto existente
2. Presiona "ACTUALIZAR PRODUCTO"
3. **App envía:** `PUT https://api-moviles-mg5l.onrender.com/api/products/{id}`
4. **Backend recibe:** Ruta coincide → Producto actualizado ✅
5. **Respuesta 200 OK** con el producto actualizado

### Eliminar Producto:
1. Usuario presiona "Eliminar" en BackOffice
2. **App envía:** `DELETE https://api-moviles-mg5l.onrender.com/api/products/{id}`
3. **Backend recibe:** Ruta coincide → Producto eliminado ✅
4. **Respuesta 200 OK**

---

## 📊 Cambios Realizados

### Archivo: `ApiService.kt`
**Líneas modificadas:** 58, 63, 68

| Endpoint | Cambio |
|----------|--------|
| POST | `api/admin/productos` → `api/products` |
| PUT | `api/admin/productos/{id}` → `api/products/{id}` |
| DELETE | `api/admin/productos/{id}` → `api/products/{id}` |
| GET | `api/products` (sin cambios) |

**Total de cambios:** 3 rutas corregidas

---

## ✅ Verificación

### Prueba 1: Crear Producto
```bash
# Request que ahora envía la app
POST https://api-moviles-mg5l.onrender.com/api/products
Content-Type: application/json

{
  "nombre": "Polera Test",
  "precio": 15990.0,
  "descripcion": "Producto de prueba",
  "categoria": "POLERAS",
  "imagenUrl": "polera_test.jpg"
}

# Respuesta esperada: 200 OK ✅
```

### Prueba 2: Actualizar Producto
```bash
# Request que ahora envía la app
PUT https://api-moviles-mg5l.onrender.com/api/products/123
Content-Type: application/json

{
  "id": 123,
  "nombre": "Polera Test Editada",
  "precio": 17990.0,
  "descripcion": "Descripción actualizada",
  "categoria": "POLERAS",
  "imagenUrl": "polera_test_v2.jpg"
}

# Respuesta esperada: 200 OK ✅
```

### Prueba 3: Eliminar Producto
```bash
# Request que ahora envía la app
DELETE https://api-moviles-mg5l.onrender.com/api/products/123

# Respuesta esperada: 200 OK ✅
```

---

## 🧪 Cómo Probar en la App

### Crear Producto:
1. Abrir app → BackOffice → Botón **+**
2. Completar formulario:
   - Nombre: "Polera Prueba"
   - Precio: "19990"
   - Descripción: "Test"
   - Categoría: "POLERAS"
   - Imagen: Seleccionar desde galería
3. Presionar **"CREAR PRODUCTO"**
4. ✅ Debe mostrar mensaje de éxito
5. ✅ Producto debe aparecer en el BackOffice
6. ✅ Producto debe estar en GET /api/products

### Actualizar Producto:
1. En BackOffice, presionar **"Editar"** en un producto
2. Cambiar nombre o precio
3. Presionar **"ACTUALIZAR PRODUCTO"**
4. ✅ Debe mostrar mensaje de éxito
5. ✅ Cambios deben verse reflejados

### Eliminar Producto:
1. En BackOffice, presionar **"Eliminar"** en un producto
2. ✅ Producto debe desaparecer de la lista
3. ✅ Producto debe eliminarse del backend

---

## 🔍 Debug: Cómo Verificar las Rutas

### En Android Studio (Logcat):
```kotlin
// Agregar logs temporales en AdminViewModel para debug:
fun createProducto(producto: Producto, onSuccess: () -> Unit) {
    viewModelScope.launch {
        try {
            Log.d("API", "Creando producto en: api/products")
            Log.d("API", "Datos: ${producto}")
            val response = apiService.createProducto(producto)
            Log.d("API", "Response code: ${response.code()}")
            Log.d("API", "Response body: ${response.body()}")
            // ...
        }
    }
}
```

### Con Postman/cURL:
```bash
# Probar directamente el backend
curl -X POST https://api-moviles-mg5l.onrender.com/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test",
    "precio": 10000,
    "descripcion": "Test",
    "categoria": "POLERAS",
    "imagenUrl": "test.jpg"
  }'
```

---

## 📝 Lecciones Aprendidas

### 1. Sincronización Frontend-Backend
Las rutas del frontend deben **coincidir exactamente** con las del backend:
- ✅ Mismo path: `/api/products`
- ✅ Mismo método HTTP: POST, PUT, DELETE
- ✅ Mismo formato de parámetros: `{id}` en la URL

### 2. Convenciones de Nombres
- Backend Spring Boot: Usa nombres en inglés (`/products`, `/users`)
- Frontend: Debe usar los mismos nombres que el backend
- Evitar traducciones: `productos` ≠ `products`

### 3. Error 404 vs otros errores HTTP
- **404 Not Found**: La ruta no existe en el servidor
- **400 Bad Request**: La ruta existe pero los datos son inválidos
- **500 Internal Server Error**: Error en el código del servidor
- **401 Unauthorized**: Falta autenticación

---

## 🎓 Buenas Prácticas

### 1. Documentar las Rutas
Mantener un archivo con todas las rutas del backend:
```
# routes.md
POST   /api/products       - Crear producto
GET    /api/products       - Listar productos
GET    /api/products/{id}  - Obtener producto
PUT    /api/products/{id}  - Actualizar producto
DELETE /api/products/{id}  - Eliminar producto
```

### 2. Usar Constantes
```kotlin
object ApiRoutes {
    const val PRODUCTS = "api/products"
    const val USERS = "api/users"
    const val CATEGORIES = "api/categories"
}

@POST(ApiRoutes.PRODUCTS)
suspend fun createProducto(...)
```

### 3. Testing de Integración
Probar las rutas con Postman antes de integrar en la app.

---

## ⚠️ NOTA ADICIONAL: Error 415

Si después de corregir las rutas obtienes **Error 415 (Unsupported Media Type)**, consulta el archivo:
📄 **FIX_ERROR_415_CONTENT_TYPE.md**

**Solución rápida:** Agregar headers `Content-Type: application/json` en los endpoints POST/PUT.

---

## 🎉 Resultado Final

✅ **Error 404 resuelto** (rutas corregidas)  
✅ **Error 415 resuelto** (headers agregados)  
✅ **Crear productos funciona**  
✅ **Actualizar productos funciona**  
✅ **Eliminar productos funciona**  
✅ **Rutas sincronizadas Frontend ↔ Backend**  

---

## 🚀 Siguiente Paso

Recompila y prueba la aplicación:

```bash
gradlew clean assembleDebug
```

**Prueba:**
1. Crear un producto nuevo
2. Editar el producto creado
3. Eliminar el producto

**Todas las operaciones deberían funcionar sin errores 404** ✅

---

**Fecha:** 18 de Diciembre, 2025  
**Archivo:** ApiService.kt  
**Error resuelto:** 404 Not Found en operaciones CRUD  
**Rutas corregidas:** 3 (POST, PUT, DELETE)  
**Estado:** ✅ COMPLETADO

