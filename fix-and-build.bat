@echo off
echo ════════════════════════════════════════════════════════
echo  SOLUCIONANDO ERROR DE COMPILACIÓN DE RECURSOS
echo ════════════════════════════════════════════════════════
echo.
echo Problema: El archivo cuadro_1.png está causando error
echo Solución: Renombrarlo a cuadro_1_backup.png.bak
echo.

cd C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2\app\src\main\res\drawable

if exist "cuadro_1.png" (
    echo [1/3] Renombrando archivo problemático...
    ren "cuadro_1.png" "cuadro_1_backup.png.bak"
    echo ✅ Archivo renombrado
) else (
    echo ℹ️ El archivo ya fue movido
)

echo.
echo [2/3] Limpiando proyecto...
cd C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2
call gradlew.bat clean

echo.
echo [3/3] Generando Bundle Release...
call gradlew.bat bundleRelease

echo.
echo ════════════════════════════════════════════════════════
echo  PROCESO COMPLETADO
echo ════════════════════════════════════════════════════════
echo.
echo Si el build fue exitoso, tu Bundle está en:
echo   app\build\outputs\bundle\release\app-release.aab
echo.
pause

