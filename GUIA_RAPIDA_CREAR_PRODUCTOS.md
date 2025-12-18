# 🚀 GUÍA RÁPIDA: Crear Productos con Imágenes

## ✅ TODO ESTÁ LISTO

Tu aplicación ahora puede crear productos reales en la API con soporte completo para imágenes.

---

## 📱 CÓMO USAR

### 1. Abrir Panel de Administración
1. Inicia sesión como administrador
2. Toca el ícono de **usuario** (arriba derecha)
3. Selecciona **"BackOffice"** o **"Panel Admin"**

### 2. Agregar Nuevo Producto
1. Presiona el botón **+** (arriba derecha)
2. Completa el formulario:

#### 📝 Información Básica:
- **Nombre:** Nombre del producto (obligatorio)
- **Precio:** Solo números, sin puntos ni comas (Ej: 15990)
- **Descripción:** Descripción del producto
- **Stock:** Cantidad disponible

#### 🏷️ Categoría:
- Selecciona: POLERAS, PANTALONES o POLERONES

#### 👕 Tallas:
- Presiona los botones para seleccionar tallas
- **Negro** = Seleccionada
- **Blanco** = No seleccionada
- Presiona de nuevo para deseleccionar
- Tallas disponibles: S, M, L, XL, XXL, XXXL

#### 📸 Imágenes:
Tienes 2 opciones:

**Opción A: Desde Galería**
1. Presiona botón verde **"Galería"**
2. Selecciona una foto de tu dispositivo
3. Verás la vista previa

**Opción B: Tomar Foto**
1. Presiona botón azul **"Cámara"**
2. Toma una foto
3. Verás la vista previa

### 3. Crear Producto
1. Revisa el **RESUMEN DEL PRODUCTO** al final
2. Presiona **"CREAR PRODUCTO"**
3. ¡Listo! El producto se creará en la API

---

## ⚠️ VALIDACIONES

La app NO te dejará crear el producto si:
- ❌ El nombre está vacío
- ❌ El precio no es un número válido
- ❌ No has seleccionado una imagen

Si hay un error, verás un mensaje rojo en la parte inferior.

---

## 🎯 EJEMPLO PRÁCTICO

```
Nombre: Polera CrimeWave Negra
Precio: 15990
Descripción: Polera negra de algodón premium con logo CrimeWave
Stock: 50
Categoría: POLERAS
Tallas: S, M, L, XL (presiona los botones)
Imagen: Presiona "Galería" → Selecciona foto
```

Presiona "CREAR PRODUCTO" → ✅ Producto creado

---

## 🔍 VERIFICAR QUE FUNCIONA

### En la App:
1. Ve al BackOffice
2. Deberías ver tu producto en la lista

### En la API:
```bash
curl https://api-moviles-mg5l.onrender.com/api/products
```
Busca tu producto en el JSON

---

## 🐛 SOLUCIÓN DE PROBLEMAS

### "Error al crear producto"
- Verifica tu conexión a internet
- Asegúrate de que la API esté funcionando
- Revisa que todos los campos estén completos

### "Debes seleccionar una imagen"
- Presiona "Galería" o "Cámara"
- Espera a que aparezca la vista previa
- No dejes el campo de imagen vacío

### Los permisos de cámara no funcionan
- Ve a Configuración → Apps → CrimeWave → Permisos
- Activa "Cámara" y "Almacenamiento"

### No puedo tomar fotos en el emulador
- El emulador necesita una cámara virtual
- Usa la galería en su lugar
- O prueba en un dispositivo real

---

## 📊 DATOS QUE SE ENVÍAN A LA API

Tu producto se envía así:
```json
{
  "nombre": "Polera CrimeWave Negra",
  "precio": 15990.0,
  "descripcion": "Polera negra de algodón premium",
  "categoria": "POLERAS",
  "imagenUrl": "polera_negra_1234567890.jpg"
}
```

---

## 🎉 NUEVAS CARACTERÍSTICAS

✅ **Botones de Galería y Cámara** - Fácil de usar  
✅ **Vista Previa** - Ves la imagen antes de crear  
✅ **Validaciones** - No puedes crear productos inválidos  
✅ **Tallas Ordenadas** - S, M, L, XL, XXL, XXXL  
✅ **Resumen Visual** - Ves todo antes de confirmar  
✅ **Mensajes de Error** - Sabes exactamente qué falta  

---

## 📞 CONTACTO

Si tienes problemas, revisa el archivo:
`IMPLEMENTACION_CREAR_PRODUCTOS_IMAGENES.md`

Contiene todos los detalles técnicos de la implementación.

---

**¡Disfruta creando productos!** 🎨

