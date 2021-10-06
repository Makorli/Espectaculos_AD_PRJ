import javax.swing.*;

public class MostrarMetadatos {
    private JPanel JPGeneral;
    private JLabel lbUrl, lbUsuario, lbDriver, lbBD, lbMetadatos, lbTituloParque;
    private JTextField txtUsuario, txtDriver, txtBD, txtUrl;
    private JPanel JPMetadatos;



        /*System.out.println("INFORMACIN SOBRE LA BASE DE DATOS:"+ nombre);
  	System.out.println("Driver : " + driver );
  	System.out.println("URL    : " + url );
  	System.out.println("Usuario: " + usuario );
    String nombCol=columnas.getString(“COLUMN_NAME”);*/

    /**
     * Datos a obtener - METADATOS
     * Nombre de la base de datos
     * Driver
     * URL
     * Usuario
     * Tablas y columnas
     * */

    public JPanel getJPMetadatos() {
        return JPMetadatos;
    }
}
