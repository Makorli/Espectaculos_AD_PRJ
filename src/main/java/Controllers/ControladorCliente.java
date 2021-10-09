package Controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Miscelaneous.IdentificadorDeClase;
import Modelos.Cliente;
import Vistas.ArrancarPrograma;
import com.db4o.ObjectContainer;

public class ControladorCliente {

    DBController dbController = ArrancarPrograma.db;

    DBController.DBTypes tipoDb = dbController.getTipoDB();

    // Connection mydb = ArrancarPrograma.conexion;
    Connection mydb = dbController.getConnectionDb();

    //ObjectContainer myObjCont = ArrancarPrograma.contenedor;
    ObjectContainer myObjCont = dbController.getObjectContainerDb();

    private boolean realizado;
    private Cliente cliente;
    private IdentificadorDeClase claseId;

    private String tableName;
    private String[] attNames;

    private String myInsert;
    private String myUpdate;


    private Statement sentencia = null;
    private PreparedStatement prepSentencia = null;


    public ControladorCliente() {
        this.cliente = new Cliente();
        this.claseId = new IdentificadorDeClase(this.cliente);

        this.tableName = claseId.getClassName(this.cliente);
        this.attNames = claseId.getAttNames(this.cliente);

        this.myInsert = crearMyInsert();
        this.myUpdate = crearMyUpdate();

    }


    /**
     * Funcion que recibe objeto cliente, conecta a bdd e inserta datos de cliente en bdd. diferencia entre relacionales y bdoo en if tipoDb
     * se pasa id como string null (no como integer) para que la bdd asigne numero automatico
     * valores posibles:
     *
     * @param cliente
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean add(Cliente cliente) {
        realizado = false;


        try {
            if (tipoDb != DBController.DBTypes.DB4o) {

                prepSentencia = mydb.prepareStatement("INSERT INTO " + tableName + " VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setString(2, cliente.getDni());
                prepSentencia.setString(3, cliente.getNombre());
                prepSentencia.setString(4, cliente.getApellidos());
                prepSentencia.setString(5, cliente.getFechaNacimiento());
                prepSentencia.setBoolean(6, cliente.getBaja());


                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Inserción");

                //cierro la sentencia
                prepSentencia.close();
                messageok();///////////////////////////////////////////////////// check maria borrar
                realizado = true;

            } else {
                System.out.println("hacer el store y close connection ");///////////////////////////////////////////////////// check maria borrar
                realizado = true;
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realizado;

    }

    /**
     * Funcion que recibe objeto cliente, conecta a bdd y modifica datos de cliente en bdd segun su id. diferencia entre relacionales y bdoo en if tipoDb
     * valores posibles:
     *
     * @param cliente
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean update(Cliente cliente) {
        realizado = false;


        try {

            if (tipoDb != DBController.DBTypes.DB4o) {

                String sentencia = String.format("update " + tableName + " set " + myUpdate + " WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4], attNames[5],
                        attNames[0]);
                prepSentencia = mydb.prepareStatement(sentencia);

                prepSentencia.setString(1, cliente.getDni());
                prepSentencia.setString(2, cliente.getNombre());
                prepSentencia.setString(3, cliente.getApellidos());
                prepSentencia.setString(4, cliente.getFechaNacimiento());
                prepSentencia.setBoolean(5, cliente.getBaja());

                prepSentencia.setInt(6, cliente.getIdCliente());


                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Actualización");

                //cierro la sentencia
                prepSentencia.close();
                messageok();///////////////////////////////////////////////////// check maria borrar
                realizado = true;


            } else {
                System.out.println("hacer el update de db4 y close connection ");
                realizado = true;
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realizado;
    }

    /**
     * Funcion que devuelve todos los objetos cliente en una lista
     * * valores posibles:
     *
     * @param
     * @return arraylist de clientes
     */
    public List<Cliente> selectAll() {

        List<Cliente> clientes = new ArrayList<>();

        try {

            if (tipoDb != DBController.DBTypes.DB4o) {

                String sql = String.format("select * from " + tableName);
                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    Cliente clienteNew = new Cliente();

                    clienteNew.setIdCliente(rs.getInt(attNames[0]));
                    clienteNew.setDni(rs.getString(attNames[1]));
                    clienteNew.setNombre(rs.getString(attNames[2]));
                    clienteNew.setApellidos(rs.getString(attNames[3]));
                    clienteNew.setFechaNacimiento(rs.getString(attNames[4]));
                    clienteNew.setBaja(rs.getBoolean(attNames[5]));

                    clientes.add(clienteNew);
                }

                //cierro la sentencia
                sentencia.close();
                messageok();///////////////////////////////////////////////////// check maria borrar

            } else {
                System.out.println("hacer el update de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;
    }

    /**
     * Funcion que recibe el id de un cliente y devuelve el objeto cliente
     * valores posibles:
     *
     * @param id
     * @return objeto cliente
     */
    public Cliente selectById(int id) {

        Cliente clienteNew = new Cliente();

        try {

            if (tipoDb != DBController.DBTypes.DB4o) {


                String sql = String.format("select * from " + tableName + " WHERE %s= %d",
                        attNames[0], id);

                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {
                    clienteNew.setIdCliente(rs.getInt(attNames[0]));
                    clienteNew.setDni(rs.getString(attNames[1]));
                    clienteNew.setNombre(rs.getString(attNames[2]));
                    clienteNew.setApellidos(rs.getString(attNames[3]));
                    clienteNew.setFechaNacimiento(rs.getString(attNames[4]));
                    clienteNew.setBaja(rs.getBoolean(attNames[5]));
                }

                //cierro la sentencia
                sentencia.close();
                messageok();///////////////////////////////////////////////////// check maria borrar

                return clienteNew;

            } else {
                System.out.println("hacer el select by id de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return clienteNew;
    }

    /**
     * Funcion que devuelve la cadena de "?" para la sentencia insert, con tantos "?" como requiera la clase
     * valores posibles:
     *
     * @param
     * @return string para sql insert
     */
    public String crearMyInsert() {

        int attQ = attNames.length;
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

    /**
     * Funcion que devuelve la cadena de "%s =?" para la sentencia update, con tantos "%s =?" como requiera la clase
     * valores posibles:
     *
     * @param
     * @return string para sql update
     */
    public String crearMyUpdate() {

        int attQ = attNames.length;
        String myInsert = "";

        for (int i = 0; i < attQ - 1; i++) {
            if (i == 0) {
                myInsert = ("%s =?");
            } else {
                myInsert = myInsert + (",%s =?");
            }
        }
        return myInsert;

    }

    public void messageok() {
        String className = claseId.getClassName(this.cliente);
        System.out.println("accion en: " + className + " ha ido ok");

    }


}
