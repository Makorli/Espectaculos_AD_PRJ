import java.lang.reflect.Field;


public class IdentificadorDeClase<T> {

    //T es el parámetro de tipo genérico.
    private T t; //Declara un objeto de tipo T

    //Pase al constructor una referencia a un objeto de tipo T.
    IdentificadorDeClase(T t) {
        this.t = t;
    }

    T getOb() {
        return t;
    }


    /**
     * Funcion que devuelve String con el nombre de la clase del objeto t
     * valores posibles: depende del objeto
     *
     * @param t
     * @return string
     */
    public String getClassName(T t) {

        String className = t.getClass().getName();

        if (className.contains("com.company.")) { //imagino qu se puede quitar para que no este
            className = className.replace("com.company.", "");
        }
        return className;
    }

    /**
     * Funcion que devuelve Array de Strings con el nombre de los atributos que tiene el objeto t
     * valores posibles: depende del objeto
     *
     * @param t
     * @return array de strings
     */
    public String[] getAttNames(T t) {

        Field f[] = t.getClass().getDeclaredFields();
        int attQ = f.length;
        String atributos[] = new String[attQ];

        for (int i = 0; i < attQ; i++) {
            atributos[i] = (f[i].getName());
        }

        return atributos;
    }

    /**
     * Funcion que devuelve Array de Strings con el tipo de atributos que tiene el objeto t
     * valores posibles: int, String, Boolean, Double
     *
     * @param t
     * @return array de strings
     */
    public String[] getAttTypes(T t) {

        Field f[] = t.getClass().getDeclaredFields();
        int attQ = f.length;
        String atributos[] = new String[attQ];

        for (int i = 0; i < attQ; i++) {
            String atributo = (f[i].getType().getName());

            if (atributo.contains("java.lang.")) {
                atributo = atributo.replace("java.lang.", "");
            }
            atributos[i] = atributo;
        }

        return atributos;
    }




}
