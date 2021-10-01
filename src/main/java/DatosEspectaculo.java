import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosEspectaculo {
    private JPanel JPGeneral;
    private JLabel lbIdEspectaculo;
    private JLabel lbNumero;
    private JLabel lbNombre;
    private JLabel lbAforo;
    private JLabel lbDescripcion;
    private JLabel lbLugar;
    private JLabel lbCoste;
    private JLabel lbFecha;
    private JLabel lbHorario;
    private JLabel lbBaja;
    private JLabel lbResponsable;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtLugar;
    private JCheckBox checkBox1;
    private JButton btnBaja;
    private JSpinner spnAforo;
    private JComboBox comboBox1;
    private JPanel JPEspectaculos;
    private JPanel JPMenu;
    private JLabel lbTituloParque;
    private JTabbedPane tpMenu;
    private JPanel jpClientes;
    private JPanel jpEsmpleados;
    private JPanel jpEspectaculos;
    private JPanel jpInscripciones;
    private JPanel jpOtrasOpciones;

    public DatosEspectaculo() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
