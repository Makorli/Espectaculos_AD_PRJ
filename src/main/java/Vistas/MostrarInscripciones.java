package Vistas;

import Controllers.ControladorCliente;
import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;
import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MostrarInscripciones {
    private JPanel JPGeneral, JPTodasInscripciones;
    private JLabel lbTituloParque, lbInscripcion;

    private JButton btnVolver;
    private JTable tbInscripciones;
    private JScrollPane JSTablaInscripciones;
    private List<Espectaculo> Espectaculos;
    private List<Cliente> Clientes;
    private List<Inscripcion> Inscripciones;

    private ControladorInscripciones ci = new ControladorInscripciones();
    private ControladorCliente cc = new ControladorCliente();
    private ControladorEspectaculo ce = new ControladorEspectaculo();

    public MostrarInscripciones() {
// implementar una tabla con todos los datos

        tbInscripciones = new JTable();
        tbInscripciones.setModel(new TablaInscripciones());
        JSTablaInscripciones.setViewportView(tbInscripciones);
        tbInscripciones.setModel(new TablaInscripciones(ci.selectAll(), cc.selectAll(), ce.selectAll()));


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });
    }
    public JPanel getJPTodasInscripciones() {
        return JPTodasInscripciones;
    }




    public void autoDestroy() {

        JPTodasInscripciones.removeAll();
        JPTodasInscripciones.repaint();
    }
}
