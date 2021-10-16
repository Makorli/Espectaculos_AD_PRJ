package Vistas;

import Controllers.ControladorCliente;
import Modelos.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DatosClientes {


    //  Esta etiqueta es la que aparecerá en la parte superior de todas las ventanas
    //  para indicarnos el parque en el que nos encontramos.

    private JLabel lbTituloParque;

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPClientes -> (Con JP en mayusculas) es el panel que muestra la ficha del cliente.
     */

    private ControladorCliente cc = new ControladorCliente();


    private JPanel JPGeneral, JPClientes;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Etiquetas
    private JLabel lbIdCliente, lbNombre, lbApellidos, lbDni, lbFechaNacimiento, lbBaja, lbCliente;


    //TextField
    private JTextField txtNombre, txtApellidos, txtDni, txtFechaNacimiento;

    public JPanel getJPGeneral(JFrame frame) {
        return JPGeneral;
    }

    private JList<Cliente> lstClientes;
    private JScrollPane JPListaCliente;
    private JCheckBox cbHistorico;


    public void setJPGeneral(JPanel JPGeneral) {
        this.JPGeneral = JPGeneral;
    }

    public JPanel getJPClientes() {
        return JPClientes;
    }

    public void setJPClientes(JPanel JPClientes) {
        this.JPClientes = JPClientes;
    }


    public DatosClientes() {



        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Cliente cliente = new Cliente();
                cliente.setNombre(txtNombre.getText());
                cliente.setApellidos(txtApellidos.getText());
                cliente.setDni(txtDni.getText());
                cliente.setFechaNacimiento(txtFechaNacimiento.getText());
                cliente.setBaja(cbBaja.isSelected());


                if (cc.validaciones(cliente) == null) {
                    if (btnGuardar.getText().equalsIgnoreCase("Guardar")) { //guardar

                        cliente.setBaja(false);

                        if (cc.add(cliente)) {
                            JOptionPane.showMessageDialog(null, "Inserción correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );

                            autoDestroy();

                        }
                        /*else {
                            JOptionPane.showMessageDialog(null, "Error al insertar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }*/

                    } else { //modificar --update

                        cliente.setIdCliente(Integer.parseInt(lbIdCliente.getText()));

                        if (cc.update(cliente)) {
                            JOptionPane.showMessageDialog(null, "Modificacion correcta",
                                    "Resultado", JOptionPane.INFORMATION_MESSAGE
                            );

                            autoDestroy();

                        }/* else {
                            JOptionPane.showMessageDialog(null, "Error al modificar", "Resultado", JOptionPane.ERROR_MESSAGE
                            );
                        }*/


                    }
                } else {

                    //En este string guardamos todos los errores, y lo mostramos.
                    String texto = cc.validaciones(cliente);

                    JOptionPane.showMessageDialog(null, texto, "Resultado", JOptionPane.ERROR_MESSAGE
                    );

                }
            }

        });

        lstClientes.addListSelectionListener(e -> {
            Cliente cliente = lstClientes.getSelectedValue();


            if (cliente != null) {

                lbIdCliente.setText(String.format("%d", cliente.getIdCliente()));

                txtNombre.setText(cliente.getNombre());
                txtApellidos.setText(cliente.getApellidos());
                txtDni.setText(cliente.getDni());
                txtFechaNacimiento.setText(cliente.getFechaNacimiento());
                cbBaja.setSelected(cliente.getBaja());

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
        });

        cbHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean state = cbHistorico.isSelected();

                if (state) {
                    limpiar();
                    mostrarClientes(cc.selectByState(true));
                    btnGuardar.setEnabled(false);
                    btnBaja.setText("Alta");

                } else {
                    limpiar();
                    mostrarClientes(cc.selectByState(false));
                    btnGuardar.setEnabled(true);
                    btnBaja.setText("Baja");

                }
            }
        });


    }

    public void mostrarClientes(List<Cliente> clientes) {


        DefaultListModel<Cliente> modelo = new DefaultListModel<>();


        for (Cliente cliente : clientes) {
            modelo.addElement(cliente);
        }

        lstClientes.setModel(modelo);
    }


    public void renombrarBtnGuardar(String nombre) {
        this.btnGuardar.setText(nombre);
    }

    public void autoDestroy() {

        JPClientes.removeAll();
        JPClientes.repaint();
    }

    public void setCbBajaState(boolean state) {
        this.cbBaja.setEnabled(state);
    }

    public void setLstEmpleadosState(boolean state) {
        this.lstClientes.setEnabled(state);
    }

    public void setBtnBajaState(boolean state) {
        this.btnBaja.setEnabled(state);
    }

    public JCheckBox getCbBaja() {
        return cbBaja;
    }

    public JCheckBox getCbHistorico() {
        return cbHistorico;
    }

    public JList<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void limpiar(){

        lbIdCliente.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtFechaNacimiento.setText("");

    }



}
