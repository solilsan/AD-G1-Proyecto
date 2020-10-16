package com.company;

import Clases.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private JTextField tfFecha;
  private JPanel tablaPanel;
  private JTable table1;
  private JPanel formularioPanel;
  private JTextField tfId;
  private JComboBox cbEmpleado;

  public visitaVentana(int opcion) {
    add(panel1);

    setTitle("Gestión de Visitas");

    setSize(670, 500);


    /**
     *
     * BOTON GUARDAR VISITA
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        boolean correcto = true;

        try {

          int id = Integer.parseInt(tfId.getText());
          float coste = Float.parseFloat(tfCoste.getText());

        } catch (NumberFormatException numberFormatException) {
          correcto = false;
        }

        String nombre = tfNombre.getText();
        String aforo = tfAforo.getText();
        String partida = tfPartida.getText();
        String curso = tfCurso.getText();
        String tematica = tfTematica.getText();
        //Empleado empleado = (Empleado) cbEmpleado.getItemAt(1);
        String fechaHora = tfFecha.getText();

      }
    });
  }
}
