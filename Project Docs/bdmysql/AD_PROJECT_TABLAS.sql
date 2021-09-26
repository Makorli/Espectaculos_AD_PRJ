/*Crear 1º*/
create table Empleado
(
    idEmpleado        int auto_increment,
    dni               varchar(9)            not null,
    nombre            varchar(40)           not null,
    apellidos         varchar(40)           not null,
    fechaNacimiento   varchar(10)           null,
    fechaContratacion varchar(10)           null,
    nacionalidad      varchar(40)           null,
    cargo             varchar(20)           null,
    baja              boolean default false not null,
    constraint Empleado_pk_Emp
        primary key (idEmpleado)
);

create unique index Empleado_dni_uindex
    on Empleado (dni);


create table Cliente
(
    idCliente       int auto_increment,
    dni             varchar(9)            not null,
    nombre          varchar(40)           not null,
    apellidos       varchar(40)           not null,
    fechaNacimiento varchar(10)           null,
    baja            boolean default false not null,
    constraint Cliente_pk_CLi
        primary key (idCliente)
);

create unique index Cliente_dni_uindex
    on Cliente (dni);

/*Crear 2º*/
create table Espectaculo
(
    idEspectaculo int auto_increment,
    numero        int                        not null,
    nombre        varchar(40)                not null,
    aforo         int          default null,
    descripcion   varchar(100) default null,
    lugar         varchar(40)  default null,
    coste         double       default null,
    fecha         varchar(10)  default null,
    horario       varchar(5)   default null,
    idResponsable int                        null,
    baja          boolean      default false not null,
    constraint Espectaculo_pk_Esp
        primary key (idEspectaculo),
    constraint Espectaculo_fk_Emp
        foreign key (idResponsable) references Empleado (idEmpleado)
);

create unique index Espectaculo_numero_uindex
    on Espectaculo (numero);

/*Crear 3º*/

create table Inscripcion
(
    idInscripcion int auto_increment,
    idEspectaculo int         not null,
    idCliente     int         not null,
    fecha         varchar(10) not null,
    constraint Inscripcion_pk_Ins
        primary key (idInscripcion),
    constraint Inscripcion_fk_Esp
        foreign key (idEspectaculo) references Espectaculo (idEspectaculo),
    constraint Inscripcion_fk_Cli
        foreign key (idCliente) references Cliente (idCliente)

);

create unique index Inscripcion_numero_uindex
    on Espectaculo (idEspectaculo,idResponsable,fecha);






