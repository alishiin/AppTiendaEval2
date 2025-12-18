# ✅ CHECKLIST DE VERIFICACIÓN - Crear Productos con Imágenes

## 📋 Verificar Antes de Compilar

### Archivos Modificados
- [x] `AndroidManifest.xml` - Permisos agregados
- [x] `Producto.kt` - Modelo actualizado (Double, String)
- [x] `AddProductScreen.kt` - UI y funcionalidad completa

### Archivos Nuevos
- [x] `ImageUploadService.kt` - Servicio de imágenes
- [x] `file_paths.xml` - Configuración FileProvider
- [x] `IMPLEMENTACION_CREAR_PRODUCTOS_IMAGENES.md` - Documentación técnica
- [x] `GUIA_RAPIDA_CREAR_PRODUCTOS.md` - Guía de usuario

---

## 🔨 Compilar y Probar

### 1. Limpiar y Compilar
```bash
# Windows (cmd)
cd C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2
gradlew clean
gradlew assembleDebug

# Si hay errores, intenta:
gradlew clean build --refresh-dependencies
```

### 2. Instalar en Dispositivo/Emulador
```bash
# Instalar APK
gradlew installDebug

# O manualmente:
# Encuentra el APK en: app/build/outputs/apk/debug/app-debug.apk
# Arrastra e instala en el emulador
```

---

## 🧪 Pruebas Funcionales

### Prueba 1: Seleccionar Imagen desde Galería
- [ ] Abrir panel admin
- [ ] Presionar botón **+**
- [ ] Presionar botón verde "Galería"
- [ ] Seleccionar una imagen
- [ ] ✅ Verificar que aparece la vista previa
- [ ] ✅ Verificar que el nombre de archivo se muestra

### Prueba 2: Tomar Foto con Cámara
- [ ] Abrir panel admin
- [ ] Presionar botón **+**
- [ ] Presionar botón azul "Cámara"
- [ ] Tomar una foto
- [ ] ✅ Verificar que aparece la vista previa
- [ ] ✅ Verificar que se genera un nombre de archivo

### Prueba 3: Seleccionar Tallas
- [ ] Presionar botón "S" → Debe ponerse negro
- [ ] Presionar botón "M" → Debe ponerse negro
- [ ] Presionar "S" de nuevo → Debe ponerse blanco
- [ ] ✅ Verificar contador: "1/6", "2/6", etc.
- [ ] ✅ Verificar resumen muestra tallas ordenadas: "M, L, XL"

### Prueba 4: Validaciones
- [ ] Dejar nombre vacío → Presionar "CREAR PRODUCTO"
  - [ ] ✅ Debe mostrar error: "El nombre es obligatorio"
- [ ] Poner precio inválido (letras) → Presionar "CREAR PRODUCTO"
  - [ ] ✅ Debe mostrar error: "El precio debe ser válido"
- [ ] No seleccionar imagen → Presionar "CREAR PRODUCTO"
  - [ ] ✅ Debe mostrar error: "Debes seleccionar una imagen"

### Prueba 5: Crear Producto Completo
- [ ] Nombre: "Polera Test"
- [ ] Precio: "19990"
- [ ] Descripción: "Producto de prueba"
- [ ] Categoría: "POLERAS"
- [ ] Tallas: S, M, L
- [ ] Imagen: Seleccionar desde galería
- [ ] Presionar "CREAR PRODUCTO"
- [ ] ✅ Debe navegar al BackOffice
- [ ] ✅ Debe aparecer en la lista de productos

### Prueba 6: Verificar en la API
```bash
curl https://api-moviles-mg5l.onrender.com/api/products
```
- [ ] ✅ El producto debe aparecer en la respuesta JSON
- [ ] ✅ Los datos deben coincidir (nombre, precio, categoría)

---

## 🐛 Errores Comunes y Soluciones

### Error de Compilación
```
Error: Unresolved reference: ImageUploadService
```
**Solución:** 
- Verifica que el archivo `ImageUploadService.kt` existe
- Sincroniza Gradle: File → Sync Project with Gradle Files

