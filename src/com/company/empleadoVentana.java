package com.company;

import Clases.Cliente;
import Clases.Empleado;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;
import MySql.MySqlControladorEmpleado;
import SQLite.ControladorCliente;
import SQLite.ControladorEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public empleadoVentana(int opcion) {

        add(panel1);

        setTitle("Gestión de Empleados");

        setSize(700, 500);

        /**
         *
         * BOTON GUARDAR
         *
         */
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                //todo validaciones

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
                        Boolean exito = ControladorEmpleado.darBajaEmpleado(empleado);

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
                        Boolean exito = ControladorEmpleado.updateEmpleado(empleado);

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
                        //todo opcion mysql guardado
                        break;
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

                DefaultTableModel modeloTablaCliente = new DefaultTableModel();

                modeloTablaCliente.setColumnIdentifiers(new Object[]{
                        "Dni",
                        "Nombre",
                        "Apellidos",
                        "F. Nacimiento",
                        "F. Contrato",
                        "Nacionalidad",
                        "Cargo"});

                List<Empleado> empleados = ModeloEmpleado.mostrar();


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


                //todo validaciones

                if (tfDni.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Introduce un DNI.", "Informacion Actualizado",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {

                    //Se recogen los datos de los input para posible busqueda
                    String dni = tfDni.getText();

                    switch (opcion) {
                        case 1://OPCION DB4O
                            Empleado emp = ModeloEmpleado.buscar(tfDni.getText());

                            if (e != null) {

                                tfDni.setText(emp.getDni());
                                tfNombre.setText(emp.getNombre());
                                tfApellidos.setText(emp.getApellidos());
                                tfNacimiento.setText(emp.getFechaNacimiento());

                                actualizarButton.setEnabled(true);

                            } else {

                                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Información.",
                                        JOptionPane.INFORMATION_MESSAGE);

                            }


                            break;

                        case 2://OPCION SQLITE
                            //todo Codigo sqlite
                            break;

                        case 3://OPCION MYSQL
                            //todo Codigo MYSQL
                            break;
                    }
                }
            }
        });
    }
}
