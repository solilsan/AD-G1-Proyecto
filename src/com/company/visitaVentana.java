package com.company;

import javax.swing.*;

public class visitaVentana extends JFrame{
  private JPanel panel1;
  private JButton guardarButton;
  private JButton eliminarButton;
  private JButton actualizarButton;
  private JButton listadoButton;
  private JButton buscarButton;
  private JTextField tfNombre;
  private JTextField tfAforo;
  private JTextField tfPartida;
  private JTextField tfCurso;
  private JTextField tfTematica;
  private JTextField tfCoste;
  private JTextField tfEmpleado;
  private JTextField tfFecha;

  public visitaVentana() {
    add(panel1);

    setTitle("Gestión de Visitas");
    setSize(630, 300);
  }
}
