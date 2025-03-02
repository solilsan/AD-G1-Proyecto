package com.company;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;
import MySql.MySqlControladorEmpleado;
import SQLite.ControladorCliente;
import SQLite.ControladorEmpleado;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class empleadoVentana extends JFrame {

  private JPanel panel1;
  private JTextField tfDni;
  private JTextField tfNombre;
  private JTextField tfApellidos;
  private JTextField tfNacimiento;
  private JButton guardarButton;
  private JButton eliminarButton;
  private JButton actualizarButton;
  private JButton listadoButton;
  private JTextField tfContratacion;
  private JTextField tfNacionalidad;
  private JTextField tfCargo;
  private JPasswordField jfContrasinal;
  private JTable listado;
  private JPanel formularioPanel;
  private JPanel botoneraPanel;
  private JPanel tablaPanel;
  private JButton buscarButton;
  private JButton limpiarButton;
  private JLabel lEstado;

  public empleadoVentana(int opcion) {

    add(panel1);

    setTitle("Gestión de Empleados");

    setSize(700, 500);

    tablaPanel.setVisible(true);

    actualizarButton.setEnabled(false);

    /**
     *
     * BOTON GUARDAR
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //validaciones
        if (tfDni.getText().isEmpty() || tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfNacimiento.getText().isEmpty() || tfContratacion.getText().isEmpty() || tfNacionalidad.getText().isEmpty() || tfCargo.getText().isEmpty() || jfContrasinal.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Guardado",
            JOptionPane.INFORMATION_MESSAGE);

        } else {

          //Se recogen los datos de los input para posible validación
          String dni = tfDni.getText();
          String nombre = tfNombre.getText();
          String apellidos = tfApellidos.getText();
          String nacimiento = tfNacimiento.getText();
          String contratacion = tfContratacion.getText();
          String nacionalidad = tfNacionalidad.getText();
          String cargo = tfCargo.getText();
          String contrasinal = new String(jfContrasinal.getPassword());

          //Se crea el objeto para enviar a guardar
          Empleado empleado = new Empleado(dni, nombre, apellidos, nacimiento, contratacion,
            nacionalidad, cargo, contrasinal, "alta");

          switch (opcion) {
            case 1://OPCION DB4O
              String mensaje = ModeloEmpleado.guardar(empleado);

              JOptionPane.showMessageDialog(null, mensaje, "Informacion Guardado",
                JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2://OPCION SQLITE
              // Guardamos el empleado
              Boolean exito = ControladorEmpleado.insertEmpleado(empleado);

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

              String mysqlMensaje = MySqlControladorEmpleado.insert(mysqlConn, empleado);

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Empleado guardado")) {

                tfDni.setText("");
                tfNombre.setText("");
                tfApellidos.setText("");
                tfNacimiento.setText("");
                tfContratacion.setText("");
                tfNacionalidad.setText("");
                tfCargo.setText("");
                jfContrasinal.setText("");

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
     * BOTON ELIMINAR
     *
     */
    eliminarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //Se recogen los datos de los input para posible validación
        String dni = tfDni.getText();

        //Se crea el objeto para enviar a eliminar
        Empleado empleado = new Empleado(dni, null, null, null,
          null, null, null, null, "baja");

        switch (opcion) {
          case 1://OPCION DB4O
            String mensaje = ModeloEmpleado.eliminar(empleado);
            JOptionPane.showMessageDialog(null, mensaje, "Informacion Eliminado",
              JOptionPane.INFORMATION_MESSAGE);
            break;

          case 2://OPCION SQLITE
            boolean exito = ControladorEmpleado.darBajaEmpleado(empleado);

            if (exito) {
              JOptionPane.showMessageDialog(null, "Empleado dado de baja.", "Informacion Baja",
                JOptionPane.INFORMATION_MESSAGE);
            } else {
              JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                  "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                JOptionPane.WARNING_MESSAGE);
            }
            break;

          case 3://OPCION MYSQL
            Connection mysqlConn = MySqlConexion.connection();

            String mysqlMensaje = MySqlControladorEmpleado.deleteWithDni(mysqlConn, empleado.getDni());

            JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                    JOptionPane.INFORMATION_MESSAGE);

            if (mysqlMensaje.equals("Empleado eliminado")) {

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
    });

    /**
     *
     * BOTON ACTUALIZAR
     *
     */
    actualizarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //validaciones
        if (tfDni.getText().isEmpty() ||
          tfNombre.getText().isEmpty() ||
          tfApellidos.getText().isEmpty() ||
          tfNacimiento.getText().isEmpty() ||
          tfContratacion.getText().isEmpty() ||
          tfNacionalidad.getText().isEmpty() ||
          tfCargo.getText().isEmpty()
        ) {

          JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Informacion Actualizado",
            JOptionPane.INFORMATION_MESSAGE);

        } else {

          //Se recogen los datos de los input para posible validación
          String dni = tfDni.getText();
          String nombre = tfNombre.getText();
          String apellidos = tfApellidos.getText();
          String nacimiento = tfNacimiento.getText();
          String contratacion = tfContratacion.getText();
          String nacionalidad = tfNacionalidad.getText();
          String cargo = tfCargo.getText();
          String contrasinal = new String(jfContrasinal.getPassword());

          //Se crea el objeto para enviar a actulizar
          Empleado empleado = new Empleado(dni, nombre, apellidos, nacimiento, contratacion,
            nacionalidad, cargo, contrasinal, "alta");

          switch (opcion) {
            case 1://OPCION DB4O
              String mensaje = ModeloEmpleado.actualiza(empleado);

              JOptionPane.showMessageDialog(null, mensaje, "Informacion Actualizar",
                JOptionPane.INFORMATION_MESSAGE);
              break;

            case 2://OPCION SQLITE
              boolean exito = ControladorEmpleado.updateEmpleado(empleado);

              if (exito) {
                JOptionPane.showMessageDialog(null, "Empleado Actualizado con Exito.", "Informacion Actualizada",
                  JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha guardado la Informacion.\n" +
                    "Revisa que los datos tengan el formato adecuado", "Informacion Guardado",
                  JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              String mysqlMensaje = MySqlControladorEmpleado.updateWithDni(mysqlConn, empleado);

              JOptionPane.showMessageDialog(null, mysqlMensaje, "Información.",
                      JOptionPane.INFORMATION_MESSAGE);

              if (mysqlMensaje.equals("Empleado actualizado")) {

                tfDni.setText("");
                tfNombre.setText("");
                tfApellidos.setText("");
                tfNacimiento.setText("");
                tfContratacion.setText("");
                tfNacionalidad.setText("");
                tfCargo.setText("");
                lEstado.setText("");
                jfContrasinal.setText("");

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
     * BOTON LISTAR TODOS LOS EMPLEADOS
     *
     */
    listadoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        DefaultTableModel modeloTablaEmpleado = new DefaultTableModel(){
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

        modeloTablaEmpleado.setColumnIdentifiers(new Object[]{
          "Dni",
          "Nombre",
          "Apellidos",
          "F. Nacimiento",
          "F. Contrato",
          "Nacionalidad",
          "Cargo",
          "estado"
        });

        switch (opcion) {
          case 1://DB4O
            List<Empleado> listadoEmpleados = ModeloEmpleado.mostrar();

            if (listadoEmpleados.size() > 0)
              tablaPanel.setVisible(true);

            for (Empleado listadoEmpleado : listadoEmpleados) {

              modeloTablaEmpleado.addRow(new Object[]{
                listadoEmpleado.getDni(),
                listadoEmpleado.getNombre(),
                listadoEmpleado.getApellidos(),
                listadoEmpleado.getFechaNacimiento(),
                listadoEmpleado.getFechaContratacion(),
                listadoEmpleado.getNacionalidad(),
                listadoEmpleado.getCargo(),
                listadoEmpleado.getEstado()
              });

              listado.setModel(modeloTablaEmpleado);

            }
            break;

          case 2://SQLITE
            ArrayList<Empleado> listaEmpleados = ControladorEmpleado.selectAll();


            if (listaEmpleados.size() > 0)
              tablaPanel.setVisible(true);

            for (Empleado listadoEmpleado : listaEmpleados) {

              modeloTablaEmpleado.addRow(new Object[]{
                      listadoEmpleado.getDni(),
                      listadoEmpleado.getNombre(),
                      listadoEmpleado.getApellidos(),
                      listadoEmpleado.getFechaNacimiento(),
                      listadoEmpleado.getFechaContratacion(),
                      listadoEmpleado.getNacionalidad(),
                      listadoEmpleado.getCargo(),
                      listadoEmpleado.getEstado()
              });

              listado.setModel(modeloTablaEmpleado);

            }
            break;

          case 3://MYSQL
            Connection mysqlConn = MySqlConexion.connection();

            ArrayList<Empleado> listaEmpleadosMysql = MySqlControladorEmpleado.selectAll(mysqlConn);

            if (listaEmpleadosMysql != null) {

              tablaPanel.setVisible(true);

              for (Empleado listadoEmpleado : listaEmpleadosMysql) {

                modeloTablaEmpleado.addRow(new Object[]{
                        listadoEmpleado.getDni(),
                        listadoEmpleado.getNombre(),
                        listadoEmpleado.getApellidos(),
                        listadoEmpleado.getFechaNacimiento(),
                        listadoEmpleado.getFechaContratacion(),
                        listadoEmpleado.getNacionalidad(),
                        listadoEmpleado.getCargo(),
                        listadoEmpleado.getEstado()
                });

                listado.setModel(modeloTablaEmpleado);

              }

            } else {

              JOptionPane.showMessageDialog(null, "No existen empleados", "Información.",
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
     * BOTON BUSCAR EMPLEADO CONCRETO
     *
     */
    buscarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //validaciones
        if (tfDni.getText().isEmpty()) {

          JOptionPane.showMessageDialog(null, "Introduce un DNI.", "Informacion Actualizado",
            JOptionPane.INFORMATION_MESSAGE);

        } else {

          //Se recogen los datos de los input para posible busqueda
          String dni = tfDni.getText();

          actualizarButton.setEnabled(true);

          switch (opcion) {
            case 1://OPCION DB4O
              Empleado emp = ModeloEmpleado.buscar(tfDni.getText());

              if (e != null) {

                tfDni.setText(emp.getDni());
                tfNombre.setText(emp.getNombre());
                tfApellidos.setText(emp.getApellidos());
                tfNacimiento.setText(emp.getFechaNacimiento());
                tfContratacion.setText(emp.getFechaContratacion());
                tfNacionalidad.setText(emp.getNacionalidad());
                tfCargo.setText(emp.getCargo());
                lEstado.setText(emp.getEstado());

                actualizarButton.setEnabled(true);

              } else {

                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Información.",
                  JOptionPane.INFORMATION_MESSAGE);

              }

              break;

            case 2://OPCION SQLITE
              ArrayList<Empleado> emple =  ControladorEmpleado.selectByDni(dni);

              if (emple.size() > 0){
                emp = emple.get(0);

                tfDni.setText(emp.getDni());
                tfNombre.setText(emp.getNombre());
                tfApellidos.setText(emp.getApellidos());
                tfNacimiento.setText(emp.getFechaNacimiento());
                tfContratacion.setText(emp.getFechaContratacion());
                tfNacionalidad.setText(emp.getNacionalidad());
                tfCargo.setText(emp.getCargo());
                lEstado.setText(emp.getEstado());

                actualizarButton.setEnabled(true);
              }else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado el empleado", "Error",
                        JOptionPane.WARNING_MESSAGE);
              }

              break;

            case 3://OPCION MYSQL

              Connection mysqlConn = MySqlConexion.connection();

              Empleado empleado = MySqlControladorEmpleado.selectWithDni(mysqlConn, dni);

              if (empleado != null) {

                tfDni.setText(empleado.getDni());
                tfNombre.setText(empleado.getNombre());
                tfApellidos.setText(empleado.getApellidos());
                tfNacimiento.setText(empleado.getFechaNacimiento().substring(8, 10) + "/" + empleado.getFechaNacimiento().substring(5, 7) + "/" + empleado.getFechaNacimiento().substring(0, 4));
                tfContratacion.setText(empleado.getFechaContratacion().substring(8, 10) + "/" + empleado.getFechaContratacion().substring(5, 7) + "/" + empleado.getFechaContratacion().substring(0, 4));
                tfNacionalidad.setText(empleado.getNacionalidad());
                tfCargo.setText(empleado.getCargo());
                jfContrasinal.setText(empleado.getPassword());
                lEstado.setText(empleado.getEstado());

                actualizarButton.setEnabled(true);

              } else {

                JOptionPane.showMessageDialog(null, "Empleado no encontrado", "Información.",
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
     * BOTON QUE LIMPIA LA VENTANA
     *
     */
    limpiarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tfDni.setText("");
        tfNombre.setText("");
        tfApellidos.setText("");
        tfNacimiento.setText("");
        tfContratacion.setText("");
        tfNacionalidad.setText("");
        tfCargo.setText("");
        lEstado.setText("");
        jfContrasinal.setText("");

        actualizarButton.setEnabled(false);
      }
    });

    /**
     *
     * RELLENAR FORMULARIO AL CLICKAR EN TABLA
     *
     */
    listado.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (listado.getSelectedRow() >= 0) {
          String dni = listado.getValueAt(listado.getSelectedRow(), 0).toString();

          switch (opcion) {

            case 1:
              //peticion a la base de datos rellena datos DB4O
              Empleado em = ModeloEmpleado.buscar(dni);

              if (e != null) {
                tfDni.setText(em.getDni());
                tfNombre.setText(em.getNombre());
                tfApellidos.setText(em.getApellidos());
                tfNacimiento.setText(em.getFechaNacimiento());
                tfContratacion.setText(em.getFechaContratacion());
                tfNacionalidad.setText(em.getNacionalidad());
                tfCargo.setText(em.getCargo());
                lEstado.setText(em.getEstado());

                actualizarButton.setEnabled(true);

              } else {
                JOptionPane.showMessageDialog(null,
                  "No se ha encontrado el Empleado", "Error", JOptionPane.WARNING_MESSAGE);
              }

              break;

            case 2:
              ArrayList<Empleado> emple = ControladorEmpleado.selectByDni(dni);

              if (emple.size() > 0) {
                Empleado emp = emple.get(0);

                tfDni.setText(emp.getDni());
                tfNombre.setText(emp.getNombre());
                tfApellidos.setText(emp.getApellidos());
                tfNacimiento.setText(emp.getFechaNacimiento());
                tfContratacion.setText(emp.getFechaContratacion());
                tfNacionalidad.setText(emp.getNacionalidad());
                tfCargo.setText(emp.getCargo());

                actualizarButton.setEnabled(true);
              } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado el empleado", "Error",
                        JOptionPane.WARNING_MESSAGE);
              }
              break;

            case 3:
              Connection mysqlConn = MySqlConexion.connection();

              Empleado empleado = MySqlControladorEmpleado.selectWithDni(mysqlConn, dni);

              if (empleado != null) {

                tfDni.setText(empleado.getDni());
                tfNombre.setText(empleado.getNombre());
                tfApellidos.setText(empleado.getApellidos());
                tfNacimiento.setText(empleado.getFechaNacimiento().substring(8, 10) + "/" + empleado.getFechaNacimiento().substring(5, 7) + "/" + empleado.getFechaNacimiento().substring(0, 4));
                tfContratacion.setText(empleado.getFechaContratacion().substring(8, 10) + "/" + empleado.getFechaContratacion().substring(5, 7) + "/" + empleado.getFechaContratacion().substring(0, 4));
                tfNacionalidad.setText(empleado.getNacionalidad());
                tfCargo.setText(empleado.getCargo());
                jfContrasinal.setText(empleado.getPassword());
                lEstado.setText(empleado.getEstado());

                actualizarButton.setEnabled(true);

              } else {

                JOptionPane.showMessageDialog(null, "Empleado no encontrado", "Información.",
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

    listado.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

          String dni = listado.getValueAt(listado.getSelectedRow(), 0).toString();

          Connection mysqlConn = MySqlConexion.connection();

          ArrayList<Visita> listaVisitasMysql = MySqlControladorEmpleado.selectAllApuntadas(mysqlConn, dni);

          if (listaVisitasMysql != null && listaVisitasMysql.size() > 0) {

            clienteVisitas ventana = new clienteVisitas(listaVisitasMysql);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

          } else {

            JOptionPane.showMessageDialog(null, "Este empleado no esta en ninguna visita.", "Información.",
                    JOptionPane.INFORMATION_MESSAGE);

          }

          try {
            assert mysqlConn != null;
            mysqlConn.close();
          } catch (SQLException throwables) {
            throwables.printStackTrace();
          }

        }
      }
    });

  }
}
