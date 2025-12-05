@echo off
echo ========================================
echo  EJECUTANDO PRUEBAS UNITARIAS
echo ========================================
echo.

echo [1/2] Ejecutando todas las pruebas...
call gradlew.bat test --console=plain

echo.
echo ========================================
echo  PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Reportes disponibles en:
echo app\build\reports\tests\testDebugUnitTest\index.html
echo.

pause

