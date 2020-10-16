package com.company;

import Clases.Cliente;
import Clases.Empleado;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

  public clienteVentana(int opcion) {

    add(jpanel1);

    setTitle("Gestion de Clientes");
    setSize(570, 300);

    /**
     *
     * BOTON GUARDAR CLIENTE
     *
     */
    guardarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        String dni = tfDni.getText();
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String nacimiento = tfNacimiento.getText();
        String profesion = tfProfesion.getText();
        //todo validaciones

        //Se crea el objeto para enviar a guardar
        Cliente cliente = new Cliente(dni,nombre,apellidos,nacimiento,profesion,"alta");

        switch (opcion) {
          case 1://OPCION DB4O
            String mensaje = ModeloCliente.guardar(cliente);

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
     * BOTON ELIMINAR CLIENTE
     *
     */
    eliminarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //Se recogen los datos de los input para posible validación
        String dni = tfDni.getText();
        //todo validaciones

        //Se crea el objeto para enviar a eliminar
        Cliente cliente = new Cliente(dni, null, null, null,
                null, null, null);

        switch (opcion) {
          case 1://OPCION DB4O
            String mensaje = ModeloCliente.eliminar(cliente);
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
  }
}
