package com.company;

import Clases.Cliente;
import Clases.Visita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class visitaClientes extends JFrame{
    private JPanel jpVisitas;
    private JTable visitasTabla;

    public visitaClientes(ArrayList<Cliente> listaClientesMysql) {

        add(jpVisitas);

        setTitle("Lista clientes de la visita");

        setSize(1000,500);

        DefaultTableModel modeloTablaCliente = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTablaCliente.setColumnIdentifiers(new Object[]{
                "Dni",
                "Nombre",
                "Apellidos",
                "F. Nacimiento",
                "Profesion",
                "Estado"
        });

        for (Cliente listadoCliente : listaClientesMysql) {

            modeloTablaCliente.addRow(new Object[]{
                    listadoCliente.getDni(),
                    listadoCliente.getNombre(),
                    listadoCliente.getApellidos(),
                    listadoCliente.getFechaNacimiento(),
                    listadoCliente.getProfesion(),
                    listadoCliente.getEstado()


            });

            visitasTabla.setModel(modeloTablaCliente);

        }

    }

}
