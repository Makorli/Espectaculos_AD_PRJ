package Controllers;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase para autoincremnto numerico de Ids en DB4o
 */
//https://forge.fiware.org/scm/viewvc.php/*checkout*/trunk/cookbooks/GESoftware/tmp/db4o-8.0/doc/reference/Content/advanced_topics/callbacks/possible_usecases/autoincrement.htm?revision=819&root=testbed
// https://github.com/lytico/db4o/blob/master/reference/CodeExamples/java/src/com/db4odoc/disconnectedobj/idexamples/AutoIncrement.java

public class Db4oAutoincrement {

    private PersistedAutoIncrements state = null;
    private final ObjectContainer container;

    public Db4oAutoincrement(ObjectContainer container) {
        this.container = container;
    }

    // #example: getting the next id and storing state
    public synchronized int getNextID(Class forClass) {
        PersistedAutoIncrements incrementState = ensureLoadedIncrements();
        return incrementState.nextNumber(forClass);
    }

    public synchronized void storeState() {
        if (null != state) {
            container.ext().store(state, 2);
        }
    }


    private PersistedAutoIncrements ensureLoadedIncrements() {
        if (null == state) {
            state = loadOrCreateState();
        }
        return state;

    }

    /**
     * Clase que consulta si existe  no un objeto PersistedAutoincrements
     * para la clase del ObjectContainer seleccionado.
     * @return
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

