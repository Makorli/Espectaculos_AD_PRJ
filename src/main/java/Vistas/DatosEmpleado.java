package Vistas;

import Controllers.ControladorEmpleado;
import Modelos.Empleado;

import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
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


    public DatosEmpleado() {

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               /* Empleado empleado = new Empleado(1, txtDni.getText(), txtNombre.getText(),
                        txtApellidos.getText(), txtFechaNacimiento.getText(),
                        txtFechaContratacion.getText(), txtNacionalidad.getText(),
                        Objects.requireNonNull(cbCargo.getSelectedItem()).toString(), cbBaja.isSelected()
                );*/
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

                        //incorpora MJ verificacion
                        /*else {
                            JOptionPane.showMessageDialog(null, "Error al insertar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }*/

                    } else { //modificar --update
                        empleado.setIdEmpleado(Integer.parseInt(lbIdEmpleado.getText()));
                        if (cc.update(empleado)) {
                            JOptionPane.showMessageDialog(null, "Modificacion correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );
                            autoDestroy();
                        }
                        //incorpora MJ verificacion
                        /* else {
                            JOptionPane.showMessageDialog(null, "Error al modificar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }*/
                    }
                } else {
                    String texto = cc.validaciones(empleado);
                    JOptionPane.showMessageDialog(null, "Error - Verifica los datos de entrada", "Resultado", JOptionPane.ERROR_MESSAGE
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

                if (cbBaja.isSelected()){
                    cbBaja.setSelected(false);
                }else{
                    cbBaja.setSelected(true);
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

    public void setLstEmpleadosState (boolean state) {
        this.lstEmpleados.setEnabled(state);    }

    public void setBtnBajaState(boolean state) {
        this.btnBaja.setEnabled(state);
    }
}
