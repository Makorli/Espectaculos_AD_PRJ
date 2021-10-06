import javax.swing.*;
public class DatosEspectaculo {

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPEspectaculos ->    (Con JP en mayusculas) es el panel que muestra la ficha del espectaculo.
     */

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

    public JPanel getJPEspectaculos() {
        return JPEspectaculos;
    }
}