### Error de Permisos
```
Permission Denial: starting Intent requires android.permission.CAMERA
```
**Solución:**
- En el dispositivo/emulador: Configuración → Apps → CrimeWave → Permisos
- Activar "Cámara" y "Almacenamiento"

### Error de FileProvider
```
Error: Couldn't find meta-data for provider with authority
```
**Solución:**
- Verifica que `file_paths.xml` existe en `res/xml/`
- Verifica que el FileProvider está en el AndroidManifest

### Error de API
```
Error al crear producto: 400
```
**Solución:**
- Revisa que el formato de datos sea correcto
- Verifica que la API esté funcionando: https://api-moviles-mg5l.onrender.com/api/products
- Revisa los logs del backend

---

## 📊 Verificar Datos Enviados

### Formato Esperado por la API:
```json
{
  "nombre": "string",
  "precio": 0.0,          // ✅ DOUBLE
  "descripcion": "string",
  "categoria": "string",   // ✅ STRING
  "imagenUrl": "string"
}
```

### Verificar en Logcat (Android Studio):
```
Busca en Logcat:
- "Creating producto: ..." → Para ver el objeto enviado
- "Response: ..." → Para ver la respuesta de la API
```

---

## 🎯 Funcionalidades Implementadas

### UI/UX
- [x] Botones de galería y cámara con íconos
- [x] Vista previa de imagen seleccionada
- [x] Indicador de carga mientras procesa
- [x] Botones de tallas con toggle (negro/blanco)
- [x] Contador de tallas seleccionadas
- [x] Resumen del producto
- [x] Mensajes de error claros

### Lógica
- [x] Launcher de galería funcional
- [x] Launcher de cámara funcional
- [x] Conversión URI → File
- [x] Conversión Bitmap → File
- [x] Validación de campos obligatorios
- [x] Validación de precio numérico
- [x] Validación de imagen seleccionada
- [x] Ordenamiento de tallas por tamaño

### Integración API
- [x] Modelo Producto compatible (Double, String)
- [x] Endpoint POST /api/admin/productos
- [x] Manejo de errores de red
- [x] Refresco de lista después de crear
- [x] Navegación después de éxito

---

## 📈 Estadísticas de Cambios

| Archivo | Líneas Agregadas | Líneas Modificadas |
|---------|------------------|-------------------|
| AndroidManifest.xml | ~15 | ~5 |
| Producto.kt | ~10 | ~5 |
| AddProductScreen.kt | ~150 | ~50 |
| ImageUploadService.kt | ~120 | 0 (nuevo) |
| file_paths.xml | ~5 | 0 (nuevo) |
| **TOTAL** | **~300** | **~60** |

---

## ✅ Confirmación Final

Marca cuando esté completado:

- [ ] ✅ Compilación exitosa sin errores
- [ ] ✅ Instalación en dispositivo/emulador exitosa
- [ ] ✅ Permisos otorgados correctamente
- [ ] ✅ Galería funciona y muestra preview
- [ ] ✅ Cámara funciona y muestra preview
- [ ] ✅ Tallas se seleccionan/deseleccionan correctamente
- [ ] ✅ Validaciones funcionan
- [ ] ✅ Producto se crea en la API
- [ ] ✅ Producto aparece en el BackOffice
- [ ] ✅ Producto visible en GET /api/products

---

## 🎉 ¡TODO LISTO!

Si todas las casillas están marcadas, la implementación está completa y funcionando.

**Fecha de verificación:** __________  
**Verificado por:** __________  
**Estado:** ⬜ Pendiente  |  ⬜ En Progreso  |  ⬜ Completado

---

## 📞 Soporte

Si encuentras algún problema no listado aquí:
1. Revisa `IMPLEMENTACION_CREAR_PRODUCTOS_IMAGENES.md`
2. Revisa los logs en Logcat
3. Verifica la conexión a la API
4. Verifica los permisos del dispositivo

**API Base:** https://api-moviles-mg5l.onrender.com/  
**Endpoint Productos:** /api/products  
**Endpoint Admin:** /api/admin/productos

