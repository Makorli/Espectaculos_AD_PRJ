import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosEspectaculo {

    //  Esta etiqueta es la que aparecerá en la parte superior de todas las ventanas
    //  para indicarnos el parque en el que nos encontramos.
    private JLabel lbTituloParque;

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPEspectaculos ->    (Con JP en mayusculas) es el panel que muestra la ficha del espectaculo.
     */

    private JPanel JPGeneral, JPMenu, JPEspectaculos, jpClientes, jpEmpleados, jpEspectaculos, jpInscripciones, jpOtrasOpciones;
    private JTabbedPane tpMenu;


    //Etiquetas
    private JLabel lbIdEspectaculo, lbNumero, lbNombre, lbAforo, lbDescripcion, lbLugar, lbCoste, lbFecha, lbHorario, lbBaja, lbResponsable;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //TextField
    private JTextField txtNombre, txtDescripcion, txtLugar;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Spinner para nº en el aforo
    private JSpinner spnAforo;

    //Combobox para elegir al responsable del espectaculo
    private JComboBox cbResponsable;


}
