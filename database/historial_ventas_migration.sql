USE tortas_cubanas_db;

SET @pago_tiene_metodo := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = 'tortas_cubanas_db'
      AND TABLE_NAME = 'pago'
      AND COLUMN_NAME = 'metodoPago'
);

SET @sql_metodo_pago := IF(
    @pago_tiene_metodo = 0,
    'ALTER TABLE pago ADD COLUMN metodoPago VARCHAR(30) NULL AFTER propina',
    'SELECT 1'
);

PREPARE stmt_metodo_pago FROM @sql_metodo_pago;
EXECUTE stmt_metodo_pago;
DEALLOCATE PREPARE stmt_metodo_pago;

UPDATE pago
SET metodoPago = 'EFECTIVO'
WHERE metodoPago IS NULL OR TRIM(metodoPago) = '';
