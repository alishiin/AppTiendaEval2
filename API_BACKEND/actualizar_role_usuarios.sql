-- Script simplificado para agregar el campo 'role' a la tabla users
-- Compatible con MySQL 5.7+ y MariaDB 10.2+

USE tienda_eval2;

-- Paso 1: Agregar columna role
ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER';

-- Paso 2: Actualizar usuarios existentes
UPDATE users SET role = 'USER' WHERE role IS NULL OR role = '';

-- Paso 3: Crear o actualizar usuario administrador
-- Primero verificar si existe
SELECT COUNT(*) as existe FROM users WHERE email = 'admin@tienda.cl';

-- Si no existe, insertar (ejecutar manualmente si es necesario)
-- INSERT INTO users (nombre, email, password, rut, direccion, comuna, role)
-- VALUES ('Administrador', 'admin@tienda.cl', 'admin123', '11111111-1', 'Oficina Central', 'Santiago - Santiago Centro', 'ADMIN');

-- Si ya existe, actualizar el role
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@tienda.cl';

-- Verificar usuarios admin
SELECT id, nombre, email, rut, direccion, comuna, role FROM users WHERE role = 'ADMIN';

-- Ver todos los usuarios
SELECT id, nombre, email, role FROM users;

