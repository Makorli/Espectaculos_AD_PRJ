import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Cliente {

    // ATRIBUTOS 

    private int idCliente;
    private String dni;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private Boolean baja;

    //CONSTRUCTORES

    public Cliente() {
    }

    public Cliente(int idCliente, String dni, String nombre, String apellidos, String fechaNacimiento, Boolean baja) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.baja = baja;
    }

    //GETTER & SETTERS

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getBaja() {
        return baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    //Metodos de recuperacion de datos de relaciones del objeto CLiente

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
     * Método de visualziacion raípad de los datos del objeto por consola
     * @return String con los valores del objeto
     */
    @Override
    public String toString() {
        return "\"Cliente --> " +
                "idCliente=" + idCliente +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
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
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return (idCliente == cliente.idCliente) ||
                (dni.equals(cliente.dni));
    }

    /**
     * Metodo de comparación de objetos basados en los hashes que generan sus atributos.
     * Se realiza el hash con las claves primarias y campos únicos.
     * @return Boolean
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCliente, dni);
    }
}
