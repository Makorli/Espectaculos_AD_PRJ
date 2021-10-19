package Vistas;

import Controllers.ControladorEspectaculo;
import Modelos.Empleado;
import Modelos.Espectaculo;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class ListadoEmpleados {
    private JPanel JPGeneral, JPListadoEmpleados;
    private JList<Empleado> lstEmpleados;
    private JButton btnVolver;
    private JPanel JPEmpleado;
    private JLabel lbTituloParque, lbEmpleado, lbNombre, lbApellidos, lbNacionalidad, lbDni, lbFechaNacimiento, lbFechaContratacion, lbCargo;
    private JTextField txtCargo, txtNombre, txtApellidos, txtNacionalidad, txtFechaNacimiento, txtDni, txtFechaContratacion;
    private JList lstResponsableEspectaculos;
    private ControladorEspectaculo cs=new ControladorEspectaculo();
    private JCheckBox cbHistorico;
    private JScrollPane JScListadoEmpleados;

    public ListadoEmpleados() {

        lstEmpleados.addListSelectionListener(e -> {


            txtNombre.setText(lstEmpleados.getSelectedValue().getNombre());
            txtApellidos.setText(lstEmpleados.getSelectedValue().getApellidos());
            txtFechaNacimiento.setText(lstEmpleados.getSelectedValue().getFechaNacimiento());
            txtDni.setText(lstEmpleados.getSelectedValue().getDni());
            txtCargo.setText(lstEmpleados.getSelectedValue().getCargo());
            txtNacionalidad.setText(lstEmpleados.getSelectedValue().getNacionalidad());
            txtFechaContratacion.setText(lstEmpleados.getSelectedValue().getFechaContratacion());

            mostrarEspectaculosResponsable(lstEmpleados.getSelectedValue().getIdEmpleado());


        });

        cbHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //todo

            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });

    }

    public void mostrarEspectaculosResponsable(int idEmpleado){

        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        List<Espectaculo> espectaculos;
        espectaculos = cs.selectAll();

                for(Espectaculo e: espectaculos){
                    if(e.getIdResponsable() == idEmpleado){
                        modelo.addElement(e);
                    }
                }

        lstResponsableEspectaculos.setModel(modelo);


    }
    public void mostrarEmpleados(List<Empleado> empleados) {

        DefaultListModel<Empleado> modelo = new DefaultListModel<>();

        for (Empleado empleado : empleados) {
            modelo.addElement(empleado);
        }

        lstEmpleados.setModel(modelo);
    }

    public JPanel getJPListadoEmpleados() {
        return JPListadoEmpleados;
    }

    public void autoDestroy() {

        JPListadoEmpleados.removeAll();
        JPListadoEmpleados.repaint();
    }

    public JCheckBox getCbHistorico() {
        return cbHistorico;
    }
}
