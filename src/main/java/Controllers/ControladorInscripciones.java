package Controllers;

import Miscelaneous.DateValidatorByDateTimeFormatter;
import Miscelaneous.IdentificadorDeClase;
import Modelos.Inscripcion;
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

public class ControladorInscripciones {

    DBController dbController = ArrancarPrograma.db;
    DBController.DBTypes tipoDb = dbController.getTipoDB();
    Connection mydb = dbController.getConnectionDb();
    ObjectContainer myObjCont = dbController.getObjectContainerDb();

    private boolean realizado;
    private Inscripcion inscripcion;
    private IdentificadorDeClase claseId;

    private String tableName;
    private String[] attNames;

    private String myInsert;
    private String myUpdate;

    private Statement sentencia = null;
    private PreparedStatement prepSentencia = null;


    public ControladorInscripciones() {
        this.inscripcion = new Inscripcion();
        this.claseId = new IdentificadorDeClase(this.inscripcion);

        this.tableName = claseId.getClassName(this.inscripcion);
        this.attNames = claseId.getAttNames(this.inscripcion);

        this.myInsert = crearMyInsert();
        this.myUpdate = crearMyUpdate();

    }

    /**
     * Funcion que recibe objeto inscripcion conecta a bdd e inserta datos  en bdd.diferencia entre relacionales y bdoo en if tipoDb
     * se pasa id como string null (no como integer) para que la bdd asigne numero automatico
     * valores posibles:
     *
     * @param inscripcion Objeto inscripcion
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean add(Inscripcion inscripcion) {
        realizado = false;

        if (tipoDb != DBController.DBTypes.DB4o) {

            /***He modificado los parameterindex de inscripcion para que entrara bien en la base de datos de sqlite
             * los campos están:
             * idClient
             * idEspectaculo
             * fecha
             * idInscripcion
             *
             * **/


            try {
                prepSentencia = mydb.prepareStatement("INSERT INTO " + tableName + " VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setInt(2, inscripcion.getIdCliente());
                prepSentencia.setInt(3, inscripcion.getIdEspectaculo());
                prepSentencia.setString(4, inscripcion.getFecha());


                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Inserción");

                //cierro la sentencia
                prepSentencia.close();
                realizado = true;

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } // DB4o
        else {

            //REalziamos la consulta a la base de datos en busca de un objeto inscripcion igual  (IdInscripcion)
            try {
                ObjectSet<Inscripcion> inscripcionesOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Inscripcion e) {
                                return e.getId() == inscripcion.getId();
                            }
                        });
                // Si no hay resultado podemos añadir el nuevo empleado.
                if (inscripcionesOS.size() == 0) {
                    myObjCont.store(inscripcion);
                    realizado = true;
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return realizado;

    }

