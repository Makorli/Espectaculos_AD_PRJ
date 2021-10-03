package Controllers;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private int tipoDb;
    private Connection conexionDb;
    private String myDriver;
    private String myDb;
    private String myUser = null;
    private String myPass = null;
    private String myDbTxt;

    public Conexion(int tipoDb) {
        this.tipoDb = tipoDb;
    }

    public Connection ConectarDb() {

        switch (this.tipoDb) {
            case 0: //Mysql

                myDriver = "com.mysql.cj.jdbc.Driver";
                myDb = "jdbc:mysql://localhost:3306/bdmysql";
                myUser = "admin";
                myPass = "pass1234";
                myDbTxt = "MySQL";

                //Cargaremos el driver JDBC de acceso a MySQL/SqLite/DB4O
                try {
                    Class.forName(myDriver);
                } catch (ClassNotFoundException error) {
                    System.out.println("Aviso/Error en la carga del driver JDBC de " + myDbTxt + ": " + error.getMessage());
                }

                //Realizamos la conexión con el servidor MySQL/SqLite/DB4O
                try {
                    conexionDb = DriverManager.getConnection(
                            myDb,
                            myUser, myPass);
                } catch (SQLException error) {
                    System.out.println("Error al conectar con  " + myDbTxt + ": " + error.getMessage());
                }

                break;
            case 1: //sqLite

                myDriver = "org.sqlite.JDBC";
                myDb = "jdbc:sqlite:bdSqlite.db";
                myDbTxt = "SQLite";

                //Cargaremos el driver JDBC de acceso a MySQL/SqLite/DB4O
                try {
                    Class.forName(myDriver);
                } catch (ClassNotFoundException error) {
                    System.out.println("Aviso/Error en la carga del driver JDBC de " + myDbTxt + ": " + error.getMessage());
                }

                //Realizamos la conexión con el servidor MySQL/SqLite/DB4O
                try {
                    conexionDb = DriverManager.getConnection(
                            myDb,
                            myUser, myPass);
                } catch (SQLException error) {
                    System.out.println("Error al conectar con  " + myDbTxt + ": " + error.getMessage());
                }

                break;
            case 2:

                myDb = "bdDb4o.YAP";
                myDbTxt = "Db4O";

                try {
                    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), myDb);
                } catch (Exception error) {
                    System.out.println("Error al conectar con  " + myDbTxt + ": " + error.getMessage());
                }

                break;
            default:
                System.out.println("Opción de conexión no válida.");
        }
        return conexionDb;
    }

    public void DesConectarDb() {
        try {
            conexionDb.close();//Cerrar conexion
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexion con la base de datos" + error.getMessage());
        }
    }


}



