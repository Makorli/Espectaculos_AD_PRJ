UPDATE bdmysql.cliente t
SET t.dni = 'x1111111a'
WHERE t.idCliente = 2;

UPDATE bdmysql.cliente t
SET t.dni    = 'x1111111a',
    t.nombre = 'JUANA',
    t.baja   = 1
WHERE t.idCliente = 2;

