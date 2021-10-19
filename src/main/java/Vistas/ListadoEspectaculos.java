package Vistas;

import Controllers.ControladorEspectaculo;
import Modelos.Espectaculo;

import javax.swing.*;
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


    private ControladorEspectaculo cs = new ControladorEspectaculo();


    public ListadoEspectaculos() {

        lstEspectaculos.addListSelectionListener(e -> {

            txtAforo.setText(String.valueOf(lstEspectaculos.getSelectedValue().getAforo()));
            txtNombre.setText(lstEspectaculos.getSelectedValue().getNombre());
            txtDescripcion.setText(lstEspectaculos.getSelectedValue().getDescripcion());
            txtNumero.setText(String.valueOf(lstEspectaculos.getSelectedValue().getNumero()));
            txtLugar.setText(lstEspectaculos.getSelectedValue().getLugar());
            txtFecha.setText(lstEspectaculos.getSelectedValue().getFecha());
            txtHorario.setText(lstEspectaculos.getSelectedValue().getHorario());
            txtCoste.setText(Double.toString(lstEspectaculos.getSelectedValue().getCoste()));


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

                if (state) {
                    limpiar();
                    mostrarEspectaculos(cs.selectByState(true));

                } else {
                    limpiar();
                    mostrarEspectaculos(cs.selectByState(false));

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

}
