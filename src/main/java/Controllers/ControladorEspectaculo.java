package Controllers;

import Miscelaneous.IdentificadorDeClase;
import Modelos.Espectaculo;
import Vistas.ArrancarPrograma;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Predicate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControladorEspectaculo {

    DBController dbController = ArrancarPrograma.db;
    DBController.DBTypes tipoDb = dbController.getTipoDB();
    Connection mydb = dbController.getConnectionDb();
    ObjectContainer myObjCont = dbController.getObjectContainerDb();

    private boolean realizado;
    private Espectaculo espectaculo;
    private IdentificadorDeClase claseId;

    private String tableName;
    private String[] attNames;

    private String myInsert;
    private String myUpdate;

    private Statement sentencia = null;
    private PreparedStatement prepSentencia = null;
    private Db4oAutoincrement increment = null;


    public ControladorEspectaculo() {
        this.espectaculo = new Espectaculo();
        this.claseId = new IdentificadorDeClase(this.espectaculo);

        this.tableName = claseId.getClassName(this.espectaculo);
        this.attNames = claseId.getAttNames(this.espectaculo);

        this.myInsert = crearMyInsert();
        this.myUpdate = crearMyUpdate();

    }

    /**
     * Funcion que recibe objeto espectaculo conecta a bdd e inserta datos  en bdd. diferencia entre relacionales y bdoo en if tipoDb
     * se pasa id como string null (no como integer) para que la bdd asigne numero automatico
     * valores posibles:
     *
     * @param espectaculo
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean add(Espectaculo espectaculo) {
        realizado = false;

        if (tipoDb != DBController.DBTypes.DB4o) {

            try {
                prepSentencia = mydb.prepareStatement("INSERT INTO " + tableName + " VALUES (" + myInsert + ")");

                prepSentencia.setString(1, null);
                prepSentencia.setInt(2, espectaculo.getNumero());
                prepSentencia.setString(3, espectaculo.getNombre());
                prepSentencia.setInt(4, espectaculo.getAforo());
                prepSentencia.setString(5, espectaculo.getDescripcion());
                prepSentencia.setString(6, espectaculo.getLugar());
                prepSentencia.setDouble(7, espectaculo.getCoste());
                prepSentencia.setString(8, espectaculo.getFecha());
                prepSentencia.setString(9, espectaculo.getHorario());
                prepSentencia.setBoolean(10, espectaculo.getBaja());
                prepSentencia.setInt(11, espectaculo.getIdResponsable());


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

            //REalziamos la consulta a la base de datos en busca de un objeto espectaculo igual  (IdEspectaculo)
            try {
                ObjectSet<Espectaculo> espectaculosOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Espectaculo e) {
                                return e.getId() == espectaculo.getId();
                            }
                        });
                // Si no hay resultado podemos añadir el nuevo empleado.
                if (espectaculosOS.size() == 0) {
                    myObjCont.store(espectaculo);
                    realizado = true;
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }


        return realizado;


    }

    /**
     * Funcion que recibe objeto espectaculo, conecta a bdd y modifica datos en bdd segun su id. diferencia entre relacionales y bdoo en if tipoDb
     * valores posibles:
     *
     * @param espectaculo
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean update(Espectaculo espectaculo) {
        realizado = false;


        if (tipoDb != DBController.DBTypes.DB4o) {

            try {

                String sentencia = String.format("update " + tableName + " set " + myUpdate + "WHERE %s= ?",
                        attNames[1], attNames[2], attNames[3], attNames[4], attNames[5],
                        attNames[6], attNames[7], attNames[8], attNames[9], attNames[10],
                        attNames[0]); // para el where
                prepSentencia = mydb.prepareStatement(sentencia);

                prepSentencia.setInt(1, espectaculo.getNumero());
                prepSentencia.setString(2, espectaculo.getNombre());
                prepSentencia.setInt(3, espectaculo.getAforo());
                prepSentencia.setString(4, espectaculo.getDescripcion());
                prepSentencia.setString(5, espectaculo.getLugar());
                prepSentencia.setDouble(6, espectaculo.getCoste());
                prepSentencia.setString(7, espectaculo.getFecha());
                prepSentencia.setString(8, espectaculo.getHorario());
                prepSentencia.setBoolean(9, espectaculo.getBaja());
                prepSentencia.setInt(10, espectaculo.getIdResponsable());

                prepSentencia.setInt(11, espectaculo.getIdEspectaculo()); // para el where

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
                //Recuperamos todos los objetos Espectaculo con el mismo Id
                ObjectSet<Espectaculo> espectaculosOS = myObjCont.query(
                        new Predicate<>() {
                            @Override
                            public boolean match(Espectaculo e) {
                                return e.getIdEspectaculo() == (espectaculo.getIdEspectaculo());
                            }
                        });
                // Si solo hay uno..que solo debería haber 1 lo almacenamos
                if (espectaculosOS.size() == 1) {
                    //Recojemos el empleado de la BBDD
                    Espectaculo e = espectaculosOS.next();
                    //modificamos todos sus campos por los nuevos..excepto el id

                    e.setNumero(espectaculo.getNumero());
                    e.setNombre(espectaculo.getNombre());
                    e.setAforo(espectaculo.getAforo());
                    e.setDescripcion(espectaculo.getDescripcion());
                    e.setLugar(espectaculo.getLugar());
                    e.setCoste(espectaculo.getCoste());
                    e.setFecha(espectaculo.getFecha());
                    e.setHorario(espectaculo.getHorario());
                    e.setBaja(espectaculo.getBaja());
                    e.setIdResponsable(espectaculo.getIdResponsable());

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
     * Funcion que devuelve todos los objetos espectaculos en una lista
     * * valores posibles:
     *
     * @param
     * @return arraylist de clientes
     */
    public List<Espectaculo> selectAll() {

        List<Espectaculo> espectaculos = new ArrayList<>();

        if (tipoDb != DBController.DBTypes.DB4o) {

            try {

                String sql = String.format("select * from " + tableName);
                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {

                    Espectaculo espectaculoNew = new Espectaculo();

                    espectaculoNew.setIdEspectaculo(rs.getInt(attNames[0]));
                    espectaculoNew.setNumero(rs.getInt(attNames[1]));
                    espectaculoNew.setNombre(rs.getString(attNames[2]));
                    espectaculoNew.setAforo(rs.getInt(attNames[3]));
                    espectaculoNew.setDescripcion(rs.getString(attNames[4]));
                    espectaculoNew.setLugar(rs.getString(attNames[5]));
                    espectaculoNew.setCoste(rs.getDouble(attNames[6]));
                    espectaculoNew.setFecha(rs.getString(attNames[7]));
                    espectaculoNew.setHorario(rs.getString(attNames[8]));
                    espectaculoNew.setBaja(rs.getBoolean(attNames[9]));
                    espectaculoNew.setIdResponsable(rs.getInt(attNames[10]));


                    espectaculos.add(espectaculoNew);
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
                //Recuperamos todos los objetos Espectaculo
                ObjectSet<Espectaculo> espectaculosOS = myObjCont.queryByExample(new Espectaculo());
                // Si tenemos espectaculos recorremos el Resultado para incorporar los objetos a la lista
                if (espectaculosOS.size() > 0) {
                    while (espectaculosOS.hasNext())
                        espectaculos.add(espectaculosOS.next());
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }


        return espectaculos;
    }

    /**
     * Funcion que recibe el id de un espectaculo y devuelve el objeto espectaculo
     * valores posibles:
     *
     * @param id
     * @return objeto cliente
     */
    public Espectaculo selectById(int id) {

        Espectaculo espectaculoNew = new Espectaculo();

        if (tipoDb != DBController.DBTypes.DB4o) {
            try {

                String sql = String.format("select * from " + tableName + " WHERE %s= %d",
                        attNames[0], id);

                sentencia = mydb.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);


                while (rs.next()) {
                    espectaculoNew.setIdEspectaculo(rs.getInt(attNames[0]));
                    espectaculoNew.setNumero(rs.getInt(attNames[1]));
                    espectaculoNew.setNombre(rs.getString(attNames[2]));
                    espectaculoNew.setAforo(rs.getInt(attNames[3]));
                    espectaculoNew.setDescripcion(rs.getString(attNames[4]));
                    espectaculoNew.setLugar(rs.getString(attNames[5]));
                    espectaculoNew.setCoste(rs.getDouble(attNames[6]));
                    espectaculoNew.setFecha(rs.getString(attNames[7]));
                    espectaculoNew.setHorario(rs.getString(attNames[8]));
                    espectaculoNew.setBaja(rs.getBoolean(attNames[9]));
                    espectaculoNew.setIdResponsable(rs.getInt(attNames[10]));

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
                //Recuperamos todos los objetos espectaculo con el mismo Id que el indicado
                Espectaculo espBuscado = new Espectaculo();
                espBuscado.setIdEspectaculo(id);
                ObjectSet<Espectaculo> espectaculosOS = myObjCont.queryByExample(espBuscado);
                // Si solo hay uno..que solo debería haber 1 lo devolvemos
                if (espectaculosOS.size() == 1) {
                    espectaculoNew = espectaculosOS.next();
                }
            } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                e.printStackTrace();
            }
        }

        return espectaculoNew;
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

    public HashMap<String, String> validaciones(Espectaculo espectaculo) {

        HashMap<String, String> errores = new HashMap<>();

        //codigoo
        //comprobar que antes de dar a modificarse ha seleccionado algun cliente, ACTUALMENTE CASCA EN MODIFICAR PUES NO CONTEMPLAMOS UQE NO SE SELECCIONE NADIE
        //comprobar que antes de guardar se han pasado todos los datos necesarios, NUMERO, NOMBRE Y AFORO SON NOT NULL

        return errores;
    }


}
