package Controllers;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encargará de establecer las conexiones a las difernetes Bases de datos.
 * Gestionar su aperturas y sus cierres.
 */
public class DBController {


    // CLASES PRIVADAS Y ENUMS

    /**
     * Clase privada para almacenamiento de datos y credenciales para conexión con MySQL
     */
    private static class MySqlDataConnect {

        //  ATRIBUTOS CON DATOS DE CONEXION A MYSQL

        private static final String driver = "com.mysql.cj.jdbc.Driver";
        private static final String dbName = "bdmysql";
        private static final int dbPort = 3306;
        private static final String dbServer = "localhost";
        private static final String dbUser = "admin";
        private static final String dbUserPwd = "pass1234";

        // METODOS DE CONSULTA DE ATRIBUTOS Y OBTENCION DE CADENA DE CONEXION

        public static String getDriver() {
            return driver;
        }

        public static String getDbName() {
            return dbName;
        }

        public static int getDbPort() {
            return dbPort;
        }

        public static String getDbUser() {
            return dbUser;
        }

        public static String getDbUserPwd() {
            return dbUserPwd;
        }

        public static String getConnectionStr() {
            return String.format("jdbc:mysql://%s:%d/%s", dbServer, dbPort, dbName);
        }
    }

    /**
     * Clase privada para almacenamiento de datos y credenciales para conexión con SQLite
     */
    private static class SQLiteDataConnect {

        //  ATRIBUTOS CON DATOS DE CONEXION A SQLite
        private static final String driver = "org.sqlite.JDBC";
        private static final String path = "./BBDD/SQLite";
        private static final String dbName = "bdSqlite.db";
        private static final String dbUser = null;
        private static final String dbUserPwd = null;

        // METODOS DE CONSULTA DE ATRIBUTOS Y OBTENCION DE CADENA DE CONEXION
        public static String getDriver() {
            return driver;
        }

        public static String getDbName() {
            return dbName;
        }

        public static String getDbUser() {
            return dbUser;
        }

        public static String getDbUserPwd() {
            return dbUserPwd;
        }

        public static String getConnectionStr() {
            return String.format("jdbc:sqlite:%s", path + "/" + dbName);
        }
    }

    /**
     * Clase privada para almacenamiento de datos y credenciales para conexión con DB4o
     */
    private static class Db4oDataConnect {

        //  ATRIBUTOS CON DATOS DE CONEXION A DB4o
        private static final String path = "./BBDD/Db4o";
        private static final String dbName = "DB4oDB.YAP";

        // METODOS DE CONSULTA DE ATRIBUTOS Y OBTENCION DE PATH
        public static String getDbName() {
            return dbName;
        }

        public static String getFullPathName() {
            return (path + "/" + dbName);
        }

    }

    /**
     * Clase privada para almacenamiento de datos y credenciales para conexión con Oracle
     */
    private static class OracleDataConnect {

        //  ATRIBUTOS CON DATOS DE CONEXION A ORACLE

        private static final String driver = "oracle.jdbc.driver.OracleDriver";
        private static final String dbSID = "XE";
        private static final int dbPort = 1521;
        private static final String dbServer = "localhost";
        private static final String dbUser = "ADORACLE";
        private static final String dbUserPwd = "Ladeoracle";

        // METODOS DE CONSULTA DE ATRIBUTOS Y OBTENCION DE CADENA DE CONEXION

        public static String getDriver() {
            return driver;
        }

        public static String getDbSID() {
            return dbSID;
        }

        public static int getDbPort() {
            return dbPort;
        }

        public static String getDbUser() {
            return dbUser;
        }

        public static String getDbUserPwd() {
            return dbUserPwd;
        }

        public static String getConnectionStr() {
            return String.format("jdbc:oracle:thin:@%s:%d:%s", dbServer, dbPort, dbSID);
        }
    }

    public enum DBTypes {
        MySQL,  //posicion 0
        SQLite, //posicion 1
        DB4o,   //posicion 2
        Oracle, //posicion 3
    } //Enum con los tipos de Bases de datos que vamos a soportar en la clase.

    // ATRIBUTOS
    private final DBTypes tipoDB; //Tipo de Base de datos que nos enviarán en el constructor
    private Object conexionDb; //Objeto de conexion para bases de datos. Puede ser Connection o ObjectContainer

    // CONSTRUCTORES
    public DBController(DBTypes tipoDB) {
        this.tipoDB = tipoDB;
        this.ConectarDb();
    }

    // GETTERS

    public DBTypes getTipoDB() {
        return tipoDB;
    }

    // Getter que devuelve un objeto Connection con la conexión en caso de haber sido establecida.
    public Connection getConnectionDb() {
        if (conexionDb instanceof Connection)
            return (Connection) conexionDb;
        return null;
    }

