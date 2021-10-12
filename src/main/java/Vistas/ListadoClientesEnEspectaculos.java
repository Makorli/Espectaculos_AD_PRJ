package Vistas;

import Modelos.Cliente;

import javax.swing.*;

public class ListadoClientesEnEspectaculos {
    private JLabel lbTituloParque;
    private JPanel JPGeneral, JPMostrarClienteEspectaculo, JPListadoClientes;

    private JList<Cliente> lstClientes; // muestra el listado de clientes
    private JTable table1;



    public ListadoClientesEnEspectaculos() {




    }



    public JPanel getJPMostrarClienteEspectaculo() {
        return JPMostrarClienteEspectaculo;
    }


}
