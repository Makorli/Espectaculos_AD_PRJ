

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //TODO


    public static void main(String[] args) throws SQLException, IllegalAccessException {

        //ATRIBUTOS PARA PRUEBAS MARIA

        int ids = 1;
        String dni = "x444444g";
        String nombre = "elcuartoadd";
        String apellidos = "update3";
        String fechaNacimiento = "03/10/2021";
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


        //controles
        ControladorCliente control = new ControladorCliente();

        Cliente cli = new Cliente(ids, dni, nombre, apellidos, fechaNacimiento, baja);
        //control.add(cli);
        //control.update(cli);


        Cliente cliById = control.selectById(3);
        System.out.println(cliById.getNombre());


        List<Cliente> clientes = (control.selectAll());
        for (Cliente cliente:clientes
             ) {
            System.out.println(cliente.getIdCliente() + "- " + cliente.getNombre() );

        }


    }
}
