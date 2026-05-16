USE tortas_cubanas_db;

SET @pedido_tiene_tipoorden := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = 'tortas_cubanas_db'
      AND TABLE_NAME = 'pedido'
      AND COLUMN_NAME = 'tipoOrden'
);

SET @sql_tipoorden_col := IF(
    @pedido_tiene_tipoorden = 0,
    'ALTER TABLE pedido ADD COLUMN tipoOrden VARCHAR(45) NULL',
    'SELECT 1'
);

PREPARE stmt_tipoorden_col FROM @sql_tipoorden_col;
EXECUTE stmt_tipoorden_col;
DEALLOCATE PREPARE stmt_tipoorden_col;

CREATE TABLE IF NOT EXISTS tipoorden (
    idTipoOrden INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(45) NULL,
    PRIMARY KEY (idTipoOrden)
) ENGINE=InnoDB;

INSERT INTO tipoorden (nombre)
SELECT 'PARA COMER AQUI'
WHERE NOT EXISTS (
    SELECT 1 FROM tipoorden WHERE nombre = 'PARA COMER AQUI'
);

INSERT INTO tipoorden (nombre)
SELECT 'A DOMICILIO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipoorden WHERE nombre = 'A DOMICILIO'
);

UPDATE estadopedido
SET nombre = 'Pendiente'
WHERE idEstadoPedido = 1;

UPDATE estadopedido
SET nombre = 'Cancelado'
WHERE idEstadoPedido = 2;

INSERT INTO estadopedido (nombre)
SELECT 'En Preparacion'
WHERE NOT EXISTS (
    SELECT 1 FROM estadopedido WHERE nombre = 'En Preparacion'
);

INSERT INTO estadopedido (nombre)
SELECT 'Listo para entrega'
WHERE NOT EXISTS (
    SELECT 1 FROM estadopedido WHERE nombre = 'Listo para entrega'
);

INSERT INTO estadopedido (nombre)
SELECT 'Entregado'
WHERE NOT EXISTS (
    SELECT 1 FROM estadopedido WHERE nombre = 'Entregado'
);

INSERT INTO estadocaja (nombre)
SELECT 'Cerrada'
WHERE NOT EXISTS (
    SELECT 1 FROM estadocaja WHERE nombre = 'Cerrada'
);

INSERT INTO estadocaja (nombre)
SELECT 'Arqueada'
WHERE NOT EXISTS (
    SELECT 1 FROM estadocaja WHERE nombre = 'Arqueada'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta Cubana Especial', 120.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta Cubana Especial'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta de Milanesa', 85.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta de Milanesa'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta de Pierna Adobada', 90.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta de Pierna Adobada'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta de Jamon y Queso', 65.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta de Jamon y Queso'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta Hawaiana', 95.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta Hawaiana'
);

INSERT INTO producto (nombre, precioBase, categoria)
SELECT 'Torta de Chorizo con Huevo', 80.00, 'TORTAS'
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre = 'Torta de Chorizo con Huevo'
);

INSERT INTO administrador (contrasena, tipo)
SELECT 'admin123', 'SuperAdmin'
WHERE NOT EXISTS (
    SELECT 1 FROM administrador WHERE contrasena = 'admin123' AND tipo = 'SuperAdmin'
);

INSERT INTO cajero (contrasena, tipo)
SELECT 'caja456', 'Turno Matutino'
WHERE NOT EXISTS (
    SELECT 1 FROM cajero WHERE contrasena = 'caja456' AND tipo = 'Turno Matutino'
);

INSERT INTO cliente (telefono, nombre, direccion, rfc, correo)
SELECT '5512345678', 'Juan Perez', 'Av. Reforma 123, CDMX', 'PERJ850101ABC', 'juan.perez@email.com'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE telefono = '5512345678'
);

INSERT INTO cliente (telefono, nombre, direccion, rfc, correo)
SELECT '5598765432', 'Maria Garcia', 'Calle Roble 45, Col. Juarez', NULL, 'maria.g@email.com'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE telefono = '5598765432'
);

INSERT INTO cliente (telefono, nombre, direccion, rfc, correo)
SELECT '5544332211', 'Carlos Lopez', 'Interior B, Depto 4, Condesa', 'LOCC900515XYZ', 'carlos_l@email.com'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE telefono = '5544332211'
);

INSERT INTO cliente (telefono, nombre, direccion, rfc, correo)
SELECT '5566778899', 'Ana Martinez', 'Privada Sauces 12, Polanco', NULL, 'ana.mtz@email.com'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE telefono = '5566778899'
);

INSERT INTO cliente (telefono, nombre, direccion, rfc, correo)
SELECT '5500112233', 'Pedro Sanchez', 'Av. Insurgentes Sur 1005', 'SANP781120HGT', 'pedro.s@email.com'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE telefono = '5500112233'
);

INSERT INTO detalleproducto (detalle, Producto_idProducto)
SELECT 'Con todo y extra picante', p.idProducto
FROM producto p
WHERE p.nombre = 'Torta Cubana Especial'
  AND NOT EXISTS (
      SELECT 1
      FROM detalleproducto d
      WHERE d.detalle = 'Con todo y extra picante'
        AND d.Producto_idProducto = p.idProducto
  );

INSERT INTO detalleproducto (detalle, Producto_idProducto)
SELECT 'Sin cebolla', p.idProducto
FROM producto p
WHERE p.nombre = 'Torta de Milanesa'
  AND NOT EXISTS (
      SELECT 1
      FROM detalleproducto d
      WHERE d.detalle = 'Sin cebolla'
        AND d.Producto_idProducto = p.idProducto
  );

INSERT INTO detalleproducto (detalle, Producto_idProducto)
SELECT 'Bien doradita', p.idProducto
FROM producto p
WHERE p.nombre = 'Torta de Pierna Adobada'
  AND NOT EXISTS (
      SELECT 1
      FROM detalleproducto d
      WHERE d.detalle = 'Bien doradita'
        AND d.Producto_idProducto = p.idProducto
  );

INSERT INTO carrito (ultimaActualizacion, Cliente_idCliente)
SELECT NOW(), c.idCliente
FROM cliente c
WHERE c.telefono = '5512345678'
  AND NOT EXISTS (
      SELECT 1 FROM carrito k WHERE k.Cliente_idCliente = c.idCliente
  );

INSERT INTO caja (
    montoInicial,
    saldoFinal,
    totalVentas,
    fechaApertura,
    fechaCierre,
    EstadoCaja_idEstadoCaja,
    Administrador_idAdministrador
)
SELECT 1000.00, 1200.00, 200.00, NOW(), NULL, 1, 1
WHERE NOT EXISTS (
    SELECT 1
    FROM caja
    WHERE Administrador_idAdministrador = 1
      AND EstadoCaja_idEstadoCaja = 1
);
