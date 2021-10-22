package Controllers;

import Miscelaneous.DateValidatorByDateTimeFormatter;
import Miscelaneous.IdentificadorDeClase;
import Modelos.Empleado;
import Modelos.Espectaculo;
import Modelos.IDHolder;
import Vistas.ArrancarPrograma;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.events.*;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Predicate;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorEmpleado {

    DBController dbController = ArrancarPrograma.db;
    DBController.DBTypes tipoDb = dbController.getTipoDB();
    Connection mydb = dbController.getConnectionDb();
    ObjectContainer myObjCont = dbController.getObjectContainerDb();


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

        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
                System.out.println(empleado.getIdEmpleado());

                prepSentencia = mydb.prepareStatement("INSERT INTO " + tableName + " VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setString(2, empleado.getDni());
                prepSentencia.setString(3, empleado.getNombre());
                prepSentencia.setString(4, empleado.getApellidos());
                prepSentencia.setString(5, empleado.getFechaNacimiento());
                prepSentencia.setString(6, empleado.getFechaContratacion());
                prepSentencia.setString(7, empleado.getNacionalidad());
                prepSentencia.setString(8, empleado.getCargo().toUpperCase());
                prepSentencia.setBoolean(9, empleado.getBaja());

                //Ejecutamos la setencia
                prepSentencia.executeUpdate();

                //cierro la sentencia
                prepSentencia.close();

                realizado = true;
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // DB4o
        else {

            //REalziamos la consulta a la base de datos en busca de un objeto empleado igual (DNI)
            try {
                ObjectSet<Empleado> empleadosOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Empleado e) {
                                return e.getDni().equalsIgnoreCase(empleado.getDni());
                            }
                        });
                // Si no hay resultado podemos añadir el nuevo empleado.
                if (empleadosOS.size() == 0) {
                    myObjCont.store(empleado);
                    realizado = true;
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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

        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
                String sentencia = String.format("update " + tableName + " set " + myUpdate + " WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4], attNames[5],
                        attNames[6], attNames[7], attNames[8],
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

                prepSentencia.executeUpdate();

                prepSentencia.close();

                realizado = true;
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // DB4o
        else {
            try {
                //Recuperamos todos los objetos Empleado con el mismo Id
                ObjectSet<Empleado> empleadosOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Empleado e) {
                                return e.getIdEmpleado() == (empleado.getIdEmpleado());
                            }
                        });
                // Si solo hay uno..que solo debería haber 1 lo almacenamos
                if (empleadosOS.size() == 1) {
                    //Recojemos el empleado de la BBDD
                    Empleado e = empleadosOS.next();
                    //modificamos todos sus campos por los nuevos..excepto el id
                    e.setDni(empleado.getDni());
                    e.setNombre(empleado.getNombre());
                    e.setApellidos(empleado.getApellidos());
                    e.setCargo(empleado.getCargo());
                    e.setFechaContratacion(empleado.getFechaContratacion());
                    e.setFechaNacimiento(empleado.getFechaNacimiento());
                    e.setNacionalidad(empleado.getNacionalidad());
                    e.setBaja(empleado.getBaja());
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
    public List<Empleado> selectAll() {

        List<Empleado> empleados = new ArrayList<>();
        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
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
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // DB4o
        else {
            try {
                //Recuperamos todos los objetos Empleados
                ObjectSet<Empleado> empleadosOS = myObjCont.queryByExample(new Empleado());
                // Si tenemos empleados recorremos el Resultado para incorporar los objetos a la lista
                if (empleadosOS.size() > 0) {
                    while (empleadosOS.hasNext())
                        empleados.add(empleadosOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return empleados;
    }

    public List<Empleado> selectByState(boolean state) {

        List<Empleado> empleados = new ArrayList<>();
        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
                String sql = String.format("select * from " + tableName + " where baja = " + state);
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
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // DB4o
        else {
            try {
                //Recuperamos todos los objetos Empleados
                Empleado empleado = new Empleado();
                empleado.setBaja(state);
                ObjectSet<Empleado> empleadosOS = myObjCont.queryByExample(empleado);
                // Si tenemos empleados recorremos el Resultado para incorporar los objetos a la lista
                if (empleadosOS.size() > 0) {
                    while (empleadosOS.hasNext())
                        empleados.add(empleadosOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return empleados;
    }

    public List<Empleado> selectResponsables() {

        List<Empleado> empleados = new ArrayList<>();
        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {

                String sql = String.format("select * from " + tableName + " WHERE cargo = 'RESPONSABLE' ");
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
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // DB4o
        else {
            try {
                //Recuperamos todos los objetos Empleados
                Empleado empleado = new Empleado();
                empleado.setCargo("RESPONSABLE");
                ObjectSet<Empleado> empleadosOS = myObjCont.queryByExample(empleado);
                // Si tenemos empleados recorremos el Resultado para incorporar los objetos a la lista
                if (empleadosOS.size() > 0) {
                    while (empleadosOS.hasNext())
                        empleados.add(empleadosOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
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

        Empleado empleadoNew = null;

        //MySQL SQLite ORACLE..
        if (tipoDb != DBController.DBTypes.DB4o) {
            try {
                String sql = String.format("select * from " + tableName + " WHERE %s= %d",
                        attNames[0], id);

                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);
                empleadoNew = new Empleado();
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
            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // DB4o
        else {
            try {
                //Recuperamos todos los objetos Empleado con el mismo Id que el indicado
                Empleado empbuscado = new Empleado();
                empbuscado.setIdEmpleado(id);
                ObjectSet<Empleado> empleadosOS = myObjCont.queryByExample(empbuscado);
                // Si solo hay uno..que solo debería haber 1 lo devolvemos
                if (empleadosOS.size() == 1) {
                    empleadoNew = empleadosOS.next();
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }
        return empleadoNew;
    }

    public Empleado getResponsableEspectaculo(Espectaculo espectaculo){
        return selectById(espectaculo.getIdResponsable());
    }

    /**
     * Funcion que devuelve la cadena de "?" para la sentencia insert, con tantos "?" como requiera la clase
     * valores posibles:
     *
     * @param
     * @return string para sql insert
     */
    private String crearMyInsert() {

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
    private String crearMyUpdate() {

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

    public String validaciones(Empleado empleado) {

        HashMap<String, String> errores = new HashMap<>();

        if (empleado.getDni().length() > 9 || empleado.getDni().equals("")) {
            if (empleado.getDni().length() > 9) {
                errores.put("DNI", "El DNI excede en longitud. MAX 9.");
            } else {
                errores.put("DNI", "El DNI no puede estar vacio.");
            }

        }
        if (empleado.getNombre().length() > 40 || empleado.getNombre().equals("")) {
            if (empleado.getNombre().length() > 40) {
                errores.put("Nombre", "El nombre excede en longitud. MAX 40.");
            } else {
                errores.put("Nombre", "El nombre no puede estar vacio.");
            }

        }
        if (empleado.getApellidos().length() > 40 || empleado.getApellidos().equals("")) {
            if (empleado.getApellidos().length() > 40) {
                errores.put("Apellidos", "El apellido excede en longitud. MAX 40.");
            } else {
                errores.put("Apellidos", "El apellido no puede estar vacio.");
            }

        }


        // Validar la fecha tanto de contratación como naciemiento, utilizamos la clase DateValidatorByDateTimeFormateer.java

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (!empleado.getFechaContratacion().equals("")) {
            DateValidatorByDateTimeFormatter d = new DateValidatorByDateTimeFormatter(formatter);
            if (!(d.isValid(empleado.getFechaContratacion()))) {
                errores.put("FechaContratacion", "La fecha de contratación no es correcta (dd/mm/yyyy).");
            }
        }


        if (!empleado.getFechaNacimiento().equals("")) {
            DateValidatorByDateTimeFormatter d = new DateValidatorByDateTimeFormatter(formatter);
            if (!(d.isValid(empleado.getFechaNacimiento()))) {
                errores.put("FechaNacimiento", "La fecha de nacimiento no es correcta (dd/mm/yyyy).");
            }
        }

        if (empleado.getNacionalidad().length() > 40) {
            errores.put("Nacionalidad", "La nacionalidad excede en longitud. MAX 40.");
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

    public void addManual (){

    }


}
