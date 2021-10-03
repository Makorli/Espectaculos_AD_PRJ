import java.util.Date;

public class Cliente {

    // ATRIBUTOS 

    private int idCliente;
    private String dni;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;//cambiado de Date a string 200211002
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

    public void setIdCliente(int  idCliente) {
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


}
