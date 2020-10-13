package com.company;

import MySql.MySqlVentanaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class principalVentana extends JFrame{
  private JPanel panel1;
  private JButton agenciaDB4OButton;
  private JButton agenciaSQLiteButton;
  private JButton agenciaMySqlButton;

  public principalVentana (){

    add(panel1);

    setTitle("Lista Agencias");

    setSize(400, 300);

    agenciaDB4OButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    agenciaSQLiteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    agenciaMySqlButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        MySqlVentanaPrincipal msvp = new MySqlVentanaPrincipal(){};

        // Crear la nueva venta para abrir
        msvp.setLocationRelativeTo(null);
        msvp.setVisible(true);
        // Cerrar ventana actual
        dispose();

      }
    });

    this.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        System.exit(0);
      }
    });

  }

}