    // Getter que devuelve un objeto ObjectContiner con la conexión en caso de haber sido establecida.
    public ObjectContainer getObjectContainerDb() {
        if (conexionDb instanceof ObjectContainer)
            return (ObjectContainer) conexionDb;
        return null;
    }


    //METODOS

    /**
     * Funcion de conexión a cualquier base de datos contemplada en el Enum de la clase.
     * Utiliza una subclase para obtener los datos de conexión
     *
     * @return Object de conexión que puede corresponder a Connection ObjectContainer.
     * dependiendo del tipo de datos a la que haya conectado.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void ConectarDb() {

        //Procedimiento de conexión a DB4o
        if (tipoDB == DBTypes.DB4o) {
            //conectar con DB4o
            if (!isDbConnected()) { //Solo conectamos si la base de datos no esta lista.
                try {
                    conexionDb = Db4oEmbedded.openFile(
                            Db4oEmbedded.newConfiguration(),
                            Db4oDataConnect.getFullPathName());
                } catch (Exception error) {
                    System.out.format("Error al conectar con %s : \n%s", tipoDB, error.getMessage());
                    throw error;
                }
            }
        }

        //Procedmiento "general" de conexión para SQLite, MySQL y Oracle
        else {
            //si no esta preparada cargamos el driver.
            if (!isDbReady()) {
                // Seleccionamos el driver correspondiente
                String drv = null;
                if (tipoDB == DBTypes.SQLite) drv = SQLiteDataConnect.getDriver();
                else if (tipoDB == DBTypes.MySQL) drv = MySqlDataConnect.getDriver();
                else if (tipoDB == DBTypes.Oracle) drv = OracleDataConnect.getDriver();

                try { //Cargamos el driver de conexion a la Base de datos SQL elegida
                    Class.forName(drv);
                } catch (ClassNotFoundException error) {
                    System.out.format("Error en la carga del driver JDBC de %s: \n%s", tipoDB, error.getMessage());
                }
            }
            // Si no esta conectada establecesmos la conexion
            if (!isDbConnected()) {
                try {//Realizamos la conexión con la base de datos
                    if (tipoDB == DBTypes.SQLite) {
                        conexionDb = DriverManager.getConnection(
                                SQLiteDataConnect.getConnectionStr());
                    } else if (tipoDB == DBTypes.MySQL) {
                        conexionDb = DriverManager.getConnection(
                                MySqlDataConnect.getConnectionStr(),
                                MySqlDataConnect.getDbUser(),
                                MySqlDataConnect.getDbUserPwd());
                    } else if (tipoDB == DBTypes.Oracle) {
                        conexionDb = DriverManager.getConnection(
                                OracleDataConnect.getConnectionStr(),
                                OracleDataConnect.getDbUser(),
                                OracleDataConnect.getDbUserPwd());
                    }
                } catch (SQLException error) {
                    System.out.format("Error al conectar con %s : \n%s", tipoDB, error.getMessage());
                }
            }
        }

    }

    /**
     * Cierra la base de datos adminsitrada.
     */
    public void DesconectarDb() {
        //Desconexión de bases de datos MySQL, SQLite, Oracle..etc
        if (conexionDb instanceof Connection) {
            Connection c = (Connection) conexionDb;
            try {
                if (!c.getAutoCommit()) c.commit(); // Si no tiene autocommit. Hacemos commmit
                c.close(); //Cerramos la conexión
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        //Desconexión de base de datos DB4o
        if (conexionDb instanceof ObjectContainer) {
            ObjectContainer o = (ObjectContainer) conexionDb;
            try {
                o.commit();
                o.close();
            } catch (Db4oIOException |
                    DatabaseClosedException |
                    DatabaseReadOnlyException d) {
                d.printStackTrace();
            }
        }
    }

    /**
     * Devuelve si la base de datos tiene una conexión activa (no está cerrada)
     * @return boolean
     */
    public boolean isDbConnected() {
        //Consulta a DB4o
        if (tipoDB == DBTypes.DB4o) {
            ObjectContainer o = getObjectContainerDb();
            return !(o == null || o.ext().isClosed());
        }
        // Consulta "general" para SQLite, MySQL y Oracle
        else {
            Connection c = getConnectionDb();
            try {
                if (c != null) return !c.isClosed();
            } catch (SQLException i) {
                i.printStackTrace();
            }
        }
        return false;
    }

    /**
     * DEvuelve si la base de datos dispone de una conexión (activa o no)
     * MYSQL, SQLite y ORacle verifica si el objeto conexión ya existe
     * DB4o verifica que el objeto exista y que sea una conexion abierta.
     *
     * @return
     */
    public boolean isDbReady() {
        //Consulta a DB4o
        if (tipoDB == DBTypes.DB4o) return isDbConnected();
            // Consulta "general" para SQLite, MySQL y Oracle
        else {
            Connection c = getConnectionDb();
            try {
                if (c != null) return !c.isClosed();
            } catch (SQLException i) {
                i.printStackTrace();
            }
            return false;
        }
    }

}




