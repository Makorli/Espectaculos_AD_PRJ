package Vistas;

import Controllers.ControladorEmpleado;
import Controllers.ControladorEspectaculo;
import Modelos.Empleado;
import Modelos.Espectaculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListadoEspectaculos {
    private JPanel JPGeneral, JPListadoEspectaculo;
    private JLabel lbTituloParque, lbEspectaculo, lbIdEspectaculo, lbNombre, lbDescripcion, lbLugar, lbNumero, lbAforo, lbFecha, lbHorario,
            lbCoste, lbResponsable, lbBaja;
    private JCheckBox cbBaja;
    private JComboBox cbResponsable;
    private JTextField txtNombre, txtDescripcion, txtNumero, txtLugar, txtFecha, txtHorario, txtCoste, txtAforo;

    private JButton btnVolver;

    private JScrollPane JPListaEspectaculos;
    private JList<Espectaculo> lstEspectaculos;
    private JCheckBox cbVerEspectBajas;

    private ControladorEmpleado ce = new ControladorEmpleado();
    private ControladorEspectaculo cs = new ControladorEspectaculo();


    public ListadoEspectaculos() {

        lstEspectaculos.addListSelectionListener(e -> {
            Espectaculo espectaculo = lstEspectaculos.getSelectedValue();

            if (espectaculo != null) {

                Empleado responsable = ce.selectById(espectaculo.getIdResponsable());


                txtNumero.setText(String.format("%d", espectaculo.getNumero()));
                txtNombre.setText(espectaculo.getNombre());
                txtAforo.setText(String.format("%d", espectaculo.getAforo()));
                txtDescripcion.setText(espectaculo.getDescripcion());
                txtLugar.setText(espectaculo.getLugar());
                txtCoste.setText(espectaculo.getCoste().toString());
                txtFecha.setText(espectaculo.getFecha());
                txtHorario.setText(espectaculo.getHorario());
                cbBaja.setSelected(espectaculo.getBaja());

                cbResponsable.setSelectedItem(responsable);

            }

        });

        cbBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescar();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });

        cbVerEspectBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean state = cbVerEspectBajas.isSelected();
                try {
                    if (state) {
                        limpiar();
                        mostrarEspectaculos(cs.selectByState(true));

                    } else {
                        limpiar();
                        mostrarEspectaculos(cs.selectByState(false));

                    }
                } catch (NullPointerException ex) {

                }
            }
        });
    }


    public void mostrarEspectaculos(List<Espectaculo> espectaculos) {

        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        for (Espectaculo empleado : espectaculos) {
            modelo.addElement(empleado);
        }

        lstEspectaculos.setModel(modelo);
    }

    public void mostrarEspectaculo(int idEspectaculo) {

        DefaultListModel<Espectaculo> modelo = new DefaultListModel<>();

        List<Espectaculo> espectaculos;
        espectaculos = cs.selectAll();

        for (Espectaculo e : espectaculos) {
            if (e.getIdEspectaculo() == idEspectaculo) {
                modelo.addElement(e);
            }
        }


        lstEspectaculos.setModel(modelo);


    }

    public JPanel getJPListadoEspectaculo() {
        return JPListadoEspectaculo;
    }

    public void autoDestroy() {

        JPListadoEspectaculo.removeAll();
        JPListadoEspectaculo.repaint();
    }

    public void refrescar() {
        JPListadoEspectaculo.repaint();
    }

    public void limpiar() {


        txtNumero.setText("");
        txtNombre.setText("");
        txtAforo.setText("");
        txtDescripcion.setText("");
        txtLugar.setText("");
        txtCoste.setText("");
        txtFecha.setText("");
        txtHorario.setText("");
        cbBaja.setSelected(false);
        cbResponsable.setSelectedItem(null);


    }


    public void loadCbResponsable(List<Empleado> responsables) {

        DefaultComboBoxModel<Empleado> defaultComboBoxModel = new DefaultComboBoxModel<Empleado>();

        for (Empleado e : responsables) {
            defaultComboBoxModel.addElement(e);
        }
        cbResponsable.setModel(defaultComboBoxModel);
        cbResponsable.setRenderer(new responsableListCellRenderer());
    }

    private class responsableListCellRenderer extends DefaultListCellRenderer {

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
