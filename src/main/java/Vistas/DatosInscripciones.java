package Vistas;

import Controllers.ControladorInscripciones;
import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DatosInscripciones {
    private JPanel JPGeneral, JPInscripciones;
    private JLabel lbInscripciones, lbTituloParque;
    private JList<Cliente> lstClientes;
    private JList<Espectaculo> lstEspectaculos;
    private JButton btnInscribir;
    private JTextField txtFechaInscripcion;
    private JLabel lbFechaInscripcion;
    private ControladorInscripciones ci = new ControladorInscripciones();


    public DatosInscripciones() {
        btnInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdCliente(lstClientes.getSelectedValue().getIdCliente());
                inscripcion.setIdEspectaculo(lstEspectaculos.getSelectedValue().getIdEspectaculo());
                inscripcion.setFecha(txtFechaInscripcion.getText());


                if(ci.validaciones(inscripcion) == null) {
                    if(ci.add(inscripcion)){
                        JOptionPane.showMessageDialog(null, "Inserción correcta",
                                "Resultado", JOptionPane.INFORMATION_MESSAGE
                        );
                        autoDestroy();
                    }
                }else{
                    String texto = ci.validaciones(inscripcion);
                    JOptionPane.showMessageDialog(null, texto, "Resultado", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    public JPanel getJPInscripciones() {
        return JPInscripciones;
    }

    /**Reutilizo estas dos funciones de DatosEspectaculo y DatosClientes*/
    public void mostrarEspectaculos(List<Espectaculo> espectaculos) {

        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        for (Espectaculo espectaculo : espectaculos) {
            modelo.addElement(espectaculo);
        }

        lstEspectaculos.setModel(modelo);
    }
    public void mostrarClientes(List<Cliente> clientes) {

        DefaultListModel<Cliente> modelo = new DefaultListModel<>();

        for (Cliente cliente : clientes) {
            modelo.addElement(cliente);
        }

        lstClientes.setModel(modelo);
    }
    public void autoDestroy() {

        JPInscripciones.removeAll();
        JPInscripciones.repaint();
    }
}
