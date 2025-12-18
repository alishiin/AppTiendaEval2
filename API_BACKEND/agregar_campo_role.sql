-- Script para agregar el campo 'role' a la tabla users
-- Ejecutar este script en tu base de datos MySQL

USE tienda_eval2;

-- Agregar columna role si no existe
ALTER TABLE users
ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'USER' AFTER comuna;

-- Actualizar usuarios existentes para que tengan el rol USER por defecto
UPDATE users
SET role = 'USER'
WHERE role IS NULL OR role = '';

-- Crear usuario administrador (si no existe)
-- NOTA: Cambiar email y password según tus necesidades
INSERT INTO users (nombre, email, password, rut, direccion, comuna, role)
VALUES ('Administrador', 'admin@tienda.cl', 'admin123', '11111111-1', 'Oficina Central', 'Santiago - Santiago Centro', 'ADMIN')
ON DUPLICATE KEY UPDATE role = 'ADMIN';

-- Verificar que se creó correctamente
SELECT id, nombre, email, role FROM users WHERE role = 'ADMIN';

-- Ver estructura de la tabla
DESCRIBE users;

