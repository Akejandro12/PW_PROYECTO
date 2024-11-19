
-- Insertar los diferentes roles
INSERT INTO rol (nombre)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_ADMIN');

INSERT INTO rol (nombre)
SELECT 'ROLE_VENDEDOR'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_VENDEDOR');

INSERT INTO rol (nombre)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_USER');




-- Esto se hace cuando quieras cambiar el rol de un usuario
DELETE FROM usuarios_roles
WHERE usuario_id = 1 AND rol_id = (SELECT id FROM rol WHERE nombre = 'ROLE_USER');

INSERT INTO usuarios_roles (usuario_id, rol_id)
VALUES (1, 2);  -- Asigna el rol VENDEDOR al usuario con id = 1




-- Trigger
DELIMITER $$

CREATE TRIGGER after_venta_insert
    AFTER UPDATE ON producto
    FOR EACH ROW
BEGIN
    DECLARE cantidad_vendida INT;

    -- Solo procesamos la venta si la cantidad ha disminuido (significa que se ha realizado una venta)
    SET cantidad_vendida = OLD.cantidad - NEW.cantidad;

    -- Verificamos si la cantidad ha disminuido debido a una venta
    IF cantidad_vendida > 0 THEN
        -- Verificamos si ya se ha registrado una venta para este producto (usando la tabla de control o la tabla de ventas)
        IF NOT EXISTS (SELECT 1 FROM venta WHERE producto_id = OLD.id AND fecha_hora > NOW() - INTERVAL 1 HOUR) THEN
            -- Si no existe una venta registrada, procedemos a registrar la venta en la tabla `venta`
            INSERT INTO venta (usuario, producto_id, cantidad, fecha_hora)
            VALUES ('NombreDelUsuario', OLD.id, cantidad_vendida, NOW());

            -- Registrar la venta en la tabla de control para evitar duplicar ventas
    INSERT INTO venta_control (producto_id, usuario_nombre, fecha_hora)
    VALUES (OLD.id, 'NombreDelUsuario', NOW());
END IF;
END IF;
END$$

DELIMITER ;


