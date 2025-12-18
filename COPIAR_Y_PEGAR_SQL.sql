-- ====================================
-- COPIA Y PEGA ESTO EN MYSQL
-- ====================================

USE tienda;

-- Paso 1: Agregar columna 'rol'
ALTER TABLE users ADD COLUMN IF NOT EXISTS rol VARCHAR(20) DEFAULT 'USER';

-- Paso 2: Actualizar usuarios sin rol
UPDATE users SET rol = 'USER' WHERE rol IS NULL OR rol = '';

-- Paso 3: CAMBIAR 'tu_email@ejemplo.com' POR TU EMAIL REAL
UPDATE users SET rol = 'ADMIN' WHERE email = 'tu_email@ejemplo.com';

-- Paso 4: Verificar que funcionó
SELECT id, nombre, email, rol FROM users WHERE rol = 'ADMIN';

-- Deberías ver tu usuario con rol = 'ADMIN'
-- Luego instala el APK y haz login
-- ¡Listo! Deberías ver el Panel de Administración

