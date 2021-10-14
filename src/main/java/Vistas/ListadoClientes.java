package Vistas;

import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;
import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListadoClientes {
    private JLabel lbTituloParque,lbNombre,lbApellido, lbFechaNacimiento, lbDni;
    private JPanel JPGeneral, JPListadoClientes;
    private JList<Cliente> lstClientes;
    private JButton btnVolver;
    private JList<Espectaculo> lstCliEspectaculos;
    private ControladorInscripciones ci =new ControladorInscripciones();
    private ControladorEspectaculo cs=new ControladorEspectaculo();


    public ListadoClientes() {

        lstClientes.addListSelectionListener(e -> {

            lbNombre.setText(lstClientes.getSelectedValue().getNombre());
            lbApellido.setText(lstClientes.getSelectedValue().getApellidos());
            lbFechaNacimiento.setText(lstClientes.getSelectedValue().getFechaNacimiento());
            lbDni.setText(lstClientes.getSelectedValue().getDni());

            mostrarEspectaculosInscritos(lstClientes.getSelectedValue().getIdCliente());


        });


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });
    }


    public JPanel getJPListadoClientes() {
        return JPListadoClientes;
    }

    public void mostrarClientes(List<Cliente> clientes) {

        DefaultListModel<Cliente> modelo = new DefaultListModel<>();

        for (Cliente cliente : clientes) {
            modelo.addElement(cliente);
        }

        lstClientes.setModel(modelo);
    }

    public void mostrarEspectaculosInscritos(int idCliente){
        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        List<Espectaculo> espectaculos;
        List<Inscripcion> inscripciones;

        espectaculos = cs.selectAll();
        inscripciones = ci.selectAll();

        for (Inscripcion i: inscripciones){
           if(i.getIdCliente() == idCliente){
               for(Espectaculo e: espectaculos){
                   if(i.getIdEspectaculo() == e.getIdEspectaculo()){
                       modelo.addElement(e);
                   }
               }
           }
        }

        lstCliEspectaculos.setModel(modelo);

    }

    public void autoDestroy() {

        JPListadoClientes.removeAll();
        JPListadoClientes.repaint();
    }
}
