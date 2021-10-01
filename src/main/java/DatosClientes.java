import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosClientes {
    private JPanel JPClientes;
    private JLabel lbIdCliente;
    private JLabel lbNombre;
    private JLabel lbApellidos;
    private JButton btnCancelar;
    private JLabel lbDni;
    private JLabel lbFechaNacimiento;
    private JLabel lbBaja;
    private JCheckBox checkBox1;
    private JButton btnBaja;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JButton btnGuardar;
    private JPanel JPMenu;
    private JLabel lbTituloParque;
    private JTabbedPane tpMenu;
    private JPanel jpClientes;
    private JPanel jpEsmpleados;
    private JPanel jpEspectaculos;
    private JPanel jpInscripciones;
    private JPanel jpOtrasOpciones;
    private JPanel JPGeneral;
    private JTextField txtDni;

    public DatosClientes() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
}
