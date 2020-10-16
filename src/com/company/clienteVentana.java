package com.company;

import javax.swing.*;
import java.awt.*;

public class clienteVentana extends JFrame {
  private JButton guardarButton;
  private JButton eliminarButton;
  private JButton actualizarButton;
  private JButton listadoButton;
  private JButton buscarButton;
  private JTextField tfDni;
  private JTextField tfNombre;
  private JTextField tfApellidos;
  private JTextField tfNacimiento;
  private JTextField tfProfesion;
  private JPanel jpanel1;

  public clienteVentana(int tipoDB) {

    add(jpanel1);

    setTitle("Gestion de Clientes");
    setSize(630, 300);

  }
}
