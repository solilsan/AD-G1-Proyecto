package com.company;

import javax.swing.*;

public class empleadoVentana extends JFrame{
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
    private JPasswordField jfConstrasinal;

    public empleadoVentana (){

        add(panel1);

        setTitle("Gestión de Empleados");
        setSize(630, 300);

    }

}
