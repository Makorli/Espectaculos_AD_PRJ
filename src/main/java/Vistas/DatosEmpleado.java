package Vistas;

import Controllers.ControladorEmpleado;
import Modelos.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class DatosEmpleado {

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPEmpleados -> (Con JP en mayusculas) es el panel que muestra la ficha del empleado.
     */

    private ControladorEmpleado cc = new ControladorEmpleado();

    private JPanel JPGeneral, JPEmpleado, JPVacio;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Etiquetas
    private JLabel lbIdEmpleado, lbEmpleado, lbNombre, lbApellidos, lbDni, lbFechaNacimiento, lbBaja, lbFechaContratacion, lbTituloParque, lbCargo, lbNacionalidad;

    //TextField
    private JTextField txtNombre, txtApellidos, txtDni, txtNacionalidad, txtFechaNacimiento, txtFechaContratacion;

    //ComboBox para mostrar los posibles cargos - Jefe, auxiliar, tecnico....
    private JComboBox cbCargo;
    private JList<Empleado> lstEmpleados;
    private JScrollPane JPListaEmpleado;
    private JCheckBox cbHistoricoEmple;
    private JLabel lbIDEmpleadoEtiqueta;


    public DatosEmpleado() {

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Empleado empleado = new Empleado();
                empleado.setDni(txtDni.getText());
                empleado.setNombre(txtNombre.getText());
                empleado.setApellidos(txtApellidos.getText());
                empleado.setFechaNacimiento(txtFechaNacimiento.getText());
                empleado.setFechaContratacion(txtFechaContratacion.getText());
                empleado.setNacionalidad(txtNacionalidad.getText());
                empleado.setCargo(Objects.requireNonNull(cbCargo.getSelectedItem()).toString());
                empleado.setBaja(cbBaja.isSelected());

                if (cc.validaciones(empleado) == null) {
                    if (btnGuardar.getText().equalsIgnoreCase("Guardar")) { //guardar
                        empleado.setBaja(false);
                        if (cc.add(empleado)) {
                            JOptionPane.showMessageDialog(null, "Inserción correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );
                            autoDestroy();
                        }


                    } else { //modificar --update
                        empleado.setIdEmpleado(Integer.parseInt(lbIdEmpleado.getText()));
                        if (cc.update(empleado)) {
                            JOptionPane.showMessageDialog(null, "Modificacion correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );
                            autoDestroy();
                        }

                    }
                } else {
                    String texto = cc.validaciones(empleado);
                    JOptionPane.showMessageDialog(null, texto, "Resultado", JOptionPane.ERROR_MESSAGE
                    );

                }
            }

        });

        lstEmpleados.addListSelectionListener(e -> {
            Empleado empleado = lstEmpleados.getSelectedValue();

            if (empleado != null) {

                lbIdEmpleado.setText(String.format("%d", empleado.getIdEmpleado()));

                txtNombre.setText(empleado.getNombre());
                txtApellidos.setText(empleado.getApellidos());
                txtNacionalidad.setText(empleado.getNacionalidad());
                txtDni.setText(empleado.getDni());
                txtFechaNacimiento.setText(empleado.getFechaNacimiento());
                txtFechaContratacion.setText(empleado.getFechaContratacion());
                cbCargo.setSelectedItem(empleado.getCargo());
                cbBaja.setSelected(empleado.getBaja());

            }

        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });

        btnBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if ((lstEmpleados.getSelectedValue() == null)) {
                    cbBaja.setSelected(false);
                    cbBaja.setEnabled(false);
                    btnGuardar.setEnabled(false);

                } else {
                    if (cbBaja.isSelected()) {
                        cbBaja.setSelected(false);
                        if (btnBaja.getText() == "Alta") {
                            btnGuardar.setEnabled(true);
                        }

                    } else {
                        cbBaja.setSelected(true);
                        if (btnBaja.getText() == "Alta") {
                            btnGuardar.setEnabled(false);
                        }
                    }

                }
            }
        });

        cbHistoricoEmple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean state = cbHistoricoEmple.isSelected();

                if (state) {
                    limpiar();
                    mostrarEmpleados(cc.selectByState(true));
                    btnGuardar.setEnabled(false);
                    btnBaja.setText("Alta");

                } else {
                    limpiar();
                    mostrarEmpleados(cc.selectByState(false));
                    btnGuardar.setEnabled(true);
                    btnBaja.setText("Baja");

                }
            }
        });


    }

    public JPanel getJPGeneral(JFrame frame) {
        return JPGeneral;
    }

    public JPanel getJPEmpleados() {

        return JPEmpleado;

    }

    public void mostrarEmpleados(List<Empleado> empleados) {

        DefaultListModel<Empleado> modelo = new DefaultListModel<>();

        for (Empleado empleado : empleados) {
            modelo.addElement(empleado);
        }

        lstEmpleados.setModel(modelo);
    }

    public void renombrarBtnGuardar(String nombre) {
        this.btnGuardar.setText(nombre);
    }

    public void autoDestroy() {

        JPEmpleado.removeAll();
        JPEmpleado.repaint();
    }

    public void setCbBajaState(boolean state) {
        this.cbBaja.setEnabled(state);
    }

    public void setLstEmpleadosState(boolean state) {
        this.lstEmpleados.setEnabled(state);
    }

    public void setBtnBajaState(boolean state) {
        this.btnBaja.setEnabled(state);
    }

    public JCheckBox getCbHistoricoEmple() {
        return cbHistoricoEmple;
    }

    public JList<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void limpiar() {

        lbIdEmpleado.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtFechaNacimiento.setText("");
        txtFechaContratacion.setText("");
        txtNacionalidad.setText("");
        cbBaja.setSelected(false);


    }


}
