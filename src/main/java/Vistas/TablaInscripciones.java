package Vistas;

import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaInscripciones extends AbstractTableModel {

    private String[] columnas = {"IDINSCRIPCION", "CLIENTE", "ESPECTACULO", "FECHA INSCRIPCION"};
    private List<Inscripcion> inscripcionesT;
    private List<Cliente> clientesT;
    private List<Espectaculo> espectaculosT;

    public TablaInscripciones() {
        inscripcionesT = new ArrayList<>();
    }

    public TablaInscripciones(List<Inscripcion> inscripciones, List<Cliente> clientes, List<Espectaculo> espectaculos) {

        inscripcionesT = inscripciones;
        clientesT = clientes;
        espectaculosT = espectaculos;

    }


    @Override
    public int getRowCount() {
        return inscripcionesT.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Inscripcion i = inscripcionesT.get(rowIndex);


        switch (columnIndex) {

            case 0:
                return i.getIdInscripcion();
            case 1:

                for (Cliente c : clientesT) {
                    if (c.getIdCliente() == i.getIdCliente()) {
                        return c.getNombre();
                    }
                }
            case 2:

                for (Espectaculo e : espectaculosT) {
                    if (e.getIdEspectaculo() == i.getIdEspectaculo()) {
                        return e.getNombre();
                    }
                }
            case 3:

               return i.getFecha();




        }//fin switch


        return null;
    }
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
}
