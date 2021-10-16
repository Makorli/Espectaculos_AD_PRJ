package Modelos;


import Controllers.ControladorCliente;
import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;

import java.util.ArrayList;
import java.util.List;

public class Inscripcion extends IDHolder {

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

    @Override
    public int getId() { return idInscripcion; }

    @Override
    public void setId(int id) { this.idInscripcion= id;}

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

    //Métodos de recuperacion de datos de relaciones del objeto Modelos.Empleado

    /**
     * Retorna el objeto cliente que ha realizado la inscripcion
     * @return Modelos.Cliente
     */
    public Cliente getCliente(){

        ControladorCliente cc = new ControladorCliente();
        Cliente cliente = cc.selectById(this.getIdCliente());
        
        return cliente;
    }

    /**
     * Retorna el objeto espectaculo al que se ha inscrito un cliente
     * @return Modelos.Espectaculo
     */
    public Espectaculo getEspectaculo(){

        ControladorEspectaculo cs = new ControladorEspectaculo();
        Espectaculo espectaculo = cs.selectById(this.getIdEspectaculo());

        return espectaculo;
    }





}
