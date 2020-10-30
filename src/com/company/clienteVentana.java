package com.company;

import Clases.Cliente;
import DB4O.ModeloCliente;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;
import SQLite.ControladorCliente;

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
  private JLabel lAltaBaja;
  private JTable tablaClientes;
  private JPanel jpTabla;
  private JButton bLimpiar;

  private String estadoCliente;

  public clienteVentana(int opcion) {

    add(jpanel1);

    setTitle("Gestion de Clientes");
    setSize(570, 300);
    jpTabla.setVisible(false);

    /**
     *
     * BOTON GUARDAR CLIENTE
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //todo validaciones

        if (tfDni.getText().isEmpty() || tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfNacimiento.getText().isEmpty() || tfProfesion.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);

        } else {

          String dni = tfDni.getText();
          String nombre = tfNombre.getText();
          String apellidos = tfApellidos.getText();
          String nacimiento = tfNacimiento.getText();
          String profesion = tfProfesion.getText();

          //Se crea el objeto para enviar a guardar
          Cliente cliente = new Cliente(dni, nombre, apellidos, nacimiento, profesion, "alta");

          switch (opcion) {
            case 1://OPCION DB4O
              String mensaje = ModeloCliente.guardar(cliente);

              tfDni.setText("");
              tfNombre.setText("");
              tfApellidos.setText("");
              tfNacimiento.setText("");
              tfProfesion.setText("");
              lAltaBaja.setText("");

              JOptionPane.showMessageDialog(null, mensaje, "Informacion Guardado",
                  JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2://OPCION SQLITE
              Boolean exito = ControladorCliente.insertCliente(cliente);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Informacion Guardada.", "Informacion Guardado",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                        "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                    JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              String mysqlMensaje = MySqlControladorCliente.insert(mysqlConn, cliente);

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                  JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Cliente guardado")) {

                tfDni.setText("");
                tfNombre.setText("");
                tfApellidos.setText("");
                tfNacimiento.setText("");
                tfProfesion.setText("");
                lAltaBaja.setText("");

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
     * BOTON ELIMINAR CLIENTE
     *
     */
    eliminarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //todo validaciones

        if (tfDni.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Introduce un DNI.", "Informacion Eliminado",
              JOptionPane.INFORMATION_MESSAGE);

        } else {


          //Se recogen los datos de los input para borrar
          String dni = tfDni.getText();

          //Se crea el objeto para enviar a eliminar
          Cliente cliente = new Cliente(dni, null, null, null,
              null, "baja", null);


          switch (opcion) {
            case 1://OPCION DB4O
              String mensaje = ModeloCliente.eliminar(cliente);

              tfDni.setText("");
              tfNombre.setText("");
              tfApellidos.setText("");
              tfNacimiento.setText("");
              tfProfesion.setText("");
              lAltaBaja.setText("");

              JOptionPane.showMessageDialog(null, mensaje, "Informacion Eliminado",
                  JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2://OPCION SQLITE
              boolean exito = ControladorCliente.darBajaCliente(cliente);


              if (exito) {
                JOptionPane.showMessageDialog(null, "Cliente dado de baja.", "Informacion Baja",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                        "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                    JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              String mysqlMensaje = MySqlControladorCliente.deleteWithDni(mysqlConn, cliente.getDni());

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                  JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Cliente eliminado")) {

                tfDni.setText("");

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
     * BOTON ACTUALIZAR CLIENTE
     *
     */
    actualizarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {


        //todo validaciones

        if (tfDni.getText().isEmpty() || tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfNacimiento.getText().isEmpty() || tfProfesion.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
              JOptionPane.INFORMATION_MESSAGE);

        } else {

          //Se recogen los datos de los input para posible actualizacion
          String dni = tfDni.getText();
          String nombre = tfNombre.getText();
          String apellidos = tfApellidos.getText();
          String nacimiento = tfNacimiento.getText();
          String profesion = tfProfesion.getText();

          //Se crea el objeto para enviar a actualizar
          Cliente cliente = new Cliente(dni, nombre, apellidos, nacimiento, profesion, estadoCliente);

          switch (opcion) {
            case 1://OPCION DB4O
              String mensaje = ModeloCliente.actualiza(cliente);

              JOptionPane.showMessageDialog(null, mensaje, "Informacion Actualizado",
                  JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2://OPCION SQLITE
              boolean exito = ControladorCliente.updateCliente(cliente);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Cliente Actualizado con Exito.", "Informacion Actualizada",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                        "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                    JOptionPane.WARNING_MESSAGE);
              }

              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              String mysqlMensaje = MySqlControladorCliente.updateWithDni(mysqlConn, cliente);

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                  JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Cliente actualizado")) {

                tfDni.setText("");
                tfNombre.setText("");
                tfApellidos.setText("");
                tfNacimiento.setText("");
                tfProfesion.setText("");

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

        }

      }
    });

    /**
     *
     * BOTON BUSCAR CLIENTE POR DNI
     *
     */
    buscarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //todo validaciones

        if (tfDni.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Introduce un DNI.", "Informacion Actualizado",
              JOptionPane.INFORMATION_MESSAGE);

        } else {

          //Se recogen los datos de los input para posible busqueda
          String dni = tfDni.getText();

          switch (opcion) {
            case 1://OPCION DB4O
              Cliente c = ModeloCliente.buscar(tfDni.getText());

              if (c != null) {

                tfDni.setText(c.getDni());
                tfNombre.setText(c.getNombre());
                tfApellidos.setText(c.getApellidos());
                tfNacimiento.setText(c.getFechaNacimiento());
                tfProfesion.setText(c.getProfesion());
                lAltaBaja.setText(c.getEstado());
                actualizarButton.setEnabled(true);

              } else {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Información.",
                    JOptionPane.INFORMATION_MESSAGE);
              }

              break;

            case 2://OPCION SQLITE
              ArrayList<Cliente> cli = ControladorCliente.selectWhereDni(dni);

              if (cli.size() > 0) {
                Cliente cl = cli.get(0);

                tfDni.setText(cl.getDni());
                tfNombre.setText(cl.getNombre());
                tfApellidos.setText(cl.getApellidos());
                tfNacimiento.setText(cl.getFechaNacimiento());
                tfProfesion.setText(cl.getProfesion());
                estadoCliente = cl.getEstado();

                actualizarButton.setEnabled(true);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado el cliente", "Error",
                    JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              Cliente cliente = MySqlControladorCliente.selectWithDni(mysqlConn, dni);

              if (cliente != null) {

                tfDni.setText(cliente.getDni());
                tfNombre.setText(cliente.getNombre());
                tfApellidos.setText(cliente.getApellidos());

                String dia = cliente.getFechaNacimiento().substring(8, 10);
                String mes = cliente.getFechaNacimiento().substring(5, 7);
                String anno = cliente.getFechaNacimiento().substring(0, 4);

                tfNacimiento.setText(dia + "/" + mes + "/" + anno);
                tfProfesion.setText(cliente.getProfesion());
                estadoCliente = cliente.getEstado();

                actualizarButton.setEnabled(true);

              } else {

                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Información.",
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
     * BOTON PARA LIMPIAR EL FORMULARIO
     *
     */
    bLimpiar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tfDni.setText("");
        tfNombre.setText("");
        tfApellidos.setText("");
        tfNacimiento.setText("");
        tfProfesion.setText("");
        lAltaBaja.setText("");
      }
    });

    /**
     *
     * BOTON GENERA LISTADO DE CLIENTES
     *
     */
    listadoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //Se recogen los datos de los input para posible actualizacion
        String dni = tfDni.getText();
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String nacimiento = tfNacimiento.getText();
        String profesion = tfProfesion.getText();

        Cliente cli = new Cliente(dni, nombre, apellidos, nacimiento, profesion, null);

        DefaultTableModel modeloTablaCliente = new DefaultTableModel();

        modeloTablaCliente.setColumnIdentifiers(new Object[]{
          "Dni",
          "Nombre",
          "Apellidos",
          "F. Nacimiento",
          "Profesion",
          "Estado"
        });

        switch (opcion) {

          case 1:
            //todo opcion DB4O listado todos los clientes
            List<Cliente> listadoClientes = ModeloCliente.mostrar();

            if (listadoClientes.size() > 0)
              jpTabla.setVisible(true);

            for (Cliente listadoCliente : listadoClientes) {

              modeloTablaCliente.addRow(new Object[]{
                  listadoCliente.getDni(),
                  listadoCliente.getNombre(),
                  listadoCliente.getApellidos(),
                  listadoCliente.getFechaNacimiento(),
                  listadoCliente.getProfesion(),
                  listadoCliente.getEstado()


              });

              tablaClientes.setModel(modeloTablaCliente);

            }

            break;

          case 2:
            ArrayList<Cliente> listaClientes = ControladorCliente.selectAll();


            if (listaClientes.size() > 0)
              jpTabla.setVisible(true);

            for (Cliente listadoCliente : listaClientes) {

              modeloTablaCliente.addRow(new Object[]{
                  listadoCliente.getNombre(),
                  listadoCliente.getApellidos(),
                  listadoCliente.getFechaNacimiento(),
                  listadoCliente.getProfesion(),
                  listadoCliente.getEstado()


              });

              tablaClientes.setModel(modeloTablaCliente);

            }
            break;

          case 3:
            //todo opcion mysql listado todos los clientes
            break;
        }

      }
    });


    /**
     *
     * RELLENAR FORMULARIO AL CLICKAR EN TABLA
     *
     */
    tablaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (tablaClientes.getSelectedRow() >= 0) {
          String dni = tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString();


          switch (opcion) {

            case 1:
              //peticion a la base de datos rellena datos DB4O
              Cliente c = ModeloCliente.buscar(dni);

              if (c != null) {
                tfDni.setText(c.getDni());
                tfNombre.setText(c.getNombre());
                tfApellidos.setText(c.getApellidos());
                tfNacimiento.setText(c.getFechaNacimiento());
                tfProfesion.setText(c.getProfesion());
                lAltaBaja.setText(c.getEstado());

                actualizarButton.setEnabled(true);

              } else {
                JOptionPane.showMessageDialog(null,
                    "No se ha encontrado el cliente", "Error", JOptionPane.WARNING_MESSAGE);
              }


              break;

            case 2:
              ArrayList<Cliente> cli = ControladorCliente.selectWhereDni(dni);

              if (cli.size() > 0) {
                Cliente cl = cli.get(0);

                tfDni.setText(cl.getDni());
                tfNombre.setText(cl.getNombre());
                tfApellidos.setText(cl.getApellidos());
                tfNacimiento.setText(cl.getFechaNacimiento());
                tfProfesion.setText(cl.getProfesion());
                estadoCliente = cl.getEstado();

                actualizarButton.setEnabled(true);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado el cliente", "Error",
                    JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3:
              //TODO: Añadir peticion a la base de datos
              break;
          }

        }
      }
    });

  }

}
