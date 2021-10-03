import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

/**
 * Clase para crear Bases de datos DB4o.
 */
public class Db4oHandler {

    private ObjectContainer db; //Objeto de base de datos
    private String myBdpath;  //path de ubicacion de la Base de datos


    /**
     * Constructor de clase. Genera el objeto Object container.
     * @param pathbd
     */
    public Db4oHandler(String pathbd) {
        this.myBdpath = pathbd;
        openDatabase(pathbd);
    }

    /**
     * Procedimeinto de creacion y / o conexion a Base de datos embebida DB4o
     * @param path espceifica la ubicación del fichero de Base de datos
     */
    private void openDatabase(String path) {
        EmbeddedConfiguration conf = Db4oEmbedded.newConfiguration();
        db = Db4oEmbedded.openFile(conf, path);
    }

    /**
     * Procedimeinto para cierre de Base de datos
     * @exception  com.db4o.ext.Db4oIOException
     * @exception com.db4o.ext.DatabaseClosedException
     * @exception com.db4o.ext.DatabaseReadOnlyException
     */
    public void close() throws com.db4o.ext.Db4oIOException,
            com.db4o.ext.DatabaseClosedException,
            com.db4o.ext.DatabaseReadOnlyException {

        db.commit(); //Realizamos un commit
        db.close(); //cerramos la base de datos.
    }

    /**
     * Retorna el Object container que representa la base de datos DB40
     * @return ObjectContainer db
     */
    public ObjectContainer getDb() {
        return db;
    }
}