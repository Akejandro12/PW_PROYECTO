
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
