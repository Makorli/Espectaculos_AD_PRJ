import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

public class Db4oHandler {

    private ObjectContainer db;
    private String myBdpath;


    public Db4oHandler(String pathbd) {
        this.myBdpath = pathbd;
        openDatabase(pathbd);
    }

    private void openDatabase(String path) {
        /*db = Db4oEmbedded.openFile(
                Db4oEmbedded.newConfiguration(),
                myBdpath);
        */
        EmbeddedConfiguration conf = Db4oEmbedded.newConfiguration();
        db = Db4oEmbedded.openFile(conf, path);


    }
    public void close() {
        try {
            db.commit(); //commit the last in-mem cached (not persisted) changes!
            db.close();
        } catch(Exception e){
            //exception handling here!
        }
    }
    public ObjectContainer getDb() {
        return db;
    }
}
