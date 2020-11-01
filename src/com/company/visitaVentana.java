package com.company;

import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;
import DB4O.ModeloVisita;
import MySql.MySqlConexion;
import MySql.MySqlControladorEmpleado;
import MySql.MySqlControladorVisita;
import SQLite.ControladorEmpleado;
import SQLite.ControladorVisita;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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

          /*System.out.println(tfId.getText() + "\n" +
                  tfNombre.getText() + "\n" +
                  tfAforo.getText() + "\n" +
                  tfPartida.getText() + "\n" +
                  tfCurso.getText() + "\n" +
                  tfTematica.getText() + "\n" +
                  tfCoste.getText() + "\n" +
                  cbEmpleado.getSelectedIndex() + "\n" +
                  tfFecha.getText());*/


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


          if (!falloTipoDato) {

            //error al hacer la conversion de tipo de dato
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
                JOptionPane.INFORMATION_MESSAGE);

          } else {//AQUI HA SALIDO BIEN Y SE PUEDE HACER EL GUARDADO

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
                Boolean exito = ControladorVisita.insertVisita(visita);

                if (exito) {
                  JOptionPane.showMessageDialog(null, "Visita Guardada con Exito.", "Informacion Guardada",
                      JOptionPane.INFORMATION_MESSAGE);
                } else {
                  JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                          "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                      JOptionPane.WARNING_MESSAGE);
                }
                break;

              case 3://OPCION MYSQL
                Connection mysqlConn = MySqlConexion.connection();

                String mysqlMensaje = MySqlControladorVisita.insert(mysqlConn, visita);

                JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                        JOptionPane.INFORMATION_MESSAGE);

                if (mysqlMensaje.equals("Visita guardada")) {



                }

                try {
                  assert mysqlConn != null;
                  mysqlConn.close();
                } catch (SQLException throwables) {
                  throwables.printStackTrace();
                }

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
        boolean error = false;
        int idVisita = -1;

        if (tfId.getText().isEmpty()) {
          error = true;
        } else {
          try {
            idVisita = Integer.parseInt(tfId.getText());
          } catch (NumberFormatException numberFormatException) {
            error = true;
          }
        }

        if (!error) {

          switch (opcion) {

            case 1://DB4O
              String mensaje = ModeloVisita.eliminar(idVisita);
              JOptionPane.showMessageDialog(null, mensaje, "Informacion Eliminado",
                  JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2:
              Boolean exito = ControladorVisita.darBajaVisita(idVisita);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Visita Eliminada con Exito.", "Informacion Guardada",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                        "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                    JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3:
              Connection mysqlConn = MySqlConexion.connection();

              String mysqlMensaje = MySqlControladorVisita.deleteWithDni(mysqlConn, idVisita);

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                      JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Visita eliminada")) {

                tfId.setText("");

              }

              try {
                assert mysqlConn != null;
                mysqlConn.close();
              } catch (SQLException throwables) {
                throwables.printStackTrace();
              }

              break;

          }

        } else {
          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);
        }

      }
    });

    /**
     *
     * BOTON ACTUALIZAR VISITA
     *
     */
    actualizarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (tfId.getText().isEmpty() ||
            tfNombre.getText().isEmpty() ||
            tfAforo.getText().isEmpty() ||
            tfPartida.getText().isEmpty() ||
            tfCurso.getText().isEmpty() ||
            tfTematica.getText().isEmpty() ||
            tfCoste.getText().isEmpty() ||
            tfFecha.getText().isEmpty() ||
            cbEmpleado.getSelectedIndex() == -1) {

          //error campos obligatorios no estan rellenos
          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);

        } else {
          Boolean error = false;
          int idVisita = -999;
          int aforo = -999;
          float coste = -999;

          try {
            idVisita = Integer.parseInt(tfId.getText());
            aforo = Integer.parseInt(tfAforo.getText());
            coste = Float.parseFloat(tfCoste.getText());
          } catch (NumberFormatException numberFormatException) {
            error = true;
          }

          if (!error) {
            Visita visit = new Visita(idVisita, tfNombre.getText(),
                aforo, tfPartida.getText(), tfCurso.getText(), tfTematica.getText(), coste,
                "alta", tfFecha.getText(), empleados.get(cbEmpleado.getSelectedIndex() - 1));


            switch (opcion) {
              case 1://DB4O
                String mensaje = ModeloVisita.actualiza(visit);

                JOptionPane.showMessageDialog(null, mensaje, "Informacion Actualizado",
                    JOptionPane.INFORMATION_MESSAGE);
                break;

              case 2://SQLITE
                boolean exito = ControladorVisita.updateVisita(visit);

                if (exito) {
                  JOptionPane.showMessageDialog(null, "Visita Actualizada con Exito.", "Informacion Guardada",
                      JOptionPane.INFORMATION_MESSAGE);
                } else {
                  JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                          "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                      JOptionPane.WARNING_MESSAGE);
                }
                break;

              case 3://MYSQL
                Connection mysqlConn = MySqlConexion.connection();

                String mysqlMensaje = MySqlControladorVisita.updateWithId(mysqlConn, visit);

                JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                        JOptionPane.INFORMATION_MESSAGE);

                if (mysqlMensaje.equals("Visita actualizado")) {

                  actualizarButton.setEnabled(false);

                }

                try {
                  assert mysqlConn != null;
                  mysqlConn.close();
                } catch (SQLException throwables) {
                  throwables.printStackTrace();
                }

                break;
            }
          } else {
            //error campos obligatorios no estan rellenos
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
                JOptionPane.INFORMATION_MESSAGE);
          }

        }


      }
    });

    /**
     *
     * BOTON LISTAR VISITAS
     *
     */
    listadoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO: completar
        DefaultTableModel modeloTablaVisita = new DefaultTableModel();

        modeloTablaVisita.setColumnIdentifiers(new Object[]{
            "Id",
            "Nombre",
            "Aforo Maximo",
            "Punto Partida",
            "Curso Academico",
            "Tematica",
            "Coste",
            "Estado",
            "Fecha y Hora",
            "Empleado"
        });

        switch (opcion) {

          case 1://BD4O

            List<Visita> listVisitas = ModeloVisita.mostrar();

            for (Visita listadoVisita : listVisitas) {

              modeloTablaVisita.addRow(new Object[]{
                  listadoVisita.getId(),
                  listadoVisita.getNombre(),
                  listadoVisita.getNmaxCli(),
                  listadoVisita.getPuntoPartida(),
                  listadoVisita.getCursoAcademico(),
                  listadoVisita.getTematica(),
                  listadoVisita.getCoste(),
                  listadoVisita.getEstado(),
                  listadoVisita.getFecha_hora(),
                  listadoVisita.getEmpleado().getNombre()
              });

            }

            table1.setModel(modeloTablaVisita);

            break;

          case 2:
            ArrayList<Visita> listadoVisitas = ControladorVisita.selectAll();


            for (Visita listadoVisita : listadoVisitas) {

              modeloTablaVisita.addRow(new Object[]{
                  listadoVisita.getId(),
                  listadoVisita.getNombre(),
                  listadoVisita.getNmaxCli(),
                  listadoVisita.getPuntoPartida(),
                  listadoVisita.getCursoAcademico(),
                  listadoVisita.getTematica(),
                  listadoVisita.getCoste(),
                  listadoVisita.getEstado(),
                  listadoVisita.getFecha_hora(),
                  listadoVisita.getEmpleado().getNombre()
              });

            }

            table1.setModel(modeloTablaVisita);
            break;

          case 3:
            Connection mysqlConn = MySqlConexion.connection();

            ArrayList<Visita> listaVisitasMysql = MySqlControladorVisita.selectAll(mysqlConn);

            if (listaVisitasMysql != null) {

              for (Visita listadoVisita : listaVisitasMysql) {

                modeloTablaVisita.addRow(new Object[]{
                        listadoVisita.getId(),
                        listadoVisita.getNombre(),
                        listadoVisita.getNmaxCli(),
                        listadoVisita.getPuntoPartida(),
                        listadoVisita.getCursoAcademico(),
                        listadoVisita.getTematica(),
                        listadoVisita.getCoste(),
                        listadoVisita.getEstado(),
                        listadoVisita.getFecha_hora(),
                        listadoVisita.getEmpleado().getNombre()
                });

              }

              table1.setModel(modeloTablaVisita);

            } else {

              JOptionPane.showMessageDialog(null, "No existen visitas", "Información.",
                      JOptionPane.INFORMATION_MESSAGE);

            }

            try {
              assert mysqlConn != null;
              mysqlConn.close();
            } catch (SQLException throwables) {
              throwables.printStackTrace();
            }
            break;


        }


      }
    });

    /**
     *
     * BOTON BUSCAR VISITA
     *
     */
    buscarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO: completar
        Boolean error = false;
        if (tfId.getText().isEmpty()) {
          error = true;
        } else {
          int idVisita = -999;
          try {
            idVisita = Integer.parseInt(tfId.getText());
          } catch (NumberFormatException numberFormatException) {
            error = true;
          }

          if (!error && idVisita != -999) {
            switch (opcion) {

              case 1://DB4O
                Visita visita = ModeloVisita.buscar(idVisita);

                if (visita != null) {
                  tfId.setText(Integer.toString(visita.getId()));
                  tfNombre.setText(visita.getNombre());
                  tfAforo.setText(Integer.toString(visita.getNmaxCli()));
                  tfPartida.setText(visita.getPuntoPartida());
                  tfCurso.setText(visita.getCursoAcademico());
                  tfTematica.setText(visita.getTematica());
                  tfCoste.setText(Float.toString(visita.getCoste()));
                  tfFecha.setText(visita.getFecha_hora());
                  int pos = posicionEmpleadoCombo(visita.getEmpleado().getDni());
                  cbEmpleado.setSelectedIndex(pos);
                }

                break;

              case 2://SQLITE
                ArrayList<Visita> visit = ControladorVisita.selectWhere(idVisita);

                if (visit.size() > 0) {
                  Visita vi = visit.get(0);

                  tfId.setText(String.valueOf(vi.getId()));
                  tfNombre.setText(vi.getNombre());
                  tfAforo.setText(String.valueOf(vi.getNmaxCli()));
                  tfPartida.setText(vi.getPuntoPartida());
                  tfCurso.setText(vi.getCursoAcademico());
                  tfTematica.setText(vi.getTematica());
                  tfCoste.setText(String.valueOf(vi.getCoste()));
                  tfFecha.setText(vi.getFecha_hora());
                  cbEmpleado.setSelectedIndex(posicionEmpleadoCombo(vi.getEmpleado().getDni()));

                } else {
                  JOptionPane.showMessageDialog(null, "No se ha encontrado el empleado", "Error",
                      JOptionPane.WARNING_MESSAGE);
                }
                break;

              case 3://MYSQL
                Connection mysqlConn = MySqlConexion.connection();

                Visita visitaMysql = MySqlControladorVisita.selectWithId(mysqlConn, idVisita);

                if (visitaMysql != null) {

                  tfNombre.setText(visitaMysql.getNombre());
                  tfAforo.setText(String.valueOf(visitaMysql.getNmaxCli()));
                  tfPartida.setText(visitaMysql.getPuntoPartida());
                  tfCurso.setText(visitaMysql.getCursoAcademico());
                  tfTematica.setText(visitaMysql.getTematica());
                  tfCoste.setText(String.valueOf(visitaMysql.getCoste()));
                  tfFecha.setText(visitaMysql.getFecha_hora().substring(8, 10) + "/" + visitaMysql.getFecha_hora().substring(5, 7) + "/" + visitaMysql.getFecha_hora().substring(0, 4) + " " + visitaMysql.getFecha_hora().substring(11, 16));
                  cbEmpleado.setSelectedIndex(posicionEmpleadoCombo(visitaMysql.getEmpleado().getDni()));

                  actualizarButton.setEnabled(true);

                } else {

                  JOptionPane.showMessageDialog(null, "Visita no encontrada", "Información.",
                          JOptionPane.INFORMATION_MESSAGE);

                }

                try {
                  assert mysqlConn != null;
                  mysqlConn.close();
                } catch (SQLException throwables) {
                  throwables.printStackTrace();
                }

                break;
            }
          }
        }

        // ERROR
        if (error) {
          //error campos obligatorios no estan rellenos
          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);
        }

      }
    });

    /**
     *
     * RELLENAR FORMULARIO AL CLICKAR EN TABLA
     *
     */
    table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int idVisita = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());

        switch (opcion) {
          case 1://DB4O
            Visita visitaDB4O = ModeloVisita.buscar(idVisita);

            if (visitaDB4O != null){
              tfId.setText(Integer.toString(visitaDB4O.getId()));
              tfNombre.setText(visitaDB4O.getNombre());
              tfAforo.setText(String.valueOf(visitaDB4O.getNmaxCli()));
              tfPartida.setText(visitaDB4O.getPuntoPartida());
              tfCurso.setText(visitaDB4O.getCursoAcademico());
              tfTematica.setText(visitaDB4O.getTematica());
              tfCoste.setText(String.valueOf(visitaDB4O.getCoste()));
              tfFecha.setText(visitaDB4O.getFecha_hora());
              cbEmpleado.setSelectedIndex(posicionEmpleadoCombo(visitaDB4O.getEmpleado().getDni()));
            }

            break;

          case 2://SQLITE
            ArrayList<Visita> visit = ControladorVisita.selectWhere(idVisita);

            if (visit.size() > 0) {
              Visita vi = visit.get(0);

              tfId.setText(String.valueOf(vi.getId()));
              tfNombre.setText(vi.getNombre());
              tfAforo.setText(String.valueOf(vi.getNmaxCli()));
              tfPartida.setText(vi.getPuntoPartida());
              tfCurso.setText(vi.getCursoAcademico());
              tfTematica.setText(vi.getTematica());
              tfCoste.setText(String.valueOf(vi.getCoste()));
              tfFecha.setText(vi.getFecha_hora());
              cbEmpleado.setSelectedIndex(posicionEmpleadoCombo(vi.getEmpleado().getDni()));
            }
            break;

          case 3://MYSQL
            Connection mysqlConn = MySqlConexion.connection();

            Visita visitaMysql = MySqlControladorVisita.selectWithId(mysqlConn, idVisita);

            if (visitaMysql != null) {

              tfId.setText(String.valueOf(visitaMysql.getId()));
              tfNombre.setText(visitaMysql.getNombre());
              tfAforo.setText(String.valueOf(visitaMysql.getNmaxCli()));
              tfPartida.setText(visitaMysql.getPuntoPartida());
              tfCurso.setText(visitaMysql.getCursoAcademico());
              tfTematica.setText(visitaMysql.getTematica());
              tfCoste.setText(String.valueOf(visitaMysql.getCoste()));
              tfFecha.setText(visitaMysql.getFecha_hora().substring(8, 10) + "/" + visitaMysql.getFecha_hora().substring(5, 7) + "/" + visitaMysql.getFecha_hora().substring(0, 4) + " " + visitaMysql.getFecha_hora().substring(11, 16));
              cbEmpleado.setSelectedIndex(posicionEmpleadoCombo(visitaMysql.getEmpleado().getDni()));

            }

            try {
              assert mysqlConn != null;
              mysqlConn.close();
            } catch (SQLException throwables) {
              throwables.printStackTrace();
            }

            break;
        }

      }
    });


    //Se añade la opcion de poder dejar en blanco el combo
    cbEmpleado.addItem(null);

    //Se rellena el combo con dni nombre y apellidos de los empleados
    for (Empleado empleado : empleados) {
      cbEmpleado.addItem(empleado.getDni() + " " + empleado.getNombre() + " " + empleado.getApellidos());
    }

    //por defecto se deja el combo a la opcion vacio para que no salga por defecto la 1 opcion
    cbEmpleado.setSelectedItem(null);
  }

  /**
   * Funcion que se encarga de generar la lista para rellenar el desplegable
   */
  private void rellenaEmpleados(int opcion) {


    switch (opcion) {

      case 1://DB4O
        empleados = ModeloEmpleado.mostrarEmpleadosAlta();
        break;

      case 2://SQLITE
        empleados = ControladorEmpleado.selectEmpActivo();
        break;

      case 3://MYSQL
        Connection mysqlConn = MySqlConexion.connection();

        empleados = MySqlControladorEmpleado.selectAll(mysqlConn);

        try {
          assert mysqlConn != null;
          mysqlConn.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
        break;
    }

  }


  /**
   * @param dni recibe el dni String
   * @return devuelve la posicion int de ese dni
   */
  public int posicionEmpleadoCombo(String dni) {

    for (int i = 0; i < empleados.size(); i++) {
      if (empleados.get(i).getDni().equals(dni))
        return i + 1;
    }

    return -1;
  }

}

