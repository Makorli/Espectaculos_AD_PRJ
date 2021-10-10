package Vistas;

import Controllers.ControladorEmpleado;
import Controllers.ControladorEspectaculo;
import Modelos.Empleado;
import Modelos.Espectaculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

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
    private ControladorEmpleado ce;

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

                Espectaculo espectaculo = new Espectaculo(1, Integer.parseInt(txtNumero.getText()), txtNombre.getText(),
                        (Integer) spnAforo.getValue() , txtDescripcion.getText(),
                        txtLugar.getText(),Double.parseDouble( txtCoste.getText()), txtFecha.getText(),
                        txtHorario.getText(), cbBaja.isSelected(),
                        2
                        //(Integer) cbResponsable.getSelectedItem() ///////////////////////////////////////Necesito recoger los id de empleados y meterlos en el cb para que sean seleccionables
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
                lbIdEspectaculo.setText(String.format("%d", espectaculo.getIdEspectaculo()));
                //txtNumero.setText(espectaculo.getNumero());
                txtNombre.setText(espectaculo.getNombre());
                cbBaja.setSelected(espectaculo.getBaja());
                ///////////////////////////////////////completar

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

    public void setLstEmpleadosState (boolean state) {
        this.lstEspectaculos.setEnabled(state);    }

    public void setBtnBajaState(boolean state) {
        this.btnBaja.setEnabled(state);
    }

/*
    public Vector listaResponsables (){

        List<Empleado> empleados = ce.selectAll();
        int respCantidad = ce.selectAll().size();

        Vector listaResponsables = new Vector();

        //String [] listaResponsables = new String[respCantidad];

        for (int j=0; j < respCantidad ; j++ ) {

            listaResponsables.addElement( new Empleado (empleados.get(j).getIdEmpleado(), empleados.get(j).getApellidos()));

        } return listaResponsables;

    }

*/


}



