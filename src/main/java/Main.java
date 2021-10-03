

import java.sql.SQLException;

public class Main {
    //TODO


    public static void main(String[] args) throws SQLException, IllegalAccessException {


        int ids = 1;
        String dni = "x7777777a";
        String nombre = "nombretest1";
        String apellidos = "insertcontrolador";
        String fechaNacimiento = "02/10/2021";
        String fechaAlta = "13/05/2000";

        String nacionalidad = "Portugues";
        String cargo = "Jefe Area";


        Integer numero = 1002;
        Integer aforo = 20;
        String descripcion = "descripcion bla bla bla";
        String lugar = "lugar bla bla";
        Double coste = 558.25;
        String fecha = "13/05/2021";
        String horario = "FullD";
        Integer idresponsable = 2;


        boolean baja = false;

        Cliente cli = new Cliente(ids, dni, nombre, apellidos, fechaNacimiento, baja);



        ControladorCliente control = new ControladorCliente(cli);
        control.add(cli);
        //control.update(cli);


    }
}
