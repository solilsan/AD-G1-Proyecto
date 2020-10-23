package com.company;

import Clases.Cliente;
import DB4O.ModeloCliente;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JTable table1;
    private JPanel jpTabla;
    private JButton bLimpiar;

    private String estadoCliente;

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
                            //todo opcion sqlite guardado
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
                            //todo opcion sqlite borrado
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
                            //todo opcion sqlite actulizar
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
                            //todo opcion sqlite buscar
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

                switch (opcion) {

                    case 1:
                        //todo opcion DB4O listado todos los clientes
                        List<Cliente> listadoClientes = ModeloCliente.mostrar();

                        Object[] nombreColumnas = {"Dni", "Nombre", "Apellidos", "F. Nacimiento", "Profesion", "Estado"};

                        Object[][] datos = new Object[listadoClientes.size()][5];

                        for (int i = 0; i < datos.length; i++) {

                            for (Cliente listadoCliente : listadoClientes) {

                                datos[i][5] = new String[]{
                                        listadoCliente.getDni(),
                                        listadoCliente.getNombre(),
                                        listadoCliente.getApellidos(),
                                        listadoCliente.getFechaNacimiento(),
                                        listadoCliente.getProfesion(),
                                        listadoCliente.getEstado()
                                };

                            }

                        }

                       /* JTabbedPane jTabbedPane = new JTabbedPane();
                        jTabbedPane.addTab("Listado", CoresAlternadas(defaultTableModel));
                        add(jTabbedPane);*/



                        DefaultTableModel defaultTableModel = new DefaultTableModel(datos, nombreColumnas) {
                            @Override
                            public Class getColumnClass(int column) {
                                return getValueAt(0, column).getClass();
                            }

                        };
                        break;

                    case 2:
                        //todo opcion sqlite listado todos los Clientes
                        break;

                    case 3:
                        //todo opcion mysql listado todos los clientes
                        break;
                }

            }
        });
    }
}
