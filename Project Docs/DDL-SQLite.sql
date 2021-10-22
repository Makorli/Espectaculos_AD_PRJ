


create table Inscripcion
(
    idInscripcion INTEGER
        primary key autoincrement,
    idCliente     INTEGER not null
        references Cliente,
    idEspectaculo INTEGER not null
        references Espectaculo,
    fecha         TEXT    not null
);

create table Espectaculo
(
    idEspectaculo INTEGER
        primary key autoincrement
        unique,
    numero        INTEGER not null,
    nombre        TEXT    not null,
    aforo         INTEGER not null,
    descripcion   TEXT,
    lugar         TEXT,
    coste         REAL,
    fecha         TEXT,
    horario       TEXT,
    baja          INTEGER default 0,
    idResponsable INTEGER
        references Empleado
);

create table Empleado
(
    idEmpleado        INTEGER
        primary key autoincrement
        unique,
    dni               TEXT not null,
    nombre            TEXT not null,
    apellidos         TEXT not null,
    fechaNacimiento   TEXT,
    fechaContratacion TEXT,
    nacionalidad      TEXT,
    cargo             TEXT,
    baja              INTEGER default 0
);

create table Cliente
(
    idCliente       INTEGER
        primary key autoincrement
        unique,
    dni             TEXT not null,
    nombre          TEXT not null,
    apellidos       TEXT not null,
    fechaNacimiento TEXT,
    baja            INTEGER default 0
);

