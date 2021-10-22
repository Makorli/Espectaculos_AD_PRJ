package Controllers;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.HashMap;
import java.util.Map;

//https://forge.fiware.org/scm/viewvc.php/*checkout*/trunk/cookbooks/GESoftware/tmp/db4o-8.0/doc/reference/Content/advanced_topics/callbacks/possible_usecases/autoincrement.htm?revision=819&root=testbed
// https://github.com/lytico/db4o/blob/master/reference/CodeExamples/java/src/com/db4odoc/disconnectedobj/idexamples/AutoIncrement.java

/**
 * Clase para autoincremnto numerico de Ids en DB4o
 */
public class Db4oAutoincrement {

    private PersistedAutoIncrements state = null;  //Objeto crado para persisntencia de los autoincremenos en DB4o
    private final ObjectContainer container;    //ObjectContainer (Db4o) al que aplicar los autoincrementos.

    //CONSTRUCTOR
    public Db4oAutoincrement(ObjectContainer container) {
        this.container = container;
    }

    /**
     * Procedimiento que devuelve el siguiente valor autoincremental para la clase especificada
     * @param forClass Clase de la que se desea obtener el siguiente indice autoincremental
     * @return int con valor del siguiente numero valido para esa clase.
     */
    public synchronized int getNextID(Class forClass) {
        PersistedAutoIncrements incrementState = ensureLoadedIncrements();
        return incrementState.nextNumber(forClass);
    }

    /**
     * Procedimiento que almacena el objeto PersistedAutoIncrements en la base de datos Db4o
     * Se realiza sincronizado para evitar errores de concurrencia en hilos
     */
    public synchronized void storeState() {
        if (null != state) {
            container.ext().store(state, 2);
        }
    }

    /**
     * Procedimiento que se asegura de que se carga el objeto de gestion de los autoincrmentos.
     * @return
     */
    private PersistedAutoIncrements ensureLoadedIncrements() {
        if (null == state) {
            state = loadOrCreateState();
        }
        return state;
    }

    /**
     * Procedimiento que consulta si existe  no un objeto PersistedAutoincrements
     * para la clase del ObjectContainer seleccionado. Si no existe lo crea.
     * @return Objeto PersistedAutoIncrements de la base de datos ya existente o creado
     * @throws IllegalStateException en el caso de existir mas de un objeto PersistedAutoIncrements
     */
    private PersistedAutoIncrements loadOrCreateState() {
        ObjectSet<PersistedAutoIncrements> existingState = container.query(PersistedAutoIncrements.class);
        if (existingState.size() == 0) {
            return new PersistedAutoIncrements();
        } else if (existingState.size() == 1) {
            return existingState.get(0);
        } else {
            throw new IllegalStateException("Cannot have more than one state stored in database");
        }
    }

    /**
     * Clase que genera numeros autoincrementales para cada clase y los almacena en
     * un diccionario
     */
    private static class PersistedAutoIncrements {
        private final Map<Class, Integer> currentHighestIds = new HashMap<Class, Integer>();

        public int nextNumber(Class forClass) {
            Integer number = currentHighestIds.get(forClass);
            if (null == number) {
                number = 0;
            }
            number += 1;
            currentHighestIds.put(forClass, number);
            return number;
        }
    }
}

