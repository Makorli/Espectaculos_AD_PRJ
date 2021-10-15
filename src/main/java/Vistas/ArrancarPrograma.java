package Vistas;

import Controllers.DBController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ArrancarPrograma {

    private JLabel lbEleccionParque;
    private JPanel JPVentanaInicio;
    private JPanel JPGeneral;
    // Botones para entrar en un parque.
    private JButton btnParque1, btnParque2, btnParque3;

    //  public static Connection conexion;
    //  public static ObjectContainer contenedor;

    public static DBController db;

    /**
     * En el constructor de ArrancarPrograma estoy pasando por parametro el JFrame que me abre al arrancar
     * Cuando pulse uno de los 3 botones se tiene que ocultar.
     * */

    public ArrancarPrograma(JFrame frame) {

        btnParque1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /**
                 * Al seleccionar el parque 1 tengo que abrir la ventana de inicio
                 * Tengo que enviar la conexion seleccionada.
                 * Aqui debo ocultar el frame anterior - el de VentanaInicio.
                 * */

                /**
                 * Estas lineas desde la creacion del frame hasta  frame.setVisible(false);
                 * Se podría crear un método para los 3 / 4 botones, segun las bases de datos que necesitemos.
                 * **/

                ////////////////////////
                db = new DBController(DBController.DBTypes.MySQL);

                JFrame f = new JFrame("Parque MySql ");

                ////////////////////////////////////////

                VentanaInicio vi = new VentanaInicio(f);
                /** Recordar que aquí (Al crear la ventana de inicio) debemos añadir la conexion que necesitemos.
                * Y deberá enviarse constantemente -- quizas se pueda poner una constante que se modifique en
                 * el momento que hemos elegido el parque
                **/
                vi.setLbTituloParque("jasdklfjalsdfs");
                f.setContentPane(vi.getJPGeneral());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
                frame.setVisible(false);

            }
        });


        btnParque2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Al seleccionar el parque 2 tengo que abrir la ventana de inicio
                 * Tengo que enviar la conexion seleccionada.
                 * */
                ////////////////////////
                db = new DBController(DBController.DBTypes.SQLite);

                JFrame f = new JFrame("Parque SQLite ");

                VentanaInicio vi = new VentanaInicio(f);
                /** Recordar que aquí (Al crear la ventana de inicio) debemos añadir la conexion que necesitemos.
                 * Y deberá enviarse constantemente -- quizas se pueda poner una constante que se modifique en
                 * el momento que hemos elegido el parque
                 **/

               vi.setLbTituloParque("jasdklfjalsdfs");

                f.setContentPane(vi.getJPGeneral());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
                frame.setVisible(false);


            }
        });


        btnParque3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Al seleccionar el parque 3 tengo que abrir la ventana de inicio
                 * Tengo que enviar la conexion seleccionada.
                 * */

                ////////////////////////
                db = new DBController(DBController.DBTypes.DB4o);

                JFrame f = new JFrame("Parque DB4O ");

                VentanaInicio vi = new VentanaInicio(f);
                /** Recordar que aquí (Al crear la ventana de inicio) debemos añadir la conexion que necesitemos.
                 * Y deberá enviarse constantemente -- quizas se pueda poner una constante que se modifique en
                 * el momento que hemos elegido el parque
                 **/
                //f.setContentPane(vi.getJPGeneral(f));
                vi.setLbTituloParque("jasdklfjalsdfs");
                f.setContentPane(vi.getJPGeneral());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
                frame.setVisible(false);

            }
        });

    }

    public JPanel getJPGeneral() {
        return JPGeneral;
    }

    public void setJPGeneral(JPanel JPGeneral) {
        this.JPGeneral = JPGeneral;
    }

    public static void main(String[] args) {

        /**
         * Arrancamos la aplicacion desde este punto.
         * */

        JFrame frame = new JFrame("Parques");

        /*Añadimos un listener al frame principal para que cierre la conexion de
        * la base de datos que esté siendo usada.
        */
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                db.DesconectarDb();
                System.out.println("Base de datos desconectada");
            }
        });

        //desde el frame estoy arrancado el menu para elegir la base de datos = parque.
        frame.setContentPane(new ArrancarPrograma(frame).JPGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
