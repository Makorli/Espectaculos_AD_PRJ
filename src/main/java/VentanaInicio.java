import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio {

    private JLabel lbTituloParque;
    private JPanel JPGeneral;
    private JPanel JPVacio;

    public VentanaInicio(JFrame frame) {

        JMenuBar menuBar = new JMenuBar();

        //Menus: clientes, empleados, espectaculos....
        JMenu MenuClientes = new JMenu("Clientes");
        JMenu MenuEmpleados = new JMenu("Empleados");
        JMenu MenuEspectaculos = new JMenu("Espectáculos");
        JMenu MenuInscripciones = new JMenu("Inscripciones");
        JMenu MenuOtrasOpciones = new JMenu("Otras Opciones");


        // Items a agregar a cada menu.
        JMenuItem itemNuevoCliente = new JMenuItem("Nuevo");
        JMenuItem itemModificarCliente = new JMenuItem("Modificar");

        JMenuItem itemNuevoEmpleado = new JMenuItem("Nuevo");
        JMenuItem itemModificarEmpleado = new JMenuItem("Modificar");

        JMenuItem itemNuevoEspectaculo = new JMenuItem("Nuevo");
        JMenuItem itemModificarEspectaculo = new JMenuItem("Modificar");

        JMenuItem itemInscribirse = new JMenuItem("Inscripción");
        JMenuItem itemMostrarTodasInscripciones = new JMenuItem("Mostrar inscripciones");

        JMenuItem itemMetadatos = new JMenuItem("Metadatos");
        JMenuItem itemBusqueda = new JMenuItem("Buscar...");

        // Aqui añadimos el item a cada menu.
        MenuEmpleados.add(itemNuevoEmpleado);
        MenuEmpleados.add(itemModificarEmpleado);

        MenuClientes.add(itemNuevoCliente);
        MenuClientes.add(itemModificarCliente);

        MenuEspectaculos.add(itemNuevoEspectaculo);
        MenuEspectaculos.add(itemModificarEspectaculo);

        MenuInscripciones.add(itemInscribirse);
        MenuInscripciones.add(itemMostrarTodasInscripciones);

        MenuOtrasOpciones.add(itemMetadatos);
        MenuOtrasOpciones.add(itemBusqueda);

        //Formamos el menu bar
        menuBar.add(MenuEmpleados);
        menuBar.add(MenuClientes);
        menuBar.add(MenuEspectaculos);
        menuBar.add(MenuInscripciones);
        menuBar.add(MenuOtrasOpciones);


        frame.add(menuBar); //Añadir el menu bar al frame. Se tiene que añadir al frame principal porque de este se arrastra a todos.
        frame.setJMenuBar(menuBar);


      /**Cada vez que pulsemos en un item nos abrirá el panel inferior nuevo con los campos correspondientes a la tabla*/
        itemNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosClientes nuevoCliente = new DatosClientes();
                mostrarPanel(nuevoCliente.getJPClientes());
            }
        });

        itemModificarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosClientes modificarCliente = new DatosClientes();
                mostrarPanel(modificarCliente.getJPClientes());
            }
        });

        itemNuevoEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEmpleado nuevoEmpleado = new DatosEmpleado();
                mostrarPanel(nuevoEmpleado.getJPEmpleados());
            }
        });

        itemModificarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEmpleado modificarEmpleado = new DatosEmpleado();
                mostrarPanel(modificarEmpleado.getJPEmpleados());
            }
        });

        itemNuevoEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEspectaculo nuevoEspectaculo = new DatosEspectaculo();
                mostrarPanel(nuevoEspectaculo.getJPEspectaculos());
            }
        });

        itemModificarEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEspectaculo modificarEspectaculo = new DatosEspectaculo();
                mostrarPanel(modificarEspectaculo.getJPEspectaculos());
            }
        });

        itemInscribirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosInscripciones nuevaInscripcion = new DatosInscripciones();
                mostrarPanel(nuevaInscripcion.getJPInscripciones());
            }
        });

        itemMostrarTodasInscripciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarInscripciones mostrarInscripciones = new MostrarInscripciones();
                mostrarPanel(mostrarInscripciones.getJPTodasInscripciones());
            }
        });

        itemMetadatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarMetadatos mostrarMetadatos = new MostrarMetadatos();
                mostrarPanel(mostrarMetadatos.getJPMetadatos());
            }
        });

    }


    /**
     * Esta funcion nos permite reutilizar el frame y solo cambiar la parte inferior donde aparecen las pantallas de cliente
     * empleado, espectaculo....
     * */
    public void mostrarPanel(JPanel panel) {

        JPVacio.removeAll();
        JPVacio.add(panel);
        JPVacio.repaint();
        JPVacio.revalidate();


    }

    public JPanel getJPGeneral(JFrame f) {
        return JPGeneral;
    }
}
