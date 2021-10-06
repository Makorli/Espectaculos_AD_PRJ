package Controllers;

import Miscelaneous.IdentificadorDeClase;
import Modelos.Cliente;
import Modelos.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorEmpleado {

    int tipoDb = 1; //esto debe venir del click del parque

    private boolean realizado;
    private Empleado empleado;
    private IdentificadorDeClase claseId;

    private String tableName;
    private String[] attNames;

    private String myInsert;
    private String myUpdate;


    private Statement sentencia = null;
    private PreparedStatement prepSentencia = null;


    public ControladorEmpleado() {

        this.empleado = new Empleado();
        this.claseId = new IdentificadorDeClase(this.empleado);

        this.tableName = claseId.getClassName(this.empleado);
        this.attNames = claseId.getAttNames(this.empleado);

        this.myInsert = crearMyInsert();
        this.myUpdate = crearMyUpdate();


    }


    /**
     * Funcion que recibe objeto empleado, conecta a bdd e inserta datos en bdd. diferencia entre relacionales y bdoo en if tipoDb
     * se pasa id como string null (no como integer) para que la bdd asigne numero automatico
     * valores posibles:
     *
     * @param empleado
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean add(Empleado empleado) {
        realizado = false;

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {
            if (tipoDb != 2) {

                prepSentencia = mydb.prepareStatement("INSERT INTO " + tableName + " VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setString(2, empleado.getDni());
                prepSentencia.setString(3, empleado.getNombre());
                prepSentencia.setString(4, empleado.getApellidos());
                prepSentencia.setString(5, empleado.getFechaNacimiento());
                prepSentencia.setString(6, empleado.getFechaContratacion());
                prepSentencia.setString(7, empleado.getNacionalidad());
                prepSentencia.setString(8, empleado.getCargo());
                prepSentencia.setBoolean(9, empleado.getBaja());



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
     * Funcion que recibe objeto empleado, conecta a bdd y modifica datos  en bdd segun su id. diferencia entre relacionales y bdoo en if tipoDb
     * valores posibles:
     *
     * @param empleado
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean update(Empleado empleado) {
        realizado = false;


        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {

                String sentencia = String.format("update " + tableName + " set " + myUpdate + " WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4], attNames[5],
                        attNames[6],attNames[7],attNames[8],
                        attNames[0]);
                prepSentencia = mydb.prepareStatement(sentencia);

                prepSentencia.setString(1, empleado.getDni());
                prepSentencia.setString(2, empleado.getNombre());
                prepSentencia.setString(3, empleado.getApellidos());
                prepSentencia.setString(4, empleado.getFechaNacimiento());
                prepSentencia.setString(5, empleado.getFechaContratacion());
                prepSentencia.setString(6, empleado.getNacionalidad());
                prepSentencia.setString(7, empleado.getCargo());
                prepSentencia.setBoolean(8, empleado.getBaja());

                prepSentencia.setInt(9, empleado.getIdEmpleado());


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
    public List<Empleado> selectAll() {

        List<Empleado> empleados = new ArrayList<>();

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {

                String sql = String.format("select * from " + tableName);
                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    Empleado empleadoNew = new Empleado();

                    empleadoNew.setIdEmpleado(rs.getInt(attNames[0]));
                    empleadoNew.setDni(rs.getString(attNames[1]));
                    empleadoNew.setNombre(rs.getString(attNames[2]));
                    empleadoNew.setApellidos(rs.getString(attNames[3]));
                    empleadoNew.setFechaNacimiento(rs.getString(attNames[4]));
                    empleadoNew.setFechaContratacion(rs.getString(attNames[5]));
                    empleadoNew.setNacionalidad(rs.getString(attNames[6]));
                    empleadoNew.setCargo(rs.getString(attNames[7]));
                    empleadoNew.setBaja(rs.getBoolean(attNames[8]));

                    empleados.add(empleadoNew);
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

        return empleados;
    }

    /**
     * Funcion que recibe el id de un cliente y devuelve el objeto cliente
     * valores posibles:
     *
     * @param id
     * @return objeto cliente
     */
    public Empleado selectById(int id) {

        Empleado empleadoNew = new Empleado();

        // conecto
        Connection mydb = new Conexion(tipoDb).ConectarDb();

        try {

            if (tipoDb != 2) {


                String sql = String.format("select * from " + tableName + " WHERE %s= %d",
                        attNames[0], id);

                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    empleadoNew.setIdEmpleado(rs.getInt(attNames[0]));
                    empleadoNew.setDni(rs.getString(attNames[1]));
                    empleadoNew.setNombre(rs.getString(attNames[2]));
                    empleadoNew.setApellidos(rs.getString(attNames[3]));
                    empleadoNew.setFechaNacimiento(rs.getString(attNames[4]));
                    empleadoNew.setFechaContratacion(rs.getString(attNames[5]));
                    empleadoNew.setNacionalidad(rs.getString(attNames[6]));
                    empleadoNew.setCargo(rs.getString(attNames[7]));
                    empleadoNew.setBaja(rs.getBoolean(attNames[8]));


                }

                //cierro la sentencia
                sentencia.close();

                mydb.close();
                messageok();///////////////////////////////////////////////////// check maria borrar

                return empleadoNew;

            } else {
                System.out.println("hacer el select by id de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return empleadoNew;
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
        String className = claseId.getClassName(this.empleado);
        System.out.println("accion en: " + className + " ha ido ok");

    }


}
