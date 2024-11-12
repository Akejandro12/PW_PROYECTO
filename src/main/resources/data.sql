
-- Insertar el rol ADMIN si no existe
INSERT INTO rol (nombre)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_ADMIN');

-- Insertar el rol VENDEDOR si no existe
INSERT INTO rol (nombre)
SELECT 'ROLE_VENDEDOR'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_VENDEDOR');

-- Insertar el rol USER si no existe
INSERT INTO rol (nombre)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_USER');

-- Esto se hace cuando quieras cambiar el rol de un usuario
DELETE FROM usuarios_roles
WHERE usuario_id = 1 AND rol_id = (SELECT id FROM rol WHERE nombre = 'ROLE_USER');

INSERT INTO usuarios_roles (usuario_id, rol_id)
VALUES (1, 2);  -- Asigna el rol VENDEDOR al usuario con id = 1
