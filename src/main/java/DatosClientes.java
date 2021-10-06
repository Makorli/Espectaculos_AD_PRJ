
import javax.swing.*;

public class DatosClientes {


    //  Esta etiqueta es la que aparecerá en la parte superior de todas las ventanas
    //  para indicarnos el parque en el que nos encontramos.
    private JLabel lbTituloParque;

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPClientes -> (Con JP en mayusculas) es el panel que muestra la ficha del cliente.
     */

    private JPanel JPGeneral, JPClientes;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Etiquetas
    private JLabel lbIdCliente,lbIdClienteAuto, lbNombre, lbApellidos, lbDni, lbFechaNacimiento, lbBaja, lbCliente;

    //TextField
    private JTextField txtNombre, txtApellidos, txtDni, txtFechaNacimiento;

    public JPanel getJPGeneral(JFrame frame) {
        return JPGeneral;
    }

    public void setJPGeneral(JPanel JPGeneral) {
        this.JPGeneral = JPGeneral;
    }

    public JPanel getJPClientes() {
        return JPClientes;
    }

    public void setJPClientes(JPanel JPClientes) {
        this.JPClientes = JPClientes;
    }
}
