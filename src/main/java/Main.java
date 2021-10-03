import Controllers.*;
import Miscelaneous.*;
import Modelos.*;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException {

        //ATRIBUTOS PARA PRUEBAS MARIA
        int ids = 2;
        String dni = "x9812397g";
        String nombre = "eEspectaculo 2";
        String apellidos = "aup";
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
        Integer idresponsable = 1;

        boolean baja = false;


        ////////////controles Cliente test ok
/*        ControladorCliente control = new ControladorCliente();

        Cliente cli = new Cliente(ids, dni, nombre, apellidos, fechaNacimiento, baja);

        //control.add(cli);
        //control.update(cli);

        Cliente cliById = control.selectById(ids);
        System.out.println(cliById.getNombre());

        List<Cliente> clientes = (control.selectAll());
        for (Cliente cliente:clientes
        ) {
            System.out.println(cliente.getIdCliente() + "- " + cliente.getNombre() );

        }*/

        ////////////controles Empleado test ok
/*        ControladorEmpleado control = new ControladorEmpleado();
        Empleado emp = new Empleado(ids,dni,nombre,apellidos,fechaNacimiento,fechaAlta,nacionalidad,cargo,baja);

        //control.add(emp);
        //control.update(emp);

        Empleado empById = control.selectById(ids);
        System.out.println(empById.getNombre());

        List<Empleado> empleados = (control.selectAll());
        for (Empleado empleado:empleados
        ) {
            System.out.println(empleado.getIdEmpleado() + "- " + empleado.getNombre() );
        }*/

        ////////////controles Espectaculo test ok
/*        ControladorEspectaculo control = new ControladorEspectaculo();
        Espectaculo esp = new Espectaculo (ids,numero,nombre,aforo,descripcion,lugar,coste,fecha,horario,baja,idresponsable);
        //control.add(esp);
        control.update(esp);

        Espectaculo espById = control.selectById(ids);
        System.out.println(espById.getNombre());

        List<Espectaculo> espectaculos = (control.selectAll());
        for (Espectaculo espectaculo:espectaculos
        ) {
            System.out.println(espectaculo.getIdEspectaculo() + "- " + espectaculo.getNombre() );
        }*/

    }

}
