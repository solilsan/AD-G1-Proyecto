package com.company;

import Clases.Visita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class empleadoVisitas extends JFrame{
    private JPanel jpVisitas;
    private JTable visitasTabla;

    public empleadoVisitas(ArrayList<Visita> listaVisitasMysql) {

        add(jpVisitas);

        setTitle("Lista visitas del empleado");

        setSize(1000,500);

        DefaultTableModel modeloTablaVisitas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTablaVisitas.setColumnIdentifiers(new Object[]{
                "Id",
                "Nombre",
                "Aforo",
                "Partida",
                "Temática",
                "Coste",
                "Fecha",
                "Empleado"
        });

        if (listaVisitasMysql != null && listaVisitasMysql.size() > 0) {

            for (Visita listadoVisita : listaVisitasMysql) {

                modeloTablaVisitas.addRow(new Object[]{
                        listadoVisita.getId(),
                        listadoVisita.getNombre(),
                        listadoVisita.getNmaxCli(),
                        listadoVisita.getPuntoPartida(),
                        listadoVisita.getTematica(),
                        listadoVisita.getCoste(),
                        listadoVisita.getFecha_hora(),
                        listadoVisita.getEmpleado().getNombre()
                });

            }

            visitasTabla.setModel(modeloTablaVisitas);

        }

    }
}
