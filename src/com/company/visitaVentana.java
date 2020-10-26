package com.company;

import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloEmpleado;
import DB4O.ModeloVisita;
import MySql.MySqlConexion;
import MySql.MySqlControladorEmpleado;
import SQLite.ControladorEmpleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class visitaVentana extends JFrame {
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
  private JComboBox<String> cbEmpleado;


  //variable global de lista de empleados
  private static List<Empleado> empleados = new ArrayList<>();

  public visitaVentana(int opcion) {

    add(panel1);

    setTitle("Gestión de Visitas");

    setSize(670, 500);

    rellenaEmpleados(opcion);

    /**
     *
     * BOTON GUARDAR VISITA
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        int id = 0;
        float coste = 0;
        int aforo = 0;

        //SE VALIDA QUE NO ESTEN VACIOS LOS CAMPOS
        if (tfId.getText().isEmpty() ||
          tfNombre.getText().isEmpty() ||
          tfAforo.getText().isEmpty() ||
          tfPartida.getText().isEmpty() ||
          tfCurso.getText().isEmpty() ||
          tfTematica.getText().isEmpty() ||
          tfCoste.getText().isEmpty() ||
          cbEmpleado.getSelectedIndex() < 0 ||
          tfFecha.getText().isEmpty()) {

          //error campos obligatorios no estan rellenos
          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
            JOptionPane.INFORMATION_MESSAGE);

        } else {//campos estan rellenos

          boolean falloTipoDato = true;

          //SE VALIDA QUE EL TIPO DE DATO ES EL CORRECTO
          try {

            id = Integer.parseInt(tfId.getText());
            coste = Float.parseFloat(tfCoste.getText());
            aforo = Integer.parseInt(tfAforo.getText());

          } catch (NumberFormatException numberFormatException) {
            falloTipoDato = false;
          }

          //TODO validaciones

          if (falloTipoDato){

            //error al hacer la conversion de tipo de dato
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);

          }else {//AQUI HA SALIDO BIEN Y SE PUEDE HACER EL GUARDADO

            String nombre = tfNombre.getText();
            String partida = tfPartida.getText();
            String curso = tfCurso.getText();
            String tematica = tfTematica.getText();
            Empleado empleado = empleados.get(cbEmpleado.getSelectedIndex() - 1);
            String fechaHora = tfFecha.getText();

            //ESTE ES EL OBJETO VISITA A GUARDAR
            Visita visita = new Visita(id, nombre, aforo, partida, curso, tematica, coste, "alta", fechaHora, empleado);

            switch (opcion) {

              case 1://OPCION DB4O
                String mensaje = ModeloVisita.guardar(visita);

                JOptionPane.showMessageDialog(null, mensaje, "Informacion Guardado",
                  JOptionPane.INFORMATION_MESSAGE);

                break;

              case 2://OPCION SQLITE
                //TODO Guarda visita SQLITE
                break;

              case 3://OPCION MYSQL
                //TODO Guarda visita MYSQL
                break;
            }

          }

        }
      }
    });

    /**
     *
     * BOTON QUE ELIMINA VISITA
     *
     */
    eliminarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void rellenaEmpleados(int opcion) {

    switch (opcion) {

      case 1://DB4O
        empleados = ModeloEmpleado.mostrarEmpleadosAlta();
        break;

      case 2://SQLITE
        //TODO listado de empleados para el combobox sqlite
        break;

      case 3://MYSQL
        //TODO listado de empleados para el combobox de mysql
        break;
    }

    //Se añade la opcion de poder dejar en blanco el combo
    cbEmpleado.addItem(null);

    //Se rellena el combo con dni nombre y apellidos de los empleados
    for (Empleado empleado : empleados) {
      cbEmpleado.addItem(empleado.getDni() + " " + empleado.getNombre() + " " + empleado.getApellidos());
    }

    //por defecto se deja el combo a la opcion vacio para que no salga por defecto la 1 opcion
    cbEmpleado.setSelectedItem(null);

  }

}
