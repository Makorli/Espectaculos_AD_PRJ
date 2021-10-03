import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inscripcion {

    //ATRIBUTOS

    private int idInscripcion;
    private int idCliente;
    private int idEspectaculo;
    private String fecha;

    //CONSTRUCTORES

    public Inscripcion() {
    }

    public Inscripcion(int idInscripcion, int idcliente, int idEspectaculo, String fecha) {
        this.idInscripcion = idInscripcion;
        this.idCliente = idcliente;
        this.idEspectaculo = idEspectaculo;
        this.fecha = fecha;
    }

    //GETTERS & SETTERS

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEspectaculo() {
        return idEspectaculo;
    }

    public void setIdEspectaculo(int espectaculo) {
        this.idEspectaculo = espectaculo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    //Métodos de recuperacion de datos de relaciones del objeto Empleado

    /**
     * Retorna el objeto cliente que ha realizado la inscripcion
     * @return Cliente
     */
    public Cliente getCliente(){
        Cliente cliente = new Cliente();
        //TODO
        return cliente;
    }

    /**
     * Retorna el objeto espectaculo al que se ha inscrito un cliente
     * @return Espectaculo
     */
    public Espectaculo getEspectaculo(){
        Espectaculo espectaculo = new Espectaculo();
        //TODO
        return espectaculo;
    }

}
