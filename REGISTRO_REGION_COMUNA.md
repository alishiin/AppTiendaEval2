# Actualización: Campos de Región y Comuna en el Registro

## Cambios Realizados

Se ha actualizado el formulario de registro para incluir los campos de **Región** y **Comuna**, utilizando los datos del archivo `ChileData.kt`.

### 1. Modificaciones en RegisterScreen.kt

#### Imports Agregados:
- `androidx.compose.material.icons.Icons`
- `androidx.compose.material.icons.filled.ArrowDropDown`
- `com.example.apptiendaeval2.utils.ChileData`

#### Nuevos Campos:
1. **Región/Ciudad**: Dropdown con las ciudades principales de Chile
2. **Comuna**: Dropdown que se actualiza dinámicamente según la región seleccionada

#### Características:
- ✅ Dropdowns interactivos con `ExposedDropdownMenuBox`
- ✅ El campo Comuna se habilita solo después de seleccionar una Región
- ✅ Las comunas se filtran automáticamente según la región seleccionada
- ✅ Validación obligatoria de ambos campos antes de registrar
- ✅ Los datos se envían al backend en el formato: "Región - Comuna"

### 2. Datos Utilizados

El archivo `ChileData.kt` proporciona:
- **ciudadesChile**: Lista de ciudades principales de Chile
- **comunasSantiago**: Lista de comunas de Santiago
- **getComunasPorCiudad()**: Función que retorna las comunas según la ciudad seleccionada

### 3. Flujo de Uso

1. El usuario selecciona una **Región/Ciudad** del dropdown
2. Automáticamente se habilita el campo **Comuna**
3. El usuario selecciona una **Comuna** del dropdown (filtrado por región)
4. Al hacer clic en "Registrar", se valida que ambos campos estén llenos
5. Los datos se envían al backend en el formato: "Santiago - Las Condes"

### 4. Validación

Se agregó validación en el botón de registro:
```kotlin
region.isBlank() || comuna.isBlank() -> 
    errorMessage = "Todos los campos son obligatorios"
```

### 5. Integración con Backend

Los datos se envían a través del `AuthViewModel`:
```kotlin
authViewModel.register(
    nombre, 
    email, 
    password, 
    formattedRut, 
    direccion, 
    "$region - $comuna"  // ✅ Formato: "Región - Comuna"
)
```

## Datos Disponibles

### Ciudades/Regiones:
- Arica, Iquique, Antofagasta, Calama, Copiapó
- La Serena, Coquimbo
- Valparaíso, Viña del Mar, Quilpué, Villa Alemana
- Rancagua
- Santiago (con 32 comunas)
- Talca, Curicó
- Chillán
- Concepción, Talcahuano, Los Ángeles
- Temuco, Valdivia, Osorno
- Puerto Montt, Puerto Varas
- Coyhaique, Punta Arenas

### Comunas de Santiago:
32 comunas incluyendo: Las Condes, Providencia, Santiago Centro, Maipú, La Florida, Ñuñoa, etc.

### Comunas de Valparaíso:
Valparaíso, Viña del Mar, Quilpué, Villa Alemana, Concón

### Comunas de Concepción:
Concepción, Talcahuano, Hualpén, San Pedro de la Paz, Chiguayante

## Notas Técnicas

- Se utiliza `@OptIn(ExperimentalMaterialApi::class)` para usar `ExposedDropdownMenuBox`
- Los dropdowns están estilizados con colores negros para mantener la consistencia visual
- El campo Comuna se deshabilita si no hay región seleccionada
- La lista de comunas se actualiza automáticamente al cambiar la región usando `remember(region)`

## Próximos Pasos Recomendados

1. **Backend**: Asegurarse de que la tabla `users` tenga una columna para almacenar región/comuna
2. **Validación**: El backend debe recibir el campo `comuna` en el formato "Región - Comuna"
3. **Extensión**: Si se necesitan más ciudades/comunas, agregar en `ChileData.kt`

