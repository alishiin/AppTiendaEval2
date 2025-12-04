# 🚨 ACCIÓN URGENTE - Eliminar Archivo Duplicado

## ❌ PROBLEMA

Existe un archivo duplicado que causa conflictos:
- `RegisterScreen_NEW.kt` ← **ELIMINAR ESTE ARCHIVO**

## ✅ SOLUCIÓN (Sigue estos pasos)

### Opción 1: Desde Android Studio (RECOMENDADO)

1. **Abre el Project Explorer** (panel izquierdo)
2. **Navega a:** `app → src → main → java → com → example → apptiendaeval2 → view`
3. **Busca el archivo:** `RegisterScreen_NEW.kt`
4. **Haz clic derecho** sobre él
5. **Selecciona:** "Delete" o "Remove"
6. **Confirma** la eliminación
7. **Sync Gradle:** `File → Sync Project with Gradle Files`
8. **Rebuild:** `Build → Rebuild Project`

### Opción 2: Manualmente desde Windows

1. **Cierra Android Studio completamente**
2. **Abre el Explorador de Archivos**
3. **Navega a:**
   ```
   C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2\app\src\main\java\com\example\apptiendaeval2\view\
   ```
4. **Elimina el archivo:** `RegisterScreen_NEW.kt`
5. **Abre Android Studio nuevamente**
6. **Sync Gradle:** `File → Sync Project with Gradle Files`
7. **Rebuild:** `Build → Rebuild Project`

---

## ✅ DESPUÉS DE ELIMINAR

La app debería compilar sin errores. Todos los archivos están correctamente actualizados:

- ✅ `UserResponse.kt` - Incluye rut, direccion, comuna
- ✅ `AuthViewModel.kt` - Envía los 6 campos
- ✅ `RegisterScreen.kt` - Formulario completo con 7 campos

---

## 🎯 RESULTADO FINAL

**Pantalla de Registro con 7 campos:**
1. Nombre completo
2. Email
3. RUT ✨
4. Dirección ✨
5. Comuna ✨
6. Contraseña
7. Confirmar Contraseña

**Request al backend:**
```json
{
  "name": "Juan Pérez",
  "email": "juan@test.com",
  "password": "test123",
  "rut": "11111111-1",
  "direccion": "Av. Libertador 123",
  "comuna": "Santiago Centro"
}
```

---

## ⚡ HAZ ESTO AHORA

**ELIMINA `RegisterScreen_NEW.kt` USANDO UNA DE LAS OPCIONES DE ARRIBA**

Luego compila la app y todo funcionará correctamente.

🎉 **¡La actualización está completa, solo falta eliminar ese archivo duplicado!**

