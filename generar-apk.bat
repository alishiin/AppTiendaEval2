@echo off
chcp 65001 >nul
echo ╔═══════════════════════════════════════════════════════════╗
echo ║         GENERAR APK/BUNDLE FIRMADO                       ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.
echo Selecciona qué quieres generar:
echo.
echo [1] APK Debug (para pruebas rápidas)
echo [2] APK Release firmado
echo [3] Bundle Release firmado (para Google Play)
echo [4] Limpiar y generar APK Release
echo [5] Ver ubicación de archivos generados
echo [6] Salir
echo.
set /p opcion="Ingresa tu opción (1-6): "

if "%opcion%"=="1" goto apk_debug
if "%opcion%"=="2" goto apk_release
if "%opcion%"=="3" goto bundle_release
if "%opcion%"=="4" goto clean_release
if "%opcion%"=="5" goto ver_ubicacion
if "%opcion%"=="6" goto fin

echo Opción inválida. Presiona cualquier tecla para continuar...
pause >nul
goto inicio

:apk_debug
echo.
echo ═══════════════════════════════════════
echo  GENERANDO APK DEBUG
echo ═══════════════════════════════════════
call gradlew.bat assembleDebug
goto resultado_apk

:apk_release
echo.
echo ═══════════════════════════════════════
echo  GENERANDO APK RELEASE FIRMADO
echo ═══════════════════════════════════════
call gradlew.bat assembleRelease
goto resultado_apk

:bundle_release
echo.
echo ═══════════════════════════════════════
echo  GENERANDO BUNDLE RELEASE FIRMADO
echo ═══════════════════════════════════════
call gradlew.bat bundleRelease
goto resultado_bundle

:clean_release
echo.
echo ═══════════════════════════════════════
echo  LIMPIANDO Y GENERANDO APK RELEASE
echo ═══════════════════════════════════════
call gradlew.bat clean assembleRelease
goto resultado_apk

:resultado_apk
echo.
echo ═══════════════════════════════════════
echo  APK GENERADO EXITOSAMENTE
echo ═══════════════════════════════════════
echo.
echo 📦 Ubicación de los APKs:
echo.
echo DEBUG:
echo    app\build\outputs\apk\debug\app-debug.apk
echo.
echo RELEASE:
echo    app\build\outputs\apk\release\app-release.apk
echo.
echo ¿Quieres abrir la carpeta? (S/N)
set /p abrir="Respuesta: "
if /i "%abrir%"=="S" start app\build\outputs\apk\release
echo.
pause
goto inicio

:resultado_bundle
echo.
echo ═══════════════════════════════════════
echo  BUNDLE GENERADO EXITOSAMENTE
echo ═══════════════════════════════════════
echo.
echo 📦 Ubicación del Bundle:
echo    app\build\outputs\bundle\release\app-release.aab
echo.
echo Este archivo .aab es para subirlo a Google Play Store
echo.
echo ¿Quieres abrir la carpeta? (S/N)
set /p abrir="Respuesta: "
if /i "%abrir%"=="S" start app\build\outputs\bundle\release
echo.
pause
goto inicio

:ver_ubicacion
echo.
echo ═══════════════════════════════════════
echo  UBICACIÓN DE ARCHIVOS GENERADOS
echo ═══════════════════════════════════════
echo.
echo APKs:
echo  • Debug:   app\build\outputs\apk\debug\
echo  • Release: app\build\outputs\apk\release\
echo.
echo Bundles (AAB):
echo  • Release: app\build\outputs\bundle\release\
echo.
echo ¿Quieres abrir la carpeta de outputs? (S/N)
set /p abrir="Respuesta: "
if /i "%abrir%"=="S" start app\build\outputs
echo.
pause
goto inicio

:fin
echo.
echo ¡Hasta luego!
exit /b

