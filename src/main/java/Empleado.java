import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Empleado {

    // ATRIBUTOS

    private int idEmpleado;
    private String dni;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private Date fechaContratacion;
    private String nacionalidad;
    private String cargo;
    private Boolean baja;

    //CONSTRUCTORES

    public Empleado() {
    }

    public Empleado(int idEmpleado, String dni, String nombre, String apellidos, Date fechaNacimiento, Date fechaContratacion, String nacionalidad, String cargo, Boolean baja) {
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

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
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

    //Metodos de recuperacion de datos de relaciones del objeto Empleado

    /**
     * Retorna una lista de objetos espectaculos de los cuales es responsable
     * @return ArrayList
     */
    public List<Espectaculo> getEspectaculos(){
        List <Espectaculo> espectaculosList = new ArrayList<>();
        //TODO
        return espectaculosList;
    }

    //Metodos de compración de objetos y visualizacion por consola

    /**
     * Método de visualizacion rápida de los datos del objeto por consola
     * @return String con los valores del objeto
     */
    @Override
    public String toString() {
        return "Empleado-->" +
                "idEmpleado=" + idEmpleado +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaContratacion=" + fechaContratacion +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cargo='" + cargo + '\'' +
                ", baja=" + baja +
                '}';
    }

    /**
     * Metodo de comparación de objetos basados en los valores que tienen sus atributos.
     * Se comparan las claves primarias o identificadores únicos
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
     * Metodo de comparación de objetos basados en los hashes que generan sus atributos.
     * Se realiza el hash con las claves primarias y campos únicos.
     * @return Boolean
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, dni);
    }
}
