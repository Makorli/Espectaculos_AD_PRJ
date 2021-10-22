package Controllers;

import Miscelaneous.DateValidatorByDateTimeFormatter;
import Miscelaneous.IdentificadorDeClase;
import Modelos.Cliente;
import Vistas.ArrancarPrograma;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Predicate;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorCliente {

    DBController dbController = ArrancarPrograma.db;
    DBController.DBTypes tipoDb = dbController.getTipoDB();
    Connection mydb = dbController.getConnectionDb();
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
    private Db4oAutoincrement increment = null;


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
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
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
                realizado = true;

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            //REalziamos la consulta a la base de datos en busca de un objeto cliente igual (DNI)
            try {
                ObjectSet<Cliente> clientesOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Cliente c) {
                                return c.getDni().equalsIgnoreCase(cliente.getDni());
                            }
                        });
                // Si no hay resultado podemos añadir el nuevo empleado.
                if (clientesOS.size() == 0) {
                    myObjCont.store(cliente);
                    realizado = true;
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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

        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
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
                realizado = true;

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                //Recuperamos todos los objetos Cliente con el mismo Id
                ObjectSet<Cliente> clientesOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Cliente c) {

                                return c.getIdCliente() == (cliente.getIdCliente());
                            }
                        });
                // Si solo hay uno..que solo debería haber 1 lo almacenamos
                if (clientesOS.size() == 1) {
                    //Recojemos el empleado de la BBDD
                    Cliente e = clientesOS.next();
                    //modificamos todos sus campos por los nuevos..excepto el id
                    e.setDni(cliente.getDni());
                    e.setNombre(cliente.getNombre());
                    e.setApellidos(cliente.getApellidos());
                    e.setFechaNacimiento(cliente.getFechaNacimiento());
                    e.setBaja(cliente.getBaja());
                    //almacenamos el empleado
                    myObjCont.store(e);
                    realizado = true;
                }

            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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


        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
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
                sentencia.close();

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                //Recuperamos todos los objetos Cliente
                ObjectSet<Cliente> clientesOS = myObjCont.queryByExample(new Cliente());
                // Si tenemos clientes recorremos el Resultado para incorporar los objetos a la lista
                if (clientesOS.size() > 0) {
                    while (clientesOS.hasNext())
                        clientes.add(clientesOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return clientes;
    }

    public List<Cliente> selectByState(boolean state) {

        List<Cliente> clientes = new ArrayList<>();


        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
                String sql = String.format("select * from " + tableName + " where baja = " + (state?1:0));
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
                sentencia.close();

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                //Recuperamos todos los objetos Cliente
                Cliente cliente = new Cliente();
                cliente.setBaja(state);
                ObjectSet<Cliente> clientesOS = myObjCont.queryByExample(cliente);
                // Si tenemos clientes recorremos el Resultado para incorporar los objetos a la lista
                if (clientesOS.size() > 0) {
                    while (clientesOS.hasNext())
                        clientes.add(clientesOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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


        if (tipoDb != DBController.DBTypes.DB4o) {

            try {
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
                return clienteNew;

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                //Recuperamos todos los objetos Empleado con el mismo Id que el indicado
                Cliente clibuscado = new Cliente();
                clibuscado.setIdCliente(id);
                ObjectSet<Cliente> clientesOS = myObjCont.queryByExample(clibuscado);
                // Si solo hay uno..que solo debería haber 1 lo devolvemos
                if (clientesOS.size() == 1) {
                    clienteNew = clientesOS.next();
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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

    public String validaciones(Cliente cliente) {

        HashMap<String, String> errores = new HashMap<>();

        if (cliente.getDni().length() > 9 || cliente.getDni().equals("")) {
            if (cliente.getDni().length() > 9) {
                errores.put("DNI", "El DNI excede en longitud. MAX 9.");
            } else {
                errores.put("DNI", "El DNI no puede estar vacio.");
            }

        }
        if (cliente.getNombre().length() > 40 || cliente.getNombre().equals("")) {
            if (cliente.getNombre().length() > 40) {
                errores.put("Nombre", "El nombre excede en longitud. MAX 40.");
            } else {
                errores.put("Nombre", "El nombre no puede estar vacio.");
            }

        }
        if (cliente.getApellidos().length() > 40 || cliente.getApellidos().equals("")) {
            if (cliente.getApellidos().length() > 40) {
                errores.put("Apellidos", "El apellido excede en longitud. MAX 40.");
            } else {
                errores.put("Apellidos", "El apellido no puede estar vacio.");
            }

        }


        // Validar la fecha tanto de contratación como naciemiento, utilizamos la clase DateValidatorByDateTimeFormateer.java

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        if (!cliente.getFechaNacimiento().equals("")) {
            DateValidatorByDateTimeFormatter d = new DateValidatorByDateTimeFormatter(formatter);
            if (!(d.isValid(cliente.getFechaNacimiento()))) {
                errores.put("FechaNacimiento", "La fecha de nacimiento no es correcta (dd/mm/yyyy).");
            }
        }

        //Utilizamos esta variable para guardar el mensaje de error.
        StringBuilder texto = new StringBuilder();

        if (errores.size() > 0) {
            for (Map.Entry<String, String> entry : errores.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                texto.append(v + "\n");
            }
            return texto.toString();
        } else {
            return null;
        }
    }


}
