package com.company;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;
import DB4O.ModeloVisita;
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

        setSize(700, 500);

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

                    DefaultTableModel modeloTinscritos = new DefaultTableModel();

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
                            //TODO Se utiliza modeloTablaClientes para rellenar las filas de los apuntados a esa visita
                            break;

                        case 3://MYSQL
                            //TODO Se utiliza modeloTablaClientes para rellenar las filas de los apuntados a esa visita
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

                    DefaultTableModel modeloTablaClientes = new DefaultTableModel();

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

                                //todo apuntar este cliente a la visita y actualizar la tabla apuntados

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
                            //todo apuntar este cliente a la visita y actualizar la tabla apuntados
                            break;

                        case 3://MYSQL
                            //todo apuntar este cliente a la visita y actualizar la tabla apuntados
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
        DefaultTableModel modeloTablaClientes = new DefaultTableModel();

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
                //todo opcion mysql listado todos los clientes de alta
                break;
        }


    }

    /**
     * Función que carga la lista de visitas activas en la tabla (TABLA IZQUIERDA)
     */
    private void cargarVisitas(int opcion) {

        DefaultTableModel modeloTablaVisitas = new DefaultTableModel();

        modeloTablaVisitas.setColumnIdentifiers(new Object[]{
                "Id",
                "Nombre",
                "Aforo",
                "Partida",
                "Temática",
                "Coste",
                "Fecha"
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
                            listadoVisita.getFecha_hora()
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
                            listadoVisita.getFecha_hora()
                    });

                    visitasTabla.setModel(modeloTablaVisitas);

                }
                break;

            case 3:
                //todo opcion mysql listado todos los Visitas
                break;
        }


    }

}
