package Modelos;

import Controllers.ControladorEspectaculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Empleado extends IDHolder {

    // ATRIBUTOS

    private int idEmpleado;
    private String dni;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String fechaContratacion;
    private String nacionalidad;
    private String cargo;
    private Boolean baja;

    //CONSTRUCTORES

    public Empleado() {
    }


    public Empleado(int idEmpleado, String dni, String nombre, String apellidos, String fechaNacimiento, String fechaContratacion, String nacionalidad, String cargo, Boolean baja) {
        this.idEmpleado = idEmpleado;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaContratacion = fechaContratacion;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
        this.baja = baja;
    }

    //GETTER & SETTERS

    @Override
    public int getId() { return idEmpleado; }

    @Override
    public void setId(int id) { this.idEmpleado = id; }

    public int getIdEmpleado() { return idEmpleado; }

    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getBaja() {
        return baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    //Metodos de recuperacion de datos de relaciones del objeto Modelos.Empleado

    /**
     * Retorna una lista de objetos espectaculos de los cuales es responsable
     *
     * @return ArrayList
     */
    public List<Espectaculo> getEspectaculos() {
        List<Espectaculo> espectaculosList = new ArrayList<>();

        ControladorEspectaculo ce = new ControladorEspectaculo();


        return espectaculosList;
    }

    //Metodos de compraci??n de objetos y visualizacion por consola

    /**
     * M??todo de visualizacion r??pida de los datos del objeto por consola
     *
     * @return String con los valores del objeto
     */
    @Override
    public String toString() {
/*        return "Modelos.Empleado-->" +
                "idEmpleado=" + idEmpleado +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaContratacion=" + fechaContratacion +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cargo='" + cargo + '\'' +
                ", baja=" + baja +
                '}';*/
        return  dni + " - " + nombre + " " + apellidos ;
    }

    /**
     * Metodo de comparaci??n de objetos basados en los valores que tienen sus atributos.
     * Se comparan las claves primarias o identificadores ??nicos
     *
     * @return Boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return (idEmpleado == empleado.idEmpleado) ||
                (dni.equals(empleado.dni));
    }

    /**
     * Metodo de comparaci??n de objetos basados en los hashes que generan sus atributos.
     * Se realiza el hash con las claves primarias y campos ??nicos.
     *
     * @return Boolean
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, dni);
    }


}


