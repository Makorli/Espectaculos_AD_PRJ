CREATE SEQUENCE EmpleadoId_seq START WITH 1;
create table Empleado
(
    idEmpleado        number  default EmpleadoId_seq.nextval  not null,
    dni               varchar(9)            not null,
    nombre            varchar(40)           not null,
    apellidos         varchar(40)           not null,
    fechaNacimiento   varchar(10)           null,
    fechaContratacion varchar(10)           null,
    nacionalidad      varchar(40)           null,
    cargo             varchar(20)           null,
    baja              number(1) default 0 not null,
    constraint Empleado_pk_Emp
        primary key (idEmpleado),
    constraint Empleado_ck_baja
        check (baja in (0,1))
);

CREATE SEQUENCE ClienteId_seq START WITH 1;
create table Cliente
(
    idCliente       number default ClienteId_seq.nextval not null,
    dni             varchar(9)            not null,
    nombre          varchar(40)           not null,
    apellidos       varchar(40)           not null,
    fechaNacimiento varchar(10)           null,
    baja            number(1) default 0 not null,
    constraint Cliente_pk_CLi
        primary key (idCliente),
    constraint Cliente_ck_dni UNIQUE (dni),
    constraint Cliente_ck_baja
        check ( baja in (0,1) )
);

CREATE SEQUENCE EspectaculoId_seq START WITH 1;
create table Espectaculo
(
    idEspectaculo number default EspectaculoID_seq.nextval not null,
    numero        number       not null,
    nombre        varchar(40)  not null,
    aforo         number       null,
    descripcion   varchar(100) null,
    lugar         varchar(40)  null,
    coste         number(*,2)  null,
    fecha         varchar(10)  null,
    horario       varchar(20)  null,
    idResponsable number       not null,
    baja          number(1)    default 0 not null,
    constraint Espectaculo_pk_Esp
        primary key (idEspectaculo),
    constraint Espectaculo_fk_Emp
        foreign key (idResponsable) references Empleado (idEmpleado),
    constraint Espectaculo_ck_numero UNIQUE (numero),
    constraint Espectaculo_ck_baja check ( baja in (0,1) )
);

CREATE SEQUENCE InscripcionId_seq START WITH 1;
create table Inscripcion
(
    idInscripcion number default InscripcionID_seq.nextval not null,
    idEspectaculo number         not null,
    idCliente     number         not null,
    fecha         varchar(10)    not null,
    constraint Inscripcion_pk_Ins
        primary key (idInscripcion),
    constraint Inscripcion_fk_Esp
        foreign key (idEspectaculo) references Espectaculo (idEspectaculo),
    constraint Inscripcion_fk_Cli
        foreign key (idCliente) references Cliente (idCliente)
);