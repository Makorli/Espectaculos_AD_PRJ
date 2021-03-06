package Modelos;

import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente extends IDHolder { //extiende de IDHolder para autoincrementales de DB4o

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

    @Override
    public void setId(int id) { this.idCliente = id; }

    @Override
    public int getId() {
        return idCliente;
    }

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
     *
     * @return ArrayList
     */
    public List<Espectaculo> getEspectaculosByCliente() {

        ControladorInscripciones ci = new ControladorInscripciones();
        List<Inscripcion> inscripciones;
        inscripciones = ci.selectAll();

        Espectaculo e;
        List<Espectaculo> espectaculosClienteList = new ArrayList<>();

        for (Inscripcion i : inscripciones) {
            if (i.getCliente().equals(this)) {
                e = i.getEspectaculo();
                espectaculosClienteList.add(e);
            }

        }

        return espectaculosClienteList;
    }


    //Metodos de compraci??n de objetos y visualizacion por consola

    /**
     * M??todo de visualziacion ra??pad de los datos del objeto por consola
     *
     * @return String con los valores del objeto
     */
    @Override
    public String toString() {
       /* return "\"Modelos.Cliente --> " +
                "idCliente=" + idCliente +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", baja=" + baja +
                '}';*/
        return dni + " - " + nombre + " " + apellidos ;
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
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return (idCliente == cliente.idCliente) ||
                (dni.equals(cliente.dni));
    }

    /**
     * Metodo de comparaci??n de objetos basados en los hashes que generan sus atributos.
     * Se realiza el hash con las claves primarias y campos ??nicos.
     *
     * @return Boolean
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCliente, dni);
    }


}
