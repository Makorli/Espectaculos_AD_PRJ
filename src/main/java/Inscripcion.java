import java.util.Date;

public class Inscripcion {

    //ATRIBUTOS

    private int idInscripcion;
    private Cliente cliente;
    private Espectaculo espectaculo;
    private String fecha;//cambiado de Date a string 200211002

    //CONSTRUCTORES

    public Inscripcion() {
    }

    public Inscripcion(int idInscripcion, Cliente cliente, Espectaculo espectaculo, String fecha) {
        this.idInscripcion = idInscripcion;
        this.cliente = cliente;
        this.espectaculo = espectaculo;
        this.fecha = fecha;
    }

    //GETTERS & SETTERS

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Espectaculo getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
