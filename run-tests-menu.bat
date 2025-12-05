@echo off
chcp 65001 >nul
echo ╔═══════════════════════════════════════════════════════════╗
echo ║      MENÚ DE PRUEBAS UNITARIAS - AppTiendaEval2         ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.
echo Selecciona qué pruebas ejecutar:
echo.
echo [1] Todas las pruebas (47 tests)
echo [2] RutValidatorTest (9 tests)
echo [3] CartViewModelTest (11 tests)
echo [4] ProductoTest (6 tests)
echo [5] ChileDataTest (8 tests)
echo [6] CategoriaTest (7 tests)
echo [7] CartItemTest (6 tests)
echo [8] Ver reporte HTML de última ejecución
echo [9] Salir
echo.
set /p opcion="Ingresa tu opción (1-9): "

if "%opcion%"=="1" goto todas
if "%opcion%"=="2" goto rut
if "%opcion%"=="3" goto cart
if "%opcion%"=="4" goto producto
if "%opcion%"=="5" goto chile
if "%opcion%"=="6" goto categoria
if "%opcion%"=="7" goto cartitem
if "%opcion%"=="8" goto reporte
if "%opcion%"=="9" goto fin

echo Opción inválida. Presiona cualquier tecla para continuar...
pause >nul
goto inicio

:todas
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO TODAS LAS PRUEBAS
echo ═══════════════════════════════════════
call gradlew.bat test --console=plain
goto resultado

:rut
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO RutValidatorTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.RutValidatorTest" --console=plain
goto resultado

:cart
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO CartViewModelTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.CartViewModelTest" --console=plain
goto resultado

:producto
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO ProductoTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.ProductoTest" --console=plain
goto resultado

:chile
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO ChileDataTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.ChileDataTest" --console=plain
goto resultado

:categoria
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO CategoriaTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.CategoriaTest" --console=plain
goto resultado

:cartitem
echo.
echo ═══════════════════════════════════════
echo  EJECUTANDO CartItemTest
echo ═══════════════════════════════════════
call gradlew.bat test --tests "com.example.apptiendaeval2.CartItemTest" --console=plain
goto resultado

:reporte
echo.
echo Abriendo reporte HTML...
start app\build\reports\tests\testDebugUnitTest\index.html
echo.
echo Si el reporte no se abre, búscalo en:
echo app\build\reports\tests\testDebugUnitTest\index.html
echo.
pause
goto inicio

:resultado
echo.
echo ═══════════════════════════════════════
echo  PRUEBAS COMPLETADAS
echo ═══════════════════════════════════════
echo.
echo 📊 Reporte disponible en:
echo    app\build\reports\tests\testDebugUnitTest\index.html
echo.
echo ¿Quieres ver el reporte HTML? (S/N)
set /p ver="Respuesta: "
if /i "%ver%"=="S" start app\build\reports\tests\testDebugUnitTest\index.html
echo.
pause
goto inicio

:fin
echo.
echo ¡Hasta luego!
exit /b

