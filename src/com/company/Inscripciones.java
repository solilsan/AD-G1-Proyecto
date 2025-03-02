package com.company;

import Clases.Cliente;
import javax.swing.JScrollPane;
import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;
import DB4O.ModeloVisita;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;
import MySql.MySqlControladorVisita;
import SQLite.ControladorCliente;
import SQLite.ControladorEmpleado;
import SQLite.ControladorVisita;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.Connection;
import java.sql.SQLException;
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

    setSize(1000,500);

    cargarVisitas(opcion);
    cargarClientes(opcion);

    /**
     *
     * Rellena la lista de apuntados al clicar en la visita
     *
     */
    visitasTabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {

        if (visitasTabla.getSelectedRow() >= 0) {
          String id = visitasTabla.getValueAt(visitasTabla.getSelectedRow(), 0).toString();

          DefaultTableModel modeloTinscritos = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };

          modeloTinscritos.setColumnIdentifiers(new Object[]{
              "Dni",
              "Nombre",
              "Apellidos",
              "Fecha de Nacimiento",
              "Profesion"
          });


          switch (opcion) {

            case 1:

              int identificador = -1;

              try {
                identificador = Integer.parseInt(id);
              } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
              }

              //peticion a la base de datos rellena datos DB4O
              Visita v = ModeloVisita.buscar(identificador);

              if (v.getClientes().size() > 0) {

                for (Cliente cliente : v.getClientes()) {

                  modeloTinscritos.addRow(new Object[]{
                      cliente.getDni(),
                      cliente.getNombre(),
                      cliente.getApellidos(),
                      cliente.getFechaNacimiento(),
                      cliente.getProfesion()
                  });

                  inscritosTabla.setModel(modeloTinscritos);
                }

              } else {
                JOptionPane.showMessageDialog(null,
                    "No se ha encontrado ningún cliente apuntado", "Error", JOptionPane.WARNING_MESSAGE);
              }


              break;

            case 2://SQLITE
              int idVisita = -1;

              try {
                idVisita = Integer.parseInt(id);
              } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
              }

              //peticion a la base de datos rellena datos DB4O
              ArrayList<Visita> visit = ControladorVisita.selectWhere(idVisita);

              if (visit.get(0).getClientes().size() > 0) {

                for (Cliente cliente : visit.get(0).getClientes()) {

                  modeloTinscritos.addRow(new Object[]{
                      cliente.getDni(),
                      cliente.getNombre(),
                      cliente.getApellidos(),
                      cliente.getFechaNacimiento(),
                      cliente.getProfesion()
                  });

                  inscritosTabla.setModel(modeloTinscritos);
                }
              }
              break;

            case 3://MYSQL
              Connection mysqlConn = MySqlConexion.connection();

              int idVisitaMysql = -1;

              try {
                idVisitaMysql = Integer.parseInt(id);
              } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
              }

              ArrayList<Cliente> listaClientesMysqlEnVisita = MySqlControladorVisita.selectAllClientesEnVisita(mysqlConn, idVisitaMysql);

              if (listaClientesMysqlEnVisita != null && listaClientesMysqlEnVisita.size() != 0) {

                for (Cliente cliente : listaClientesMysqlEnVisita) {

                  modeloTinscritos.addRow(new Object[]{
                          cliente.getDni(),
                          cliente.getNombre(),
                          cliente.getApellidos(),
                          cliente.getFechaNacimiento(),
                          cliente.getProfesion()
                  });

                  inscritosTabla.setModel(modeloTinscritos);
                }
              } else {

                inscritosTabla.setModel(modeloTinscritos);

                JOptionPane.showMessageDialog(null, "No existen clientes en esta visita", "Información.",
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
    });


    /**
     *
     * Funcion que se encarga de apuntar a los clientes a una visita
     *
     */
    apuntarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (disponiblesTabla.getSelectedRow() >= 0 && visitasTabla.getSelectedRow() >= 0) {

          String dni = disponiblesTabla.getValueAt(disponiblesTabla.getSelectedRow(), 0).toString();
          String id = visitasTabla.getValueAt(visitasTabla.getSelectedRow(), 0).toString();

          DefaultTableModel modeloTablaClientes = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };

          modeloTablaClientes.setColumnIdentifiers(new Object[]{
              "Dni",
              "Nombre",
              "Apellidos",
              "Fecha de Nacimiento",
              "Profesion"
          });

          switch (opcion) {

            case 1://DB4O

              try {

                int identificador = Integer.parseInt(id);

                Visita visitaGuardada = ModeloVisita.apuntar(identificador, dni);

                for (Cliente listadoCliente : visitaGuardada.getClientes()) {

                  modeloTablaClientes.addRow(new Object[]{
                          listadoCliente.getDni(),
                          listadoCliente.getNombre(),
                          listadoCliente.getApellidos(),
                          listadoCliente.getFechaNacimiento(),
                          listadoCliente.getProfesion()
                  });

                  inscritosTabla.setModel(modeloTablaClientes);
                }
                /*//Se apunta el cliente a la visita
                visita.getClientes().add(cliente);

                //Se regostra la visita en el cliente
                cliente.getVisitas().add(visita);*/


                //ModeloCliente.guardar(cliente);

                JOptionPane.showMessageDialog(null,
                        "Apuntado!",
                        "Informacion Apuntado",
                        JOptionPane.INFORMATION_MESSAGE);


              } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
              }

              break;

            case 2://SQLITE
              int idVisita = Integer.parseInt(id);

              boolean exito = ControladorVisita.addClienteVisita(dni, idVisita);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Cliente Añadido con Exito.", "Informacion Actualizada",
                        JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                                "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                        JOptionPane.WARNING_MESSAGE);
              }

              /// CODIGO DE SUPERVIVENCIA
              Visita vis = ControladorVisita.selectWhere(idVisita).get(0);


              for (Cliente listCliente : vis.getClientes()) {

                modeloTablaClientes.addRow(new Object[]{
                        listCliente.getDni(),
                        listCliente.getNombre(),
                        listCliente.getApellidos(),
                        listCliente.getFechaNacimiento(),
                        listCliente.getProfesion()
                });

                inscritosTabla.setModel(modeloTablaClientes);
              }

              break;

            case 3://MYSQL
              //todo apuntar este cliente a la visita y actualizar la tabla apuntados

              Connection mysqlConn = MySqlConexion.connection();

              int idVisitaMysql = Integer.parseInt(id);

              boolean completa = MySqlControladorVisita.nTotalCliVisita(mysqlConn, idVisitaMysql);

              boolean existe = MySqlControladorVisita.comprobarClienteEnVisita(mysqlConn, idVisitaMysql, dni);

              if (!completa) {

                if (existe) {

                  JOptionPane.showMessageDialog(null, "Este cliente ya esta apuntado.", "Información.",
                          JOptionPane.INFORMATION_MESSAGE);

                } else {

                  String mysqlMensaje = MySqlControladorVisita.insertClienteVisita(mysqlConn, idVisitaMysql, dni);

                  JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                          JOptionPane.INFORMATION_MESSAGE);

                  ArrayList<Cliente> listaClientesMysqlEnVisita = MySqlControladorVisita.selectAllClientesEnVisita(mysqlConn, idVisitaMysql);

                  if (listaClientesMysqlEnVisita != null && listaClientesMysqlEnVisita.size() != 0) {

                    for (Cliente cliente : listaClientesMysqlEnVisita) {

                      modeloTablaClientes.addRow(new Object[]{
                              cliente.getDni(),
                              cliente.getNombre(),
                              cliente.getApellidos(),
                              cliente.getFechaNacimiento(),
                              cliente.getProfesion()
                      });

                      inscritosTabla.setModel(modeloTablaClientes);
                    }
                  }

                }

              }
              else {
                JOptionPane.showMessageDialog(null, "Visita completa, no se admiten mas clientes.", "Información.",
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


        } else {
          JOptionPane.showMessageDialog(null,
              "Debes seleccionar al menos una visita y un cliente para apuntarlo.",
              "Informacion Apuntado",
              JOptionPane.INFORMATION_MESSAGE);
        }

      }

    });

    /**
     *
     * BOTON DESAPUNTAR CLIENTE
     *
     */
    desapuntarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (inscritosTabla.getSelectedRow() >= 0 && visitasTabla.getSelectedRow() >= 0) {

          String dni = inscritosTabla.getValueAt(inscritosTabla.getSelectedRow(), 0).toString();
          String id = visitasTabla.getValueAt(visitasTabla.getSelectedRow(), 0).toString();

          DefaultTableModel modeloTablaClientes = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };

          modeloTablaClientes.setColumnIdentifiers(new Object[]{
              "Dni",
              "Nombre",
              "Apellidos",
              "Fecha de Nacimiento",
              "Profesion"
          });

          switch (opcion) {

            case 1://DB4O
              try {

                int identificador = Integer.parseInt(id);

                Visita visitasDB4O = ModeloVisita.desapuntar(identificador, dni);

                //SI NO TIENE CLIENTES QUE CREE UNO VACIO PARA BORRAR LA TABLA
                if (visitasDB4O.getClientes().size() < 1 ){
                  visitasDB4O.getClientes().add(new Cliente());
                }

                for (Cliente listadoCliente : visitasDB4O.getClientes()) {

                  modeloTablaClientes.addRow(new Object[]{
                      listadoCliente.getDni(),
                      listadoCliente.getNombre(),
                      listadoCliente.getApellidos(),
                      listadoCliente.getFechaNacimiento(),
                      listadoCliente.getProfesion()
                  });

                  inscritosTabla.setModel(modeloTablaClientes);
                }


                JOptionPane.showMessageDialog(null,
                    "Desapuntado!",
                    "Informacion Apuntado",
                    JOptionPane.INFORMATION_MESSAGE);


              } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
              }

              break;

            case 2://SQLITE
              int idVisita = Integer.parseInt(id);

              boolean exito = ControladorVisita.eliminaClienteVisita(dni, idVisita);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Cliente Eliminado con Exito.", "Informacion Actualizada",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                        "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                    JOptionPane.WARNING_MESSAGE);
              }

              /// CODIGO DE SUPERVIVENCIA
              Visita vis = ControladorVisita.selectWhere(idVisita).get(0);


              for (Cliente listCliente : vis.getClientes()) {

                modeloTablaClientes.addRow(new Object[]{
                    listCliente.getDni(),
                    listCliente.getNombre(),
                    listCliente.getApellidos(),
                    listCliente.getFechaNacimiento(),
                    listCliente.getProfesion()
                });

                inscritosTabla.setModel(modeloTablaClientes);
              }

              break;

            case 3://MYSQL
              Connection mysqlConn = MySqlConexion.connection();

              int idVisitaMysql = Integer.parseInt(id);

              boolean borrado = MySqlControladorVisita.deleteClienteVisita(mysqlConn, idVisitaMysql, dni);

              if (borrado) {

                JOptionPane.showMessageDialog(null, "Cliente Eliminado con Exito.", "Informacion Actualizada",
                        JOptionPane.INFORMATION_MESSAGE);

                ArrayList<Cliente> listaClientesMysqlEnVisita = MySqlControladorVisita.selectAllClientesEnVisita(mysqlConn, idVisitaMysql);

                if (listaClientesMysqlEnVisita != null && listaClientesMysqlEnVisita.size() != 0) {

                  for (Cliente cliente : listaClientesMysqlEnVisita) {

                    modeloTablaClientes.addRow(new Object[]{
                            cliente.getDni(),
                            cliente.getNombre(),
                            cliente.getApellidos(),
                            cliente.getFechaNacimiento(),
                            cliente.getProfesion()
                    });

                    inscritosTabla.setModel(modeloTablaClientes);
                  }
                }
                else {

                  inscritosTabla.setModel(modeloTablaClientes);

                }
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
          JOptionPane.showMessageDialog(null,
              "Debes seleccionar al menos una visita y un cliente para apuntarlo.",
              "Informacion Apuntado",
              JOptionPane.INFORMATION_MESSAGE);
        }

      }
    });
  }//Fin del constructor


  /**
   * Función que carga la lista de clientes activas en la tabla (TABLA ABAJO DERECHA)
   */
  private void cargarClientes(int opcion) {
    DefaultTableModel modeloTablaClientes = new DefaultTableModel(){
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    modeloTablaClientes.setColumnIdentifiers(new Object[]{
        "Dni",
        "Nombre",
        "Apellidos",
        "Fecha de Nacimiento",
        "Profesion"

    });

    switch (opcion) {
      case 1://DB4O
        List<Cliente> listadoClientes = ModeloCliente.mostrarClientesAlta();

        if (listadoClientes.size() > 0)
          jpDisponibles.setVisible(true);

        for (Cliente listadoCliente : listadoClientes) {

          modeloTablaClientes.addRow(new Object[]{
              listadoCliente.getDni(),
              listadoCliente.getNombre(),
              listadoCliente.getApellidos(),
              listadoCliente.getFechaNacimiento(),
              listadoCliente.getProfesion()
          });

          disponiblesTabla.setModel(modeloTablaClientes);

        }
        break;

      case 2:
        List<Cliente> listClientes = ControladorCliente.selectActivos();

        if (listClientes.size() > 0)
          jpDisponibles.setVisible(true);

        for (Cliente listadoCliente : listClientes) {

          modeloTablaClientes.addRow(new Object[]{
              listadoCliente.getDni(),
              listadoCliente.getNombre(),
              listadoCliente.getApellidos(),
              listadoCliente.getFechaNacimiento(),
              listadoCliente.getProfesion()
          });

          disponiblesTabla.setModel(modeloTablaClientes);
        }
        break;

      case 3:
        Connection mysqlConn = MySqlConexion.connection();

        ArrayList<Cliente> listaClientesMysql = MySqlControladorCliente.selectAllActivos(mysqlConn);

        if (listaClientesMysql != null) {

          jpDisponibles.setVisible(true);

          for (Cliente listadoCliente : listaClientesMysql) {

            modeloTablaClientes.addRow(new Object[]{
                    listadoCliente.getDni(),
                    listadoCliente.getNombre(),
                    listadoCliente.getApellidos(),
                    listadoCliente.getFechaNacimiento(),
                    listadoCliente.getProfesion(),
                    listadoCliente.getEstado()


            });

            disponiblesTabla.setModel(modeloTablaClientes);

          }

        } else {

          JOptionPane.showMessageDialog(null, "No existen clientes", "Información.",
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

  /**
   * Función que carga la lista de visitas activas en la tabla (TABLA IZQUIERDA)
   */
  private void cargarVisitas(int opcion) {

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


    switch (opcion) {
      case 1://DB4O
        List<Visita> listadoVisitas = ModeloVisita.mostrarVisitasAlta();

        if (listadoVisitas.size() > 0)
          jpVisitas.setVisible(true);

        for (Visita listadoVisita : listadoVisitas) {

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

          visitasTabla.setModel(modeloTablaVisitas);

        }
        break;

      case 2:
        //todo opcion sqlite listado todos las Visitas
        List<Visita> listVisitas = ControladorVisita.selectActivos();

        if (listVisitas.size() > 0)
          jpVisitas.setVisible(true);

        for (Visita listadoVisita : listVisitas) {

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

          visitasTabla.setModel(modeloTablaVisitas);

        }
        break;

      case 3:
        Connection mysqlConn = MySqlConexion.connection();

        ArrayList<Visita> listaVisitasMysql = MySqlControladorVisita.selectAllActivas(mysqlConn);

        if (listaVisitasMysql != null) {

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

}
