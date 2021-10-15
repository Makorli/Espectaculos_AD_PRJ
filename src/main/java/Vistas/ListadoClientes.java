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
import java.util.Date;
import java.util.List;

public class ListadoClientes {
    private JLabel lbTituloParque,lbNombre,lbApellido, lbFechaNacimiento, lbDni;
    private JPanel JPGeneral, JPListadoClientes;
    private JList<Cliente> lstClientes;
    private JButton btnVolver;
    private JList<Espectaculo> lstCliEspectaculos;
    private JCheckBox cbHistorico;
    private ControladorInscripciones ci =new ControladorInscripciones();
    private ControladorEspectaculo cs=new ControladorEspectaculo();


    public ListadoClientes() {

        lstClientes.addListSelectionListener(e -> {

            lbNombre.setText(lstClientes.getSelectedValue().getNombre());
            lbApellido.setText(lstClientes.getSelectedValue().getApellidos());
            lbFechaNacimiento.setText(lstClientes.getSelectedValue().getFechaNacimiento());
            lbDni.setText(lstClientes.getSelectedValue().getDni());

            try {
                mostrarEspectaculosInscritos(lstClientes.getSelectedValue().getIdCliente(), cbHistorico.isSelected());
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

    public void mostrarEspectaculosInscritos(int idCliente, boolean historico  ) throws ParseException {
        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        List<Espectaculo> espectaculos;
        List<Inscripcion> inscripciones;

        espectaculos = cs.selectAll();
        inscripciones = ci.selectAll();

          for (Inscripcion i: inscripciones){
           if(i.getIdCliente() == idCliente){
               for(Espectaculo e: espectaculos){

                   Date date = new Date();
                   DateFormat fechaHora = new SimpleDateFormat("dd/MM/yyyy");
                   String ahora = fechaHora.format(date);

                   System.out.println(ahora);
                   System.out.println(e.getFecha());

                   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                   Date date1 = sdf.parse(ahora);
                   Date date2 = sdf.parse(e.getFecha());

                   System.out.println("date1 : " + sdf.format(date1));
                   System.out.println("date2 : " + sdf.format(date2));

                       if(historico){
                       if(date1.compareTo(date2) <0 ){
                           modelo.addElement(e);
                       }
                   }else{
                       if(date1.compareTo(date2) >0 ){
                           modelo.addElement(e);
                       }
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
