@echo off
echo ═══════════════════════════════════════
echo  ELIMINANDO ARCHIVO PROBLEMÁTICO
echo ═══════════════════════════════════════
echo.

set "archivo=C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2\app\src\main\res\drawable\cuadro_1.png"

if exist "%archivo%" (
    echo Eliminando: cuadro_1.png
    del "%archivo%"
    echo.
    echo ✅ Archivo eliminado exitosamente
    echo.
    echo El archivo cuadro1.png (sin guion bajo) sigue disponible.
) else (
    echo ⚠️ El archivo ya no existe
)

echo.
echo ═══════════════════════════════════════
echo  LIMPIANDO PROYECTO
echo ═══════════════════════════════════════
echo.

cd C:\Users\alvar\Desktop\TiendaCrimeWave\AppTiendaEval2
call gradlew.bat clean

echo.
echo ═══════════════════════════════════════
echo  LISTO
echo ═══════════════════════════════════════
echo.
echo Ahora puedes generar el Bundle nuevamente:
echo   gradlew.bat bundleRelease
echo.
pause

