import java.util.Date;

public class Inscripcion {

    //ATRIBUTOS

    private int idInscripcion;
    private int idCliente;//cambiado de cliente a int 200211003
    private int idEspectaculo;//cambiado de espectaculo a int 200211003
    private String fecha;//cambiado de Date a string 200211002

    //CONSTRUCTORES

    public Inscripcion() {
    }


    public Inscripcion(int idInscripcion, int idCliente, int idEspectaculo, String fecha) {
        this.idInscripcion = idInscripcion;
        this.idCliente = idCliente;
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

    public void setIdEspectaculo(int idEspectaculo) {
        this.idEspectaculo = idEspectaculo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
