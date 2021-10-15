
create table empleado
(
    idEmpleado        int auto_increment
        primary key,
    dni               varchar(9)           not null,
    nombre            varchar(40)          not null,
    apellidos         varchar(40)          not null,
    fechaNacimiento   varchar(10)          null,
    fechaContratacion varchar(10)          null,
    nacionalidad      varchar(40)          null,
    cargo             varchar(20)          null,
    baja              tinyint(1) default 0 not null,
    constraint Empleado_dni_uindex
        unique (dni)
);

create table espectaculo
(
    idEspectaculo int auto_increment
        primary key,
    numero        int                  not null,
    nombre        varchar(40)          not null,
    aforo         int                  null,
    descripcion   varchar(100)         null,
    lugar         varchar(40)          null,
    coste         double               null,
    fecha         varchar(10)          null,
    horario       varchar(20)          null,
    baja          tinyint(1) default 0 not null,
    idResponsable int                  null,
    constraint Espectaculo_numero_uindex
        unique (numero),
    constraint Inscripcion_numero_uindex
        unique (idEspectaculo, idResponsable, fecha),
    constraint Espectaculo_fk_Emp
        foreign key (idResponsable) references empleado (idEmpleado)
);

create table cliente
(
    idCliente       int auto_increment
        primary key,
    dni             varchar(9)           not null,
    nombre          varchar(40)          not null,
    apellidos       varchar(40)          not null,
    fechaNacimiento varchar(10)          null,
    baja            tinyint(1) default 0 not null,
    constraint Cliente_dni_uindex
        unique (dni)
);

create table inscripcion
(
    idInscripcion int auto_increment
        primary key,
    idCliente     int         not null,
    idEspectaculo int         not null,
    fecha         varchar(10) not null,
    constraint Inscripcion_fk_Cli
        foreign key (idCliente) references cliente (idCliente),
    constraint Inscripcion_fk_Esp
        foreign key (idEspectaculo) references espectaculo (idEspectaculo)
);