    /**
     * Funcion que recibe objeto inscripcion, conecta a bdd y modifica datos en bdd segun su id. diferencia entre relacionales y bdoo en if tipoDb
     * valores posibles:
     *
     * @param inscripcion
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean update(Inscripcion inscripcion) {
        realizado = false;


        if (tipoDb != DBController.DBTypes.DB4o) {

            try {

                String sentencia = String.format("update " + tableName + " set " + myUpdate + "WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3],
                        attNames[0]); // para el where
                prepSentencia = mydb.prepareStatement(sentencia);

                prepSentencia.setInt(1, inscripcion.getIdCliente());
                prepSentencia.setInt(2, inscripcion.getIdInscripcion());
                prepSentencia.setString(3, inscripcion.getFecha());

                prepSentencia.setInt(11, inscripcion.getIdInscripcion()); // para el where

                if (prepSentencia.executeUpdate() != 1) throw new Exception("Error en la Actualización");

                //cierro la sentencia
                prepSentencia.close();
                realizado = true;

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }         // DB4o
        else {
            try {
                //Recuperamos todos los objetos Inscripcion con el mismo Id
                ObjectSet<Inscripcion> inscripcionesOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Inscripcion e) {
                                return e.getIdInscripcion() == (inscripcion.getIdInscripcion());
                            }
                        });
                // Si solo hay uno..que solo debería haber 1 lo almacenamos
                if (inscripcionesOS.size() == 1) {
                    //Recojemos la inscripcion de la BBDD
                    Inscripcion e = inscripcionesOS.next();
                    //modificamos todos sus campos por los nuevos..excepto el id

                    e.setIdCliente(inscripcion.getIdCliente());
                    e.setIdInscripcion(inscripcion.getIdInscripcion());
                    e.setFecha(inscripcion.getFecha());

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
     * Funcion que devuelve todos los objetos inscripciones en una lista
     * * valores posibles:
     *
     * @param
     * @return arraylist de clientes
     */
    public List<Inscripcion> selectAll() {

        List<Inscripcion> inscripciones = new ArrayList<>();

        if (tipoDb != DBController.DBTypes.DB4o) {

            try {

                String sql = String.format("select * from " + tableName);
                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    Inscripcion inscripcionNew = new Inscripcion();

                    inscripcionNew.setIdInscripcion(rs.getInt(attNames[0]));
                    inscripcionNew.setIdCliente(rs.getInt(attNames[1]));
                    inscripcionNew.setIdEspectaculo(rs.getInt(attNames[2]));
                    inscripcionNew.setFecha(rs.getString(attNames[3]));

                    inscripciones.add(inscripcionNew);
                }

                //cierro la sentencia
                sentencia.close();

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }         // DB4o
        else {
            try {
                //Recuperamos todos los objetos Inscripcion
                ObjectSet<Inscripcion> inscripcionesOS = myObjCont.queryByExample(new Inscripcion());
                // Si tenemos inscripciones recorremos el Resultado para incorporar los objetos a la lista
                if (inscripcionesOS.size() > 0) {
                    while (inscripcionesOS.hasNext())
                        inscripciones.add(inscripcionesOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }


        return inscripciones;
    }

    /**
     * Funcion que recibe el id de un inscripcion y devuelve el objeto inscripcion
     * valores posibles:
     *
     * @param id
     * @return objeto cliente
     */
    public Inscripcion selectById(int id) {

        Inscripcion inscripcionNew = new Inscripcion();

        if (tipoDb != DBController.DBTypes.DB4o) {
            try {

                String sql = String.format("select * from " + tableName + " WHERE %s= %d",
                        attNames[0], id);

                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {
                    inscripcionNew.setIdInscripcion(rs.getInt(attNames[0]));
                    inscripcionNew.setIdCliente(rs.getInt(attNames[1]));
                    inscripcionNew.setIdInscripcion(rs.getInt(attNames[2]));
                    inscripcionNew.setFecha(rs.getString(attNames[3]));

                }

                sentencia.close();

            } catch (SQLException error) {
                System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }  // DB4o
        else {
            try {
                //Recuperamos todos los objetos inscripcion con el mismo Id que el indicado
                Inscripcion espBuscado = new Inscripcion();
                espBuscado.setIdInscripcion(id);
                ObjectSet<Inscripcion> inscripcionesOS = myObjCont.queryByExample(espBuscado);
                // Si solo hay uno..que solo debería haber 1 lo devolvemos
                if (inscripcionesOS.size() == 1) {
                    inscripcionNew = inscripcionesOS.next();
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return inscripcionNew;
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

    public String  validaciones(Inscripcion inscripcion) {

        HashMap<String, String> errores = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (!inscripcion.getFecha().equals("")) {
            DateValidatorByDateTimeFormatter d = new DateValidatorByDateTimeFormatter(formatter);
            if (!(d.isValid(inscripcion.getFecha()))) {
                errores.put("Fecha", "La fecha de inscripcion no es correcta (dd/mm/yyyy).");
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
