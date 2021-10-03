package Controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Miscelaneous.IdentificadorDeClase;
import Modelos.Cliente;


public class ControladorCliente {

    int tipoDb = 1; //esto debe venir del click del parque

    private boolean realizado;
    private Cliente cliente;
    IdentificadorDeClase claseId;

    Statement sentencia = null;
    PreparedStatement prepSentencia = null;


    public ControladorCliente() {
        this.cliente = new Cliente();
        this.claseId = new IdentificadorDeClase(this.cliente);
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

        // conecto
        realizado = false;
        Connection mydb = new Conexion(tipoDb).ConectarDb();
        String className = claseId.getClassName(cliente);
        String myInsert = crearMyInsert(cliente);

        try {

            if (tipoDb != 2) {

                prepSentencia = mydb.prepareStatement("INSERT INTO " + className + " VALUES (" + myInsert + ")");

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
        String className = claseId.getClassName(cliente);
        String[] attNames = claseId.getAttNames(cliente);
        int id = cliente.getIdCliente();

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {

                String sentencia = String.format("update " + className + " set %s =?, %s =?,%s =?,%s =?,%s =? WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4], attNames[5], attNames[0]);
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

                mydb.close();
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

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {

                String sql = String.format("select * from cliente");
                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    Cliente clienteNew = new Cliente();
                    String[] attNames = claseId.getAttNames(this.cliente);

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

                mydb.close();
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
        String[] attNames = claseId.getAttNames(this.cliente);

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {


                String sql = String.format("select * from cliente WHERE %s= %d",
                        attNames[0], id);


                //String sql = String.format("select * from cliente");
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

                mydb.close();
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

    public void messageok() {
        String className = claseId.getClassName(this.cliente);
        System.out.println("accion en: " + className + " ha ido ok");

    }


}
