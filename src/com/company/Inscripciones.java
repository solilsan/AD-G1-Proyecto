package com.company;

import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloEmpleado;
import DB4O.ModeloVisita;
import SQLite.ControladorEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Inscripciones extends JFrame {
  private JPanel jpPrincipal;
  private JPanel jpVisitas;
  private JPanel jpApuntados;
  private JPanel jpDisponibles;
  private JTable visitasTabla;
  private JTable inscritosTabla;
  private JPanel jplstados;
  private JButton apuntarButton;
  private JButton desapuntarButton;
  private JTable disponiblesTabla;
  private JPanel jpbotonera;

  //variable global de lista de empleados
  private static List<Visita> visitas = new ArrayList<>();

  public Inscripciones(int opcion) {

    add(jpPrincipal);

    setTitle("Gestión de Empleados");

    setSize(700, 500);

    cargarVisitas(opcion);
  }

  private void cargarVisitas(int opcion) {

    DefaultTableModel modeloTablaEmpleado = new DefaultTableModel();

    modeloTablaEmpleado.setColumnIdentifiers(new Object[]{
        "Id",
        "Nombre",
        "Aforo",
        "Partida",
        "Temática",
        "Coste",
        "Fecha"
    });

    switch (opcion) {
      case 1://DB4O
        List<Visita> listadoVisitas = ModeloVisita.mostrarVisitasAlta();

        if (listadoVisitas.size() > 0)
          jpVisitas.setVisible(true);

        for (Visita listadoVisita : listadoVisitas) {

          modeloTablaEmpleado.addRow(new Object[]{
              listadoVisita.getNombre(),
              listadoVisita.getNmaxCli(),
              listadoVisita.getPuntoPartida(),
              listadoVisita.getTematica(),
              listadoVisita.getCoste(),
              listadoVisita.getFecha_hora()
          });

          visitasTabla.setModel(modeloTablaEmpleado);

        }
        break;

      case 2:
        //todo opcion sqlite listado todos las Visitas
        break;

      case 3:
        //todo opcion mysql listado todos los Visitas
        break;
    }


  }

}
