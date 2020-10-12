package com.company;

import Clases.Empleado;
import DB4O.ModeloEmpleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

  public empleadoVentana() {

    add(panel1);

    setTitle("Gestión de Empleados");

    setSize(630, 300);

    /**
     *
     * BOTON GUARDAR
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //Se recogen los datos de los input para posible validacion
        String dni = tfDni.getText();
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String nacimiento = tfNacimiento.getToolTipText();
        String contratacion = tfContratacion.getText();
        String nacionalidad = tfNacionalidad.getText();
        String cargo = tfCargo.getText();
        String contrasinal = new String(jfContrasinal.getPassword());


        //Se crea el objeto para enviar a guardar
        Empleado empleado = new Empleado(dni, nombre, apellidos, nacimiento, contratacion,
            nacionalidad, cargo, contrasinal, "alta");

        //todo switch opcion elegida es DB4O
        String mensaje = ModeloEmpleado.guardar(empleado);
        JOptionPane.showMessageDialog(null,mensaje,"Informacion Guardado",
            JOptionPane.INFORMATION_MESSAGE);


        //todo opcion sqlite

        //todo opcion mysql
      }
    });
  }

}
