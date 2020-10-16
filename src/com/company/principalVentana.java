package com.company;

import MySql.MySqlConexion;
import MySql.MySqlControladorEmpleado;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class principalVentana extends JFrame{
  private JPanel panel1;
  private JRadioButton radioButtonDB4O;
  private JRadioButton radioButtonSQLite;
  private JRadioButton radioButtonMySql;
  private JButton loginButton;
  private JTextField textFieldDni;
  private JLabel JLabelNombre;
  private JLabel JLabelFechAbert;
  private JLabel JLabelDir;
  private JLabel JLabelTef;
  private JPanel PanelAgencias;
  private JPanel DatosAgencias;
  private JPanel Agencias;
  private JPanel PanelLogin;
  private JPanel DatosEmpleado;
  private JPanel BotonLogin;
  private JPanel PanelGeneral;
  private JPanel PanelTextoSA;
  private JPanel PanelError;
  private JLabel JLabelError;
  private JPasswordField passwordField1;
  private ButtonGroup bg1;

  public principalVentana (){

    add(panel1);

    setTitle("Lista PanelAgencias");
    setResizable(false);
    setSize(520, 300);

    radioButtonDB4O.setText("DB4O");
    radioButtonSQLite.setText("SQLite");
    radioButtonMySql.setText("MySql");

    bg1 = new ButtonGroup();

    bg1.add(radioButtonDB4O);
    bg1.add(radioButtonSQLite);
    bg1.add(radioButtonMySql);

    // Por defecto esta seleccionado la primera opción
    JLabelNombre.setText("DB4O");
    JLabelFechAbert.setText("10 Agosto 2020");
    JLabelDir.setText("Calle Alfonso Nº10");
    JLabelTef.setText("666999111");

    radioButtonDB4O.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JLabelError.setText("");
        JLabelNombre.setText("DB4O");
        JLabelFechAbert.setText("10 Agosto 2020");
        JLabelDir.setText("Calle Alfonso Nº10");
        JLabelTef.setText("666999111");
      }
    });

    radioButtonSQLite.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JLabelError.setText("");
        JLabelNombre.setText("SQLite");
        JLabelFechAbert.setText("8 Agosto 2020");
        JLabelDir.setText("Calle Prado Nº5");
        JLabelTef.setText("945172673");
      }
    });

    radioButtonMySql.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JLabelError.setText("");
        JLabelNombre.setText("MySql");
        JLabelFechAbert.setText("15 Agosto 2020");
        JLabelDir.setText("Calle Alcala Nº3");
        JLabelTef.setText("617283921");
      }
    });

    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        // DB4O Login
        if (radioButtonDB4O.isSelected()){

        }

        // SQLite Login
        if (radioButtonSQLite.isSelected()){

        }

        // MySql Login
        if (radioButtonMySql.isSelected()){

          if (textFieldDni.getText().isEmpty()) {
            JLabelError.setText("Introduce un Dni por favor.");
          }
          else if (passwordField1.getText().isEmpty()) {
            JLabelError.setText("Introduce una contraseña por favor.");
          }
          else {

            Connection connection = MySqlConexion.connection();

            if (MySqlControladorEmpleado.login(connection, textFieldDni.getText(), passwordField1.getText()).equals("")) {

              JLabelError.setText("Usuario o contraseña incorrectos.");

            }
            else {

              JLabelError.setText("");

              menuSeleccion menu = new menuSeleccion(3){};

              // Crear la nueva venta para abrir
              menu.setLocationRelativeTo(null);
              menu.setVisible(true);
              // Cerrar ventana actual
              dispose();

            }

            try {
              assert connection != null;
              connection.close();
            } catch (SQLException throwables) {
              throwables.printStackTrace();
            }

          }

        }

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
