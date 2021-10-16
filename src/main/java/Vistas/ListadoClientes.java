package Vistas;

import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;
import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListadoClientes {
    private JLabel lbTituloParque, lbNombre, lbApellido, lbFechaNacimiento, lbDni;
    private JPanel JPGeneral, JPListadoClientes;
    private JList<Cliente> lstClientes;
    private JButton btnVolver;
    private JList<Espectaculo> lstCliEspectaculos;
    private JCheckBox cbHistorico;

    private JScrollPane JPListadoCli;

    private ControladorInscripciones ci = new ControladorInscripciones();
    private ControladorEspectaculo cs = new ControladorEspectaculo();


    public ListadoClientes() {


        lstClientes.addListSelectionListener(e -> {

            lbNombre.setText(lstClientes.getSelectedValue().getNombre());
            lbApellido.setText(lstClientes.getSelectedValue().getApellidos());
            lbFechaNacimiento.setText(lstClientes.getSelectedValue().getFechaNacimiento());
            lbDni.setText(lstClientes.getSelectedValue().getDni());
            cbHistorico.setEnabled(true);


            try {
                mostrarEspectaculosInscritos(lstClientes.getSelectedValue(), cbHistorico.isSelected());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }


        });
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });
        cbHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (lstClientes.getSelectedValue() != null) {
                        try {
                        mostrarEspectaculosInscritos(lstClientes.getSelectedValue(), cbHistorico.isSelected());
                    } catch(ParseException ex){
                        ex.printStackTrace();
                    }
                }
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

    public void mostrarEspectaculosInscritos(Cliente cliente, boolean historico) throws ParseException {
        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        List<Espectaculo> espectaculosClienteList = cliente.getEspectaculosByCliente();

      for (Espectaculo e : espectaculosClienteList){

          Date date = new Date();
          DateFormat fechaHora = new SimpleDateFormat("dd/MM/yyyy");
          String ahora = fechaHora.format(date);

          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          Date date1 = sdf.parse(ahora);
          Date date2 = sdf.parse(e.getFecha());

          if (historico) {

              if (date1.compareTo(date2) < 0) {
                  modelo.addElement(e);
              }
          } else {

              if (date1.compareTo(date2) > 0) {
                  modelo.addElement(e);
              }


          }

      }

        lstCliEspectaculos.setModel(modelo);

    }

    public JCheckBox getCbHistorico() {
        return cbHistorico;
    }



    public void autoDestroy() {

        JPListadoClientes.removeAll();
        JPListadoClientes.repaint();
    }




}

