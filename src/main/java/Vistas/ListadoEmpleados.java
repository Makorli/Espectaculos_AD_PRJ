package Vistas;

import Modelos.Cliente;
import Modelos.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListadoEmpleados {
    private JPanel JPGeneral, JPListadoEmpleados;
    private JLabel lbTituloParque;
    private JList<Empleado> lstEmpleados;
    private JButton btnVolver;
    private JPanel JPEmpleado;
    private JLabel lbIdEmpleado;
    private JLabel lbNombre;
    private JLabel lbApellidos;
    private JLabel lbNacionalidad;
    private JLabel lbDni;
    private JLabel lbFechaNacimiento;
    private JLabel lbFechaContratacion;
    private JLabel lbCargo;
    private JLabel lbBaja;
    private JCheckBox cbBaja;
    private JComboBox cbCargo;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtNacionalidad;
    private JTextField txtDni;
    private JTextField txtFechaNacimiento;
    private JTextField txtFechaContratacion;
    private JLabel lbEmpleado;
    private DatosEmpleado datosEmpleado ;

    public ListadoEmpleados() {

        lstEmpleados.addListSelectionListener(e -> {

            lbNombre.setText(lstEmpleados.getSelectedValue().getNombre());
            lbApellido.setText(lstClientes.getSelectedValue().getApellidos());
            lbFechaNacimiento.setText(lstClientes.getSelectedValue().getFechaNacimiento());
            lbDni.setText(lstClientes.getSelectedValue().getDni());

            mostrarEmpleado(lstEmpleados.getSelectedValue().getIdEmpleado());


        });


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });

    }

    private void mostrarEmpleado(int idEmpleado) {

        JPEmpleado = datosEmpleado.getJPEmpleados();

    }


    public void mostrarEmpleados(List<Empleado> empleados) {

        DefaultListModel<Empleado> modelo = new DefaultListModel<>();

        for (Empleado  empleado : empleados) {
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
}
