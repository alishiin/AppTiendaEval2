# 🔧 Solución: Panel de Administrador no aparece

## ❌ Problema
Al intentar entrar como administrador, aparece la vista de usuario normal en lugar del panel de admin.

## ✅ Solución Implementada

### 1. **Cambios en el Frontend (Android)**

#### A. Modelo UserResponse actualizado
Se agregó el campo `role` al modelo:

```kotlin
data class UserResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val password: String? = null,
    val rut: String,
    val direccion: String,
    val comuna: String,
    val role: String = "USER"  // ✅ NUEVO
)
```

#### B. LoginScreen actualizado
Se cambió la lógica de navegación para usar el campo `role`:

**ANTES:**
```kotlin
if (it.email == "admin@tienda.cl") navController.navigate("backoffice")
```

**DESPUÉS:**
```kotlin
if (it.role.uppercase() == "ADMIN") {
    navController.navigate("backoffice")
} else {
    navController.navigate("home")
}
```

---

### 2. **Cambios en el Backend (Base de Datos)**

#### Opción A: Script Automático
Ejecuta el archivo `actualizar_role_usuarios.sql`:

```sql
USE tienda_eval2;

-- Agregar columna role
ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER';

-- Actualizar usuarios existentes
UPDATE users SET role = 'USER' WHERE role IS NULL OR role = '';

-- Actualizar usuario admin
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@tienda.cl';
```

#### Opción B: Comandos Manuales (MySQL/MariaDB)

**Paso 1:** Conectar a la base de datos
```bash
mysql -u root -p
```

**Paso 2:** Seleccionar la base de datos
```sql
USE tienda_eval2;
```

**Paso 3:** Agregar columna role
```sql
ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER';
```

**Paso 4:** Ver la estructura de la tabla
```sql
DESCRIBE users;
```

**Paso 5:** Actualizar el usuario administrador
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@tienda.cl';
```

**Paso 6:** Verificar que se actualizó correctamente
```sql
SELECT id, nombre, email, role FROM users WHERE email = 'admin@tienda.cl';
```

Deberías ver algo como:
```
+----+----------------+-------------------+-------+
| id | nombre         | email             | role  |
+----+----------------+-------------------+-------+
|  1 | Administrador  | admin@tienda.cl   | ADMIN |
+----+----------------+-------------------+-------+
```

---

### 3. **Backend API (Spring Boot)**

Si estás usando Spring Boot, asegúrate de que la entidad User incluya el campo role:

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String email;
    private String password;
    private String rut;
    private String direccion;
    private String comuna;
    
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'USER'")
    private String role = "USER";  // ✅ NUEVO
    
    // Getters y Setters
}
```

Y que el AuthController devuelva el campo role:

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    User user = authService.authenticate(request.getEmail(), request.getPassword());
    if (user != null) {
        return ResponseEntity.ok(new UserResponse(
            user.getId(),
            user.getNombre(),
            user.getEmail(),
            null, // password
            user.getRut(),
            user.getDireccion(),
            user.getComuna(),
            user.getRole()  // ✅ NUEVO
        ));
    }
    return ResponseEntity.status(401).body("Credenciales inválidas");
}
```

---

## 🚀 Pasos para Probar

### 1. **Actualizar Base de Datos**
Ejecuta uno de los scripts SQL proporcionados:
- `agregar_campo_role.sql` (más completo)
- `actualizar_role_usuarios.sql` (simplificado)

### 2. **Reiniciar Backend**
Si tienes un servidor backend corriendo, reinícialo para que reconozca el nuevo campo.

### 3. **Recompilar la App Android**
```bash
.\gradlew clean assembleDebug
```

### 4. **Instalar la App**
Instala la nueva versión en tu dispositivo/emulador.

### 5. **Probar el Login Admin**
1. Abre la app
2. Ingresa con las credenciales de administrador:
   - Email: `admin@tienda.cl`
   - Password: (la que hayas configurado)
3. Deberías ver el **Panel de Administrador** (BackOffice)

---

## 🔍 Verificación

### Base de Datos
```sql
-- Ver todos los usuarios con sus roles
SELECT id, nombre, email, role FROM users;

-- Ver solo administradores
SELECT * FROM users WHERE role = 'ADMIN';
```

### Logs de la App
Si sigue sin funcionar, revisa los logs de la app:
```bash
adb logcat | grep -i "auth\|login\|user"
```

---

## 📋 Crear Usuario Admin Manualmente

Si no existe el usuario admin, créalo:

```sql
INSERT INTO users (nombre, email, password, rut, direccion, comuna, role)
VALUES (
    'Administrador', 
    'admin@tienda.cl', 
    'admin123',  -- ⚠️ Cambiar por una contraseña segura
    '11111111-1', 
    'Oficina Central', 
    'Santiago - Santiago Centro', 
    'ADMIN'
);
```

---

## 🎯 Tipos de Roles Soportados

- **ADMIN**: Acceso al panel de administración (BackOffice)
- **USER**: Acceso normal (Home, tienda, carrito, etc.)

---

## 📝 Archivos Modificados

1. ✅ `UserResponse.kt` - Agregado campo `role`
2. ✅ `LoginScreen.kt` - Actualizada lógica de navegación
3. ✅ `agregar_campo_role.sql` - Script SQL completo
4. ✅ `actualizar_role_usuarios.sql` - Script SQL simplificado
5. ✅ `SOLUCION_PANEL_ADMIN.md` - Esta documentación

---

## ⚠️ Notas Importantes

1. **Seguridad**: En producción, las contraseñas deben estar hasheadas (bcrypt, argon2, etc.)
2. **Validación**: El backend debe validar el rol antes de permitir operaciones admin
3. **Token JWT**: Si usas JWT, incluye el rol en el token
4. **Mayúsculas**: El código usa `role.uppercase()` para comparar, así que "admin", "ADMIN", "Admin" funcionan igual

---

## 🆘 Si Sigue Sin Funcionar

1. **Verifica que el backend esté devolviendo el campo `role`**
   - Usa Postman o curl para probar el endpoint de login
   - Verifica que la respuesta JSON incluya `"role": "ADMIN"`

2. **Verifica que la base de datos tenga el campo**
   ```sql
   SHOW COLUMNS FROM users LIKE 'role';
   ```

3. **Limpia y recompila**
   ```bash
   .\gradlew clean
   .\gradlew assembleDebug
   ```

4. **Verifica los logs del backend**
   - Revisa si hay errores al serializar el campo `role`

---

## 🎉 Resultado Esperado

Después de aplicar estos cambios:

1. Usuario con `role = "USER"` → Navega a **Home** (vista normal)
2. Usuario con `role = "ADMIN"` → Navega a **BackOffice** (panel admin)

---

**¡Problema resuelto! 🚀**

