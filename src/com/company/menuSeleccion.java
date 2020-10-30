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
  private JButton inscripcionesButton;
  private int tipoDB;


    public menuSeleccion(int tipoDB){
        add(panel2);
        setTitle("Seleccione Un Apartado");
        setSize(630, 300);
        setResizable(false);
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
                visitaVentana ventana = new visitaVentana(tipoDB);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
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

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                // Crear la nueva venta para abrir
                principalVentana pv = new principalVentana(){};
                pv.setLocationRelativeTo(null);
                pv.setVisible(true);
                // Cerrar ventana actual
                dispose();

            }
        });

      inscripcionesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Inscripciones ventana = new Inscripciones(tipoDB);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

        }
      });
    }

}
