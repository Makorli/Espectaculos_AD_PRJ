package Vistas;

import Controllers.ControladorCliente;
import Controllers.ControladorEmpleado;
import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;
import Modelos.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class VentanaInicio {

    private ControladorEspectaculo cs;
    private ControladorEmpleado ce;
    private ControladorCliente cc;
    private ControladorInscripciones ci;
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
        JMenuItem itemListarClientes = new JMenuItem("Listados");

        JMenuItem itemNuevoEmpleado = new JMenuItem("Nuevo");
        JMenuItem itemModificarEmpleado = new JMenuItem("Modificar");
        JMenuItem itemListarEmpleados = new JMenuItem("Listados");

        JMenuItem itemNuevoEspectaculo = new JMenuItem("Nuevo");
        JMenuItem itemModificarEspectaculo = new JMenuItem("Modificar");
        JMenuItem itemListarEspectaculos = new JMenuItem("Listados");

        JMenuItem itemInscribirse = new JMenuItem("Inscripción");
        JMenuItem itemMostrarTodasInscripciones = new JMenuItem("Mostrar inscripciones");

        JMenuItem itemMetadatos = new JMenuItem("Metadatos");
        JMenuItem itemBusqueda = new JMenuItem("Buscar...");
        JMenuItem itemSalirParque = new JMenuItem("Salir de este parque.");

        // Aqui añadimos el item a cada menu.
        MenuEmpleados.add(itemNuevoEmpleado);
        MenuEmpleados.add(itemModificarEmpleado);
        MenuEmpleados.add(itemListarEmpleados);

        MenuClientes.add(itemNuevoCliente);
        MenuClientes.add(itemModificarCliente);
        MenuClientes.add(itemListarClientes);

        MenuEspectaculos.add(itemNuevoEspectaculo);
        MenuEspectaculos.add(itemModificarEspectaculo);
        MenuEspectaculos.add(itemListarEspectaculos);

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

        //Añadir evento del cierre de ventana para controlar el cierre de la conexion de Base de datos.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ArrancarPrograma.db.DesconectarDb();
                System.out.println("Cerrando bases de datos");
            }
        });


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
                modificarCliente.mostrarClientes(cc.selectAll());
                mostrarPanel(modificarCliente.getJPClientes());
            }
        });

        itemListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListadoClientes listadoClientes = new ListadoClientes();
                cc = new ControladorCliente();
                listadoClientes.mostrarClientes(cc.selectAll());

                //tenemos que enviar un listado de clientes, de espectaculos e inscripciones.

                mostrarPanel(listadoClientes.getJPListadoClientes());
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

        itemListarEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListadoEmpleados listadoEmpleados = new ListadoEmpleados();
                ce = new ControladorEmpleado();
                listadoEmpleados.mostrarEmpleados(ce.selectAll());

                //tenemos que enviar un listado de clientes, de espectaculos e inscripciones.

                mostrarPanel(listadoEmpleados.getJPListadoEmpleados());
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
                ce = new ControladorEmpleado();

                nuevoEspectaculo.mostrarEspectaculos(cs.selectAll());
                nuevoEspectaculo.loadCbResponsable(ce.selectAll());

                mostrarPanel(nuevoEspectaculo.getJPEspectaculos());
            }
        });

        itemModificarEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEspectaculo modificarEspectaculo = new DatosEspectaculo();

                modificarEspectaculo.renombrarBtnGuardar("Modificar");
                modificarEspectaculo.setCbBajaState(false); //

                cs = new ControladorEspectaculo();
                ce = new ControladorEmpleado();

                modificarEspectaculo.mostrarEspectaculos(cs.selectAll());
                modificarEspectaculo.loadCbResponsable(ce.selectAll());

                mostrarPanel(modificarEspectaculo.getJPEspectaculos());
            }
        });

        itemListarEspectaculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListadoEspectaculos listadoEspectaculos = new ListadoEspectaculos();
                cs = new ControladorEspectaculo();
                listadoEspectaculos.mostrarEspectaculos(cs.selectAll());

                //tenemos que enviar un listado de clientes, de espectaculos e inscripciones.

                mostrarPanel(listadoEspectaculos.getJPListadoEspectaculo());
            }
        });

        itemInscribirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosInscripciones nuevaInscripcion = new DatosInscripciones();
                cs = new ControladorEspectaculo();
                cc = new ControladorCliente();

                nuevaInscripcion.mostrarClientes( cc.selectAll());
                nuevaInscripcion.mostrarEspectaculos( cs.selectAll());

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

    public void setLbTituloParque(String lbTituloParque) {
        this.lbTituloParque.setText(lbTituloParque);
    }

    public JPanel getJPGeneral() {
        return JPGeneral;
    }

    public JPanel getJPVacio() {
        return JPVacio;
    }
}


