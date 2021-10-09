package Controllers;

import Miscelaneous.IdentificadorDeClase;
import Modelos.Espectaculo;
import Vistas.ArrancarPrograma;
import com.db4o.ObjectContainer;

import java.sql.*;
import java.util.ArrayList;
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

        // conecto


        try {

            if (tipoDb != DBController.DBTypes.DB4o) {

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
     * Funcion que recibe objeto espectaculo, conecta a bdd y modifica datos en bdd segun su id. diferencia entre relacionales y bdoo en if tipoDb
     * valores posibles:
     *
     * @param espectaculo
     * @return true si ha ido ok, false si no para tratar en vistas
     */
    public boolean update(Espectaculo espectaculo) {
        realizado = false;

        // conecto


        try {

            if (tipoDb != DBController.DBTypes.DB4o) {

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
     * Funcion que devuelve todos los objetos espectaculos en una lista
     * * valores posibles:
     *
     * @param
     * @return arraylist de clientes
     */
    public List<Espectaculo> selectAll() {

        List<Espectaculo> espectaculos = new ArrayList<>();

        // conecto


        try {

            if (tipoDb != DBController.DBTypes.DB4o) {

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
                messageok();///////////////////////////////////////////////////// check maria borrar

            } else {
                System.out.println("hacer el update de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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

        try {

            if (tipoDb != DBController.DBTypes.DB4o) {


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

                //cierro la sentencia
                sentencia.close();
                messageok();///////////////////////////////////////////////////// check maria borrar

                return espectaculoNew;

            } else {
                System.out.println("hacer el select by id de db4 y close connection ");
            }

        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/SqLite/DB4O: " + error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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

    public void messageok() {
        String className = claseId.getClassName(this.espectaculo);
        System.out.println("accion en: " + className + " ha ido ok");

    }


}
