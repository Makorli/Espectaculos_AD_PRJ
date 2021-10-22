package Vistas;

import Controllers.ControladorCliente;
import Controllers.ControladorEspectaculo;
import Controllers.ControladorInscripciones;
import Modelos.Cliente;
import Modelos.Espectaculo;
import Modelos.Inscripcion;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MostrarInscripciones {
    private JPanel JPGeneral, JPTodasInscripciones;
    private JLabel lbTituloParque, lbInscripcion;

    private JButton btnVolver;
    private JTable tbInscripciones;
    private JScrollPane JSTablaInscripciones;
    private List<Espectaculo> Espectaculos;
    private List<Cliente> Clientes;
    private List<Inscripcion> Inscripciones;

    private ControladorInscripciones ci = new ControladorInscripciones();
    private ControladorCliente cc = new ControladorCliente();
    private ControladorEspectaculo ce = new ControladorEspectaculo();

    public MostrarInscripciones() {
// implementar una tabla con todos los datos

        tbInscripciones = new JTable();
        //tbInscripciones.setModel(new TablaInscripciones());
        JSTablaInscripciones.setViewportView(tbInscripciones);
        tbInscripciones.setModel(new TablaInscripciones(ci.selectAll()));


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoDestroy();
            }
        });
    }
    public JPanel getJPTodasInscripciones() {
        return JPTodasInscripciones;
    }




    public void autoDestroy() {

        JPTodasInscripciones.removeAll();
        JPTodasInscripciones.repaint();
    }

    /**
     * Clase para la visualizacion del Jtable de Inscripciones
     */
    public class TablaInscripciones extends AbstractTableModel {

        private String[] columnas = {"IDINSCRIPCION", "CLIENTE", "ESPECTACULO", "FECHA INSCRIPCION"};
        private List<Inscripcion> inscripcionesT;

        public TablaInscripciones(List<Inscripcion> inscripciones) {

            inscripcionesT = inscripciones;

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

                case 0: //ID DE INSCRIPCION
                    return i.getIdInscripcion();

                case 1: // NOMBRE Y APELLIDOS DEL CLIENTE
                    Cliente cliente = cc.selectById(i.getIdCliente());
                    return String.format("%s %s", cliente.getNombre(),cliente.getApellidos());

                case 2: //TITULO DEL ESPECTACULO
                    Espectaculo espectaculo = ce.selectById(i.getIdEspectaculo());
                    return String.format("%s\n\t%s",
                            espectaculo.getNombre(),
                            espectaculo.getDescripcion());

                case 3: //FECHA DE LA INSCRIPCION
                    return i.getFecha();

            }//fin switch

            return null;
        }
        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }
    }
}
