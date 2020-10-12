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

  public empleadoVentana(int opcion) {

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

        //Se recogen los datos de los input para posible validación
        String dni = tfDni.getText();
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String nacimiento = tfNacimiento.getToolTipText();
        String contratacion = tfContratacion.getText();
        String nacionalidad = tfNacionalidad.getText();
        String cargo = tfCargo.getText();
        String contrasinal = new String(jfContrasinal.getPassword());
        //todo validaciones

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
            //todo opcion sqlite guardado
            break;

          case 3://OPCION MYSQL
            //todo opcion mysql guardado
            break;
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
        //todo validaciones

        //Se crea el objeto para enviar a eliminar
        Empleado empleado = new Empleado(dni, null, null, null,
            null, null, null, null, null);

        switch (opcion) {
          case 1://OPCION DB4O
            String mensaje = ModeloEmpleado.eliminar(empleado);
            JOptionPane.showMessageDialog(null, mensaje, "Informacion Eliminado",
                JOptionPane.INFORMATION_MESSAGE);
            break;

          case 2://OPCION SQLITE
            //todo opcion sqlite borrado
            break;

          case 3://OPCION MYSQL
            //todo opcion mysql borrado
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
        //Se recogen los datos de los input para posible validación
        String dni = tfDni.getText();
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String nacimiento = tfNacimiento.getToolTipText();
        String contratacion = tfContratacion.getText();
        String nacionalidad = tfNacionalidad.getText();
        String cargo = tfCargo.getText();
        String contrasinal = new String(jfContrasinal.getPassword());
        //todo validaciones

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
            //todo opcion sqlite guardado
            break;

          case 3://OPCION MYSQL
            //todo opcion mysql guardado
            break;
        }


      }
    });
  }
}
