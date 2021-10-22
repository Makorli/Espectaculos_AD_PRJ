package Controllers;

import Modelos.IDHolder;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.events.EventRegistry;
import com.db4o.events.EventRegistryFactory;
import com.db4o.ext.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        private static final String dbSID = "ORCLCDB";
        private static final int dbPort = 1521;
        private static final String dbServer = "localhost";
        private static final String dbUser = "ParqueOracle"; //"ADORACLE";
        private static final String dbUserPwd = "parqueoracle"; //"Ladeoracle";

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
    private Db4oAutoincrement increment; //Variable para persistencia de Ids autoincrementales en DB4o
    private EventRegistry eventRegistry; // Registro de eventos para Db40

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
                    //Seteamos la base de datos para Autoicrementar los ids

                    //https://github.com/lytico/db4o/blob/master/reference/CodeExamples/java/src/com/db4odoc/disconnectedobj/idexamples/AutoIncrement.java
                    //https://forge.fiware.org/scm/viewvc.php/*checkout*/trunk/cookbooks/GESoftware/tmp/db4o-8.0/doc/reference/Content/advanced_topics/callbacks/possible_usecases/autoincrement.htm?revision=819&root=testbed
                    setDb4oAutoincrement(getObjectContainerDb());

                    //Indexamos por el campo ID para acelerar las busquedas.
                    Configuration conf = getObjectContainerDb().ext().configure();
                    conf.objectClass(IDHolder.class).objectField("id").indexed(true);

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
     *
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

    /**
     * Establece el seteo automatico de valores autoincrementales en el objeto contenedor
     * de DB4o para todas las clases en el contenidas.
     *
     * @param objectContainer
     */
    public void setDb4oAutoincrement(ObjectContainer objectContainer) {
        //Seteamos la clase incremental y su registo de eventos para establecer el id automaticamente
        increment = new Db4oAutoincrement(objectContainer);
        eventRegistry = EventRegistryFactory.forObjectContainer(objectContainer);
        eventRegistry.creating().addListener(
                (event4, objectArgs) -> {
                    if (objectArgs.object() instanceof IDHolder) {
                        IDHolder idHolder = (IDHolder) objectArgs.object();
                        idHolder.setId(increment.getNextID(idHolder.getClass()));
                    }
                });
        eventRegistry.committing().addListener(
                (commitEventArgsEvent4, commitEventArgs) -> increment.storeState());


        // https://bdooinfo.wordpress.com/db4o-consultas-nativas-nq-native-query/
    }

    /**
     * Procedimeinto que deuelve los metadatos de la BD gestionada por el DBController
     * @return Strinbuilder con metadatos o null
     */
    public StringBuilder getDBMetadata() {
        StringBuilder dbMetadataSb = new StringBuilder();
        String esquemaSql = null;
        switch (this.tipoDB) {
            case MySQL:
                //seteamos el esquema sobre el que realizar la consulta y dejamos correr el switch
                esquemaSql = MySqlDataConnect.getDbName();
            case SQLite:
                //nos aseguramos de que tipo de base de datos MySQL o SQLite y seteamos el esquema.
                if (this.tipoDB == DBTypes.SQLite) esquemaSql = SQLiteDataConnect.getDbName();
                try {
                    //Recogemos la conexión del controlador
                    DatabaseMetaData dbmd = getConnectionDb().getMetaData();
                    //NOMBRE BD
                    dbMetadataSb.append(String.format("Nombre BD: %s \n", dbmd.getDatabaseProductName()));
                    //DRIVER BD
                    dbMetadataSb.append(String.format("Driver : %s \n", dbmd.getDriverName()));
                    //DRIVER VERSION
                    dbMetadataSb.append(String.format("Driver Version: %s \n", dbmd.getDriverVersion()));
                    //URL BD
                    dbMetadataSb.append(String.format("URL BD: %s \n", dbmd.getURL()));
                    //USUARIO BD
                    dbMetadataSb.append(String.format("Usuario BD: %s \n", dbmd.getUserName()));
                    //Tablas y sus detalles
                    //Obtenemos las tablas del esquema
                    dbMetadataSb.append("\n Composicion de la Base de datos...\n\n");
                    ResultSet resultSet =
                            dbmd.getTables(
                                    null,
                                    esquemaSql,
                                    null,
                                    new String[]{"Table"});
                    //recorremos el resultset para recorrer las tablas contenidas en el y sacar sus detalles.
                    String nombreTabla;
                    while (resultSet.next()) {
                        //pinteamos la tabla y el tipo
                        nombreTabla = resultSet.getString("TABLE_NAME");
                        dbMetadataSb.append(
                                String.format("   %s: %s \n",
                                        resultSet.getString("TABLE_TYPE"),
                                        nombreTabla)
                        );
                        //Por cada tabla extraemos las columnas / campos y su tipo
                        ResultSet resultSet1 = dbmd.getColumns(
                                null,
                                esquemaSql,
                                nombreTabla,
                                null);

                        //Extraemos los datos de la tabla
                        while (resultSet1.next()) {
                            dbMetadataSb.append(
                                    String.format("     Campo: %-40s\tTipo: %-20s\tNulable: %B\n",
                                            resultSet1.getString("COLUMN_NAME"),
                                            resultSet1.getString("TYPE_NAME"),
                                            resultSet1.getBoolean("NULLABLE")
                                    )
                            );
                        }
                        dbMetadataSb.append("\n");


                    }
                } catch (SQLException s) {
                    dbMetadataSb.append("Error en consulta de Metadatos");
                }
                break;

            case DB4o:
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");

                //INFORMACION BASICA DE OBJECT CONTAINER (BASE DE DATOS)
                dbMetadataSb.append(
                        String.format("Nombre BD: %s \n", getObjectContainerDb().toString()));
                dbMetadataSb.append(
                        String.format("Fecha creación: %s \n",
                                df.format(getObjectContainerDb().ext().identity().getCreationTime())));
                dbMetadataSb.append(
                        String.format("DB UUID: %s \n",
                                getObjectContainerDb().ext().identity().i_uuid));
                dbMetadataSb.append(
                        String.format("DB Version: %s \n",
                                getObjectContainerDb().ext().version()));

                //INFORMACION ESPECIFICA DE LAS CLASES DEL OBJECT CONTAINER
                dbMetadataSb.append("\n Composicion de la Base de datos...\n\n");
                //Recojemos todas las clases almacenadas
                StoredClass[] misclases= getObjectContainerDb().ext().storedClasses();
                //Recorremos el listado
                for (StoredClass s: misclases){
                    dbMetadataSb.append(String.format("   Objeto: %s\n", s.getName()));
                    //Extraemos los campos
                    StoredField[] misCampos = s.getStoredFields();
                    //Recorremos los atributos
                    for (StoredField sf: misCampos){
                        //extraemos la inforamción deseada
                        dbMetadataSb.append(String.format("    Atributo:   %-50S\tTipo: %-40s\tIndexado: %B\n",
                                sf.getName(),
                                //control para evitar sf.getStoredType con NullpointerException
                                (sf.getStoredType()!=null)?sf.getStoredType().getName():"N/A",
                                sf.hasIndex()
                        ));
                    }
                    dbMetadataSb.append("\n");
                }
                break;
            case Oracle:
                //MUy simliar a MySQL
                //TODO
                break;
            default:
                dbMetadataSb.append("Base de datos no reconocida");
                break;
        }
        return dbMetadataSb;

    }
}




