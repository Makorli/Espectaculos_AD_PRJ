package Vistas;

import Controllers.ControladorCliente;
import Controllers.ControladorEmpleado;
import Controllers.ControladorEspectaculo;
import Modelos.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VentanaInicio {

    private ControladorEspectaculo cs;
    private ControladorEmpleado ce;
    private ControladorCliente cc;
    private JLabel lbTituloParque;
    private JPanel JPGeneral;
    private JPanel JPVacio;
    private List<Empleado> empleados;

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
        JMenuItem itemSalirParque = new JMenuItem("Salir de este parque.");

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
        MenuOtrasOpciones.add(itemSalirParque);

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

                nuevoCliente.renombrarBtnGuardar("Guardar");
                nuevoCliente.setCbBajaState(false); //
                nuevoCliente.setLstEmpleadosState(false);
                nuevoCliente.setBtnBajaState(false);

                cc = new ControladorCliente();
                nuevoCliente.mostrarClientes(cc.selectAll());
                mostrarPanel(nuevoCliente.getJPClientes());
            }
        });

        itemModificarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosClientes modificarCliente = new DatosClientes();

                modificarCliente.renombrarBtnGuardar("Modificar");
                modificarCliente.setCbBajaState(false); //

                cc = new ControladorCliente();
                modificarCliente .mostrarClientes(cc.selectAll());
                mostrarPanel(modificarCliente.getJPClientes());
            }
        });


        itemNuevoEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEmpleado nuevoEmpleado = new DatosEmpleado();

                nuevoEmpleado.renombrarBtnGuardar("Guardar");
                nuevoEmpleado.setCbBajaState(false); //
                nuevoEmpleado.setLstEmpleadosState(false);
                nuevoEmpleado.setBtnBajaState(false);

                ce = new ControladorEmpleado();
                nuevoEmpleado.mostrarEmpleados(ce.selectAll());
                mostrarPanel(nuevoEmpleado.getJPEmpleados());
            }
        });

        itemModificarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEmpleado modificarEmpleado = new DatosEmpleado();
                modificarEmpleado.renombrarBtnGuardar("Modificar");
                modificarEmpleado.setCbBajaState(false); //
                ce = new ControladorEmpleado();
                modificarEmpleado.mostrarEmpleados(ce.selectAll());
                mostrarPanel(modificarEmpleado.getJPEmpleados());
            }
        });


        itemNuevoEspectaculo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatosEspectaculo nuevoEspectaculo = new DatosEspectaculo();

                nuevoEspectaculo.renombrarBtnGuardar("Guardar");
                nuevoEspectaculo.setCbBajaState(false); //
                nuevoEspectaculo.setLstEmpleadosState(false);
                nuevoEspectaculo.setBtnBajaState(false);

                cs = new ControladorEspectaculo();
                nuevoEspectaculo.mostrarEspectaculos(cs.selectAll());
                mostrarPanel(nuevoEspectaculo.getJPEspectaculos());
            }
        });

        itemModificarEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEspectaculo modificarEspectaculo = new DatosEspectaculo();

                modificarEspectaculo .renombrarBtnGuardar("Modificar");
                modificarEspectaculo .setCbBajaState(false); //
                cs = new ControladorEspectaculo();
                modificarEspectaculo .mostrarEspectaculos(cs.selectAll());
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

        itemSalirParque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    ArrancarPrograma.db.DesconectarDb();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(false);
                JFrame frame = new JFrame("Parques");
                frame.setContentPane(new ArrancarPrograma(frame).getJPGeneral());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });

    }

    /**
     * Esta funcion nos permite reutilizar el frame y solo cambiar la parte inferior donde aparecen las pantallas de cliente
     * empleado, espectaculo....
     */
    public void mostrarPanel(JPanel panel) {

        JPVacio.removeAll();
        JPVacio.add(panel);
        JPVacio.repaint();
        JPVacio.revalidate();

    }

    public JPanel getJPGeneral(JFrame f) {
        return JPGeneral;
    }

    public JPanel getJPVacio() {
        return JPVacio;
    }
}


