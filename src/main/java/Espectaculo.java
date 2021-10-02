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
    private Date fecha;
    private String horario;
    private Boolean baja;
    private Empleado empleado;

    //CONSTRUCTORES


    public Espectaculo() {
    }

    public Espectaculo(int idEspectaculo, int numero, String nombre, int aforo, String descripcion, String lugar, Double coste, Date fecha, String horario, Boolean baja, Empleado empleado) {
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
        this.empleado = empleado;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
