# ⚡ Inicio Rápido

## 🚀 Para Ejecutar la App:

### Opción 1: Desde Android Studio
1. Abre el proyecto en Android Studio
2. **Build** → **Clean Project**
3. **Build** → **Rebuild Project**
4. Conecta tu dispositivo o inicia un emulador
5. Haz clic en **Run** ▶️

### Opción 2: Desde Terminal
```bash
cd C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2
gradlew clean
gradlew assembleDebug
```

## ✅ Lo que se Corrigió:

1. ✅ **Paquete incorrecto**: Todos los archivos ahora usan `com.example.apptiendaeval2`
2. ✅ **MainActivity no declarado**: AndroidManifest.xml corregido
3. ✅ **API AWS configurada**: `http://18.217.254.148:8080/`
4. ✅ **Permisos de Internet**: Configurados correctamente

## 🌐 Tu API AWS:
```
http://18.217.254.148:8080/
```

## ⚠️ Si no funciona:

1. Verifica que el servidor AWS esté corriendo:
   ```bash
   curl http://18.217.254.148:8080/api/products
   ```

2. Si usas emulador, verifica que tenga conexión a Internet

3. Revisa los logs de Android Studio (Logcat) para ver errores de red

## 📱 La App Está Lista!

**¡Todos los errores están resueltos!** Ahora puedes compilar e instalar la app en tu dispositivo.

