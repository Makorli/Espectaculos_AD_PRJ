package Modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Espectaculo {

    //ATRIBUTOS

    private int idEspectaculo;
    private int numero;
    private String nombre;
    private int aforo;
    private String descripcion;
    private String lugar;
    private Double coste;
    private String fecha;
    private String horario;
    private Boolean baja;
    private int idResponsable;

    //CONSTRUCTORES


    public Espectaculo() {
    }

    public Espectaculo(int idEspectaculo, int numero, String nombre, int aforo, String descripcion, String lugar, Double coste, String fecha, String horario, Boolean baja, int idResponsable) {
        this.idEspectaculo = idEspectaculo;
        this.numero = numero;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.coste = coste;
        this.fecha = fecha;
        this.horario = horario;
        this.baja = baja;
        this.idResponsable = idResponsable;
    }
//GETTERS & SETTERS

    public int getIdEspectaculo() {
        return idEspectaculo;
    }

    public void setIdEspectaculo(int idEspectaculo) {
        this.idEspectaculo = idEspectaculo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Boolean getBaja() {
        return baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    //Metodos de recuperacion de datos de relaciones del objeto Modelos.Empleado

    /**
     * Retorna el objeto Modelos.Empleado que se responsabiliza del espectaculo.
     * Se utilizará el campo idResponsable
     * @return
     */
    public Empleado getResponsable(){
        Empleado responsable = new Empleado();
        //TODO
        return responsable;
    }

    /**
     * Retorna una lista de objetos cliente que han asistido a ese espectaculo
     * @return ArrayList
     */
    public List<Cliente> getClientesInscritos(){
        List <Cliente> clientesList = new ArrayList<>();
        //TODO
        return clientesList;
    }

    /**
     * Retorna una lista de objetos Modelos.Inscripcion en la que figuran los datos de horario
     * de las inscripciones y los ids de cliente y espectaculos correspondientes.
     * @return ArrayList
     */
    public List<Inscripcion> getInscripciones(){
        List <Inscripcion> inscripcionesList = new ArrayList<>();
        //TODO
        return inscripcionesList;
    }

    //Metodos de compración de objetos y visualizacion por consola

    /**
     * Método de visualizacion rápida de los datos del objeto por consola
     * @return String con los valores del objeto
     */
    @Override
    public String toString() {
        return "Modelos.Espectaculo{" +
                "idEspectaculo=" + idEspectaculo +
                ", numero=" + numero +
                ", nombre='" + nombre + '\'' +
                ", aforo=" + aforo +
                ", descripcion='" + descripcion + '\'' +
                ", lugar='" + lugar + '\'' +
                ", coste=" + coste +
                ", fecha='" + fecha + '\'' +
                ", horario='" + horario + '\'' +
                ", baja=" + baja +
                ", idResponsable=" + idResponsable +
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
        if (!(o instanceof Espectaculo)) return false;
        Espectaculo that = (Espectaculo) o;
        return (idEspectaculo == that.idEspectaculo) ||
                (numero == that.numero) ||
                (nombre.trim().equalsIgnoreCase(that.nombre.trim())
                );
    }

    /**
     * Metodo de comparación de objetos basados en los hashes que generan sus atributos.
     * Se realiza el hash con las claves primarias y campos únicos.
     * @return Boolean
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEspectaculo, numero, nombre);
    }
}
