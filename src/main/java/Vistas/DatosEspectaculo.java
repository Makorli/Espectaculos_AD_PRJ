package Vistas;

import Controllers.ControladorEmpleado;
import Controllers.ControladorEspectaculo;
import Modelos.Empleado;
import Modelos.Espectaculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DatosEspectaculo {

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPEspectaculos ->    (Con JP en mayusculas) es el panel que muestra la ficha del espectaculo.
     */

    private ControladorEspectaculo cc = new ControladorEspectaculo();
    private ControladorEmpleado ce = new ControladorEmpleado();

    private JPanel JPGeneral, JPEspectaculos;

    //Etiquetas
    private JLabel lbIdEspectaculo, lbEspectaculo, lbTituloParque, lbNumero, lbNombre, lbAforo, lbDescripcion, lbLugar, lbCoste, lbFecha, lbHorario, lbBaja, lbResponsable;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //TextField
    private JTextField txtNombre, txtDescripcion, txtLugar, txtFecha, txtHorario, txtCoste;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Spinner para nº en el aforo
    private JSpinner spnAforo;

    //Combobox para elegir al responsable del espectaculo
    private JComboBox cbResponsable;
    private JTextField txtNumero;

    private JList<Empleado> lstEmpleados;
    private JList<Espectaculo> lstEspectaculos;
    private JScrollPane JPListaEspectaculos;


    public DatosEspectaculo() {


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Empleado responsable = (Empleado) cbResponsable.getSelectedItem();

                Espectaculo espectaculo = new Espectaculo(1, Integer.parseInt(txtNumero.getText()), txtNombre.getText(),
                        (Integer) spnAforo.getValue(), txtDescripcion.getText(),
                        txtLugar.getText(), Double.parseDouble(txtCoste.getText()), txtFecha.getText(),
                        txtHorario.getText(), cbBaja.isSelected(),
                        responsable.getIdEmpleado()
                );


                if (cc.validaciones(espectaculo).size() == 0) {
                    if (btnGuardar.getText().equalsIgnoreCase("Guardar")) { //guardar

                        espectaculo.setBaja(false);

                        if (cc.add(espectaculo)) {
                            JOptionPane.showMessageDialog(null, "Inserción correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );

                            autoDestroy();

                        } else {
                            JOptionPane.showMessageDialog(null, "Error al insertar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }

                    } else { //modificar --update

                        espectaculo.setIdEspectaculo(Integer.parseInt(lbIdEspectaculo.getText()));

                        if (cc.update(espectaculo)) {
                            JOptionPane.showMessageDialog(null, "Modificacion correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );

                            autoDestroy();

                        } else {
                            JOptionPane.showMessageDialog(null, "Error al modificar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }


                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error - Verifica los datos de entrada", "Resultado", JOptionPane.ERROR_MESSAGE
                    );

                }
            }

        });

        lstEspectaculos.addListSelectionListener(e -> {
            Espectaculo espectaculo = lstEspectaculos.getSelectedValue();

            if (espectaculo != null) {

                Empleado responsable = ce.selectById(espectaculo.getIdResponsable());

                lbIdEspectaculo.setText(String.format("%d", espectaculo.getIdEspectaculo()));
                txtNumero.setText(String.format("%d", espectaculo.getNumero()));
                txtNombre.setText(espectaculo.getNombre());
                spnAforo.setValue(espectaculo.getAforo());
                txtDescripcion.setText(espectaculo.getDescripcion());
                txtLugar.setText(espectaculo.getLugar());
                txtCoste.setText(espectaculo.getCoste().toString());
                txtFecha.setText(espectaculo.getFecha());
                txtHorario.setText(espectaculo.getHorario());
                cbBaja.setSelected(espectaculo.getBaja());

                cbResponsable.setSelectedItem(responsable);

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

                if (cbBaja.isSelected()) {
                    cbBaja.setSelected(false);
                } else {
                    cbBaja.setSelected(true);
                }

            }
        });
    }

    public JPanel getJPEspectaculos() {
        return JPEspectaculos;
    }

    public void mostrarEspectaculos(List<Espectaculo> espectaculos) {

        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        for (Espectaculo espectaculo : espectaculos) {
            modelo.addElement(espectaculo);
        }

        lstEspectaculos.setModel(modelo);
    }

    public void renombrarBtnGuardar(String nombre) {
        this.btnGuardar.setText(nombre);
    }

    public void autoDestroy() {

        JPEspectaculos.removeAll();
        JPEspectaculos.repaint();
    }

    public void setCbBajaState(boolean state) {
        this.cbBaja.setEnabled(state);
    }

    public void setLstEmpleadosState(boolean state) {
        this.lstEspectaculos.setEnabled(state);
    }

    public void setBtnBajaState(boolean state) {
        this.btnBaja.setEnabled(state);
    }

    public void loadCbResponsable(List<Empleado> responsables) {

        DefaultComboBoxModel<Empleado> defaultComboBoxModel = new DefaultComboBoxModel<Empleado>();

        for (Empleado e: responsables){
            defaultComboBoxModel.addElement(e);
        }
        cbResponsable.setModel(defaultComboBoxModel);
        cbResponsable.setRenderer(new responsableListCellRenderer());
    }

    private class responsableListCellRenderer extends DefaultListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Empleado) {
                Empleado e = (Empleado) value;
                value = e.getDni() + " - " + e.getApellidos();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }


}



