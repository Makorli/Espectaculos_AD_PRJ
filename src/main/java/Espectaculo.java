import java.util.Date;

public class Espectaculo {

    //ATRIBUTOS

    private int idEspectaculo;
    private int numero;
    private String nombre;
    private int aforo;
    private String descripcion;
    private String lugar;
    private Double coste;
    private String fecha;//cambiado de Date a string 200211002
    private String horario;
    private Boolean baja;
    private int idEmpleado;//cambiado de empleado a int 200211003

    //CONSTRUCTORES


    public Espectaculo() {
    }

    public Espectaculo(int idEspectaculo, int numero, String nombre, int aforo, String descripcion, String lugar, Double coste, String fecha, String horario, Boolean baja, int idEmpleado) {
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
        this.idEmpleado= idEmpleado;
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

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
