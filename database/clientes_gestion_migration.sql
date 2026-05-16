USE tortas_cubanas_db;

SET @cliente_tiene_activo := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = 'tortas_cubanas_db'
      AND TABLE_NAME = 'cliente'
      AND COLUMN_NAME = 'activo'
);

SET @sql_cliente_activo := IF(
    @cliente_tiene_activo = 0,
    'ALTER TABLE cliente ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1',
    'SELECT 1'
);

PREPARE stmt_cliente_activo FROM @sql_cliente_activo;
EXECUTE stmt_cliente_activo;
DEALLOCATE PREPARE stmt_cliente_activo;

UPDATE cliente
SET activo = 1
WHERE activo IS NULL;
