INSERT INTO bdmysql.cliente (dni, nombre, apellidos, fechaNacimiento, baja)
VALUES ('x9996663a', 'maria', 'roda', '23/09/1976', DEFAULT);

INSERT INTO bdmysql.cliente (dni, nombre, apellidos, fechaNacimiento, baja)
VALUES ('x0000000a', 'lola', 'martinez', '30/11/1966', DEFAULT);

INSERT INTO bdmysql.empleado (dni, nombre, apellidos, fechaNacimiento, fechaContratacion, nacionalidad, cargo, baja)
VALUES ('77778888A', 'fran', 'orlando', '04/10/1977', '05/05/2000', 'Española', 'TIC', DEFAULT);

INSERT INTO bdmysql.espectaculo (numero, nombre, aforo, descripcion, lugar, coste, fecha, horario, idResponsable, baja)
VALUES (1001, 'MAGIA 1', 40, 'MAGIA Y ACROBACIOAS', 'SALA 4', 30, 'martes', '21:00', 1, DEFAULT);

INSERT INTO bdmysql.inscripcion (idEspectaculo, idCliente, fecha)
VALUES (1, 2, '25/09/2021');



