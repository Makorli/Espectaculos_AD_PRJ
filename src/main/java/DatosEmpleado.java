import javax.swing.*;

public class DatosEmpleado {


    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPEmpleados -> (Con JP en mayusculas) es el panel que muestra la ficha del empleado.
     */

    private JPanel JPGeneral, JPEmpleados;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Etiquetas
    private JLabel lbIdEmpleado, lbEmpleado, lbNombre, lbApellidos, lbDni, lbFechaNacimiento, lbBaja, lbFechaContratacion,lbTituloParque, lbCargo, lbNacionalidad;

    //TextField
    private JTextField txtNombre, txtApellidos, txtDni, txtNacionalidad, txtFechaNacimiento, txtFechaContratacion;

    //ComboBox para mostrar los posibles cargos - Jefe, auxiliar, tecnico....
    private JComboBox cbCargo;

    public JPanel getJPGeneral(JFrame frame) {
        return JPGeneral;
    }

    public void setJPGeneral(JPanel JPGeneral) {
        this.JPGeneral = JPGeneral;
    }

    public JPanel getJPEmpleados() {
        return JPEmpleados;
    }

    public void setJPEmpleados(JPanel JPEmpleados) {
        this.JPEmpleados = JPEmpleados;
    }
}
