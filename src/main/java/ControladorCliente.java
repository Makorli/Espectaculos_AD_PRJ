import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class ControladorCliente {

    int tipoDb = 0; //esto debe venir del click del parque

    IdentificadorDeClase claseId;
    Cliente cliente;

    Statement sentencia = null;
    PreparedStatement prepSentencia = null;


    public ControladorCliente(Cliente cliente) {
        this.cliente = cliente;
        this.claseId = new IdentificadorDeClase(cliente);
    }

    public boolean add(Cliente cliente) {

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        String myInsert = crearMyInsert(cliente);

        try {

            if (tipoDb != 2) {

                prepSentencia = mydb.prepareStatement("INSERT INTO cliente VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setString(2, cliente.getDni());
                prepSentencia.setString(3, cliente.getNombre());
                prepSentencia.setString(4, cliente.getApellidos());
                prepSentencia.setString(5, cliente.getFechaNacimiento());
                prepSentencia.setBoolean(6, cliente.getBaja());


                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Inserción");

                //cierro la sentencia
                prepSentencia.close();

                mydb.close();

            } else {
                System.out.println("hacer el store y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            System.out.println("Error no controlado: " + e.getMessage());
        }

        return true;


    }

    public boolean update(Cliente cliente) {


        String[] attNames = claseId.getAttNames(cliente);
        int id = cliente.getIdCliente();

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();


        try {

            if (tipoDb != 2) {

                String sentencia = String.format("update cliente set %s =?, %s =?,%s =?,%s =?,%s =? WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4],attNames[5],attNames[0]);
                prepSentencia = mydb.prepareStatement(sentencia);
                //prepSentencia = mydb.prepareStatement("update cliente set " + attNames[1] + "=?," + attNames[2] + "=?," + attNames[3] + "=?," + attNames[4] + "=?, " + attNames[5] + "=?, where " + attNames[0] + "=" + id);

                prepSentencia.setString(1, cliente.getDni());
                prepSentencia.setString(2, cliente.getNombre());
                prepSentencia.setString(3, cliente.getApellidos());
                prepSentencia.setString(4, cliente.getFechaNacimiento());
                prepSentencia.setBoolean(5, cliente.getBaja());
                prepSentencia.setInt(6, cliente.getIdCliente());



                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Actualización");


                //cierro la sentencia
                prepSentencia.close();

                mydb.close();


            } else {
                System.out.println("hacer el update de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            System.out.println("Error no controlado: " + e.getMessage());
        }

        return true;
    }


    public String crearMyInsert(Cliente cliente) {


        String[] attTypes = claseId.getAttTypes(cliente);
        int attQ = attTypes.length;
        String myInsert = "";

        for (int i = 0; i < attQ; i++) {
            if (i == 0) {
                myInsert = ("?");
            } else {
                myInsert = myInsert + (", ?");
            }
        }
        return myInsert;
    }


}
