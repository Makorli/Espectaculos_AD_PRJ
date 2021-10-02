import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosClientes {


    //  Esta etiqueta es la que aparecerá en la parte superior de todas las ventanas
    //  para indicarnos el parque en el que nos encontramos.
    private JLabel lbTituloParque;

    //Paneles

    /**
     * @param JPGeneral -> contenedor de todos los paneles
     * @param JPMenu ->   es el panel del menu
     * @param tpMenu -> es un TabbledPanel donde se muestran todas las opciones: Clientes, empleados, Espectaculos...
     * @param jpClientes -> (con jp en minusculas) jpEmpleados... son los despleglables correspondientes.
     * @param JPClientes -> (Con JP en mayusculas) es el panel que muestra la ficha del cliente.
     */

    private JPanel JPGeneral, JPMenu, JPClientes, jpClientes, jpEmpleados, jpEspectaculos, jpInscripciones, jpOtrasOpciones;
    private  JTabbedPane tpMenu;

    private JMenu tpMenu2;

    //Botones
    private JButton btnGuardar, btnCancelar, btnBaja;

    //Checkbox para la baja si/no
    private JCheckBox cbBaja;

    //Etiquetas
    private JLabel lbIdCliente, lbNombre, lbApellidos, lbDni, lbFechaNacimiento, lbBaja;

    //TextField
    private JTextField txtNombre, txtApellidos, txtDni;


    JMenuItem nuevo = new JMenuItem();

    public static  void main(String[] args){



        JFrame frame = new JFrame("Iniciar sesión");



        frame.setContentPane(new DatosClientes().JPGeneral);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


}
