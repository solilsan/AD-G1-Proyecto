package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuSeleccion extends JFrame{
    private JButton clientesButton;
    private JButton visitasButton;
    private JButton empleadosButton;
    private JButton metadatosButton;
    private JPanel panel1;
    private JPanel panel2;
    private int tipoDB;


    public menuSeleccion(int tipoDB){
        add(panel2);
        setTitle("Seleccione Un Apartado");
        setSize(400, 200);
        this.tipoDB = tipoDB;

        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empleadoVentana ventana = new empleadoVentana(tipoDB);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });

        visitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Añadir una ventana de visitas
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clienteVentana ventana = new clienteVentana(tipoDB);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });

        metadatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metadatosVentana ventana = new metadatosVentana(tipoDB);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });

    }
}
