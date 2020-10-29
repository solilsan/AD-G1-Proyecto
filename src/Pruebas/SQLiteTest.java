package Pruebas;

import Clases.Empleado;
import SQLite.ControladorCliente;
import Clases.Cliente;
import SQLite.ControladorEmpleado;
import SQLite.Metadatos;
import com.company.menuSeleccion;
import com.company.principalVentana;


import java.util.ArrayList;

public class SQLiteTest {

    public static void main(String[] args) {
        //insertCliente();
        //selectWhereCliente();
        //darClienteDeBaja();
       // selectAllClientes();
       // darClienteDeAlta();
        // actualizarUnoWhere();
        //insertEmpleado();
        //selectAllEmpleados();
        menuSeleccion pv = new menuSeleccion(1);
        //principalVentana pv = new principalVentana();
        pv.setLocationRelativeTo(null);
        pv.setVisible(true);
        //getmetadatos();
    }

    public static void getmetadatos(){
        Metadatos.getMetadatos();
    }


    public static void selectAllClientes(){
        ArrayList<Cliente> clientes = ControladorCliente.selectAll();

        // Comprobamos que no el array list no esta vacio
        if (!clientes.isEmpty()){
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("\nDNI: " + clientes.get(i).getDni() +
                        "\nNombre: " + clientes.get(i).getNombre() +
                        "\nApellido: " + clientes.get(i).getApellidos() +
                        "\nFecha Nacimiento: " + clientes.get(i).getFechaNacimiento() +
                        "\nProfesion: " +  clientes.get(i).getProfesion() +
                        "\nEstado: " +  clientes.get(i).getEstado());
            }
        }else{
            System.out.println("[SQLite - Select All] La tabla esta vacia, o ha ocurrido un error.");
        }
    }

    public static void insertCliente(){
        ControladorCliente.insertCliente(new Cliente("123asd","pepe","dfd","2020-03-03","una profesion","activo", null));

    }

    public static void selectWhereCliente(){
        ArrayList<Cliente> clientes = ControladorCliente.selectWhereDni("dfd");

        // Comprobamos que no el array list no esta vacio
        if (!clientes.isEmpty()){
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("\nDNI: " + clientes.get(i).getDni() +
                        "\nNombre: " + clientes.get(i).getNombre() +
                        "\nApellido: " + clientes.get(i).getApellidos() +
                        "\nFecha Nacimiento: " + clientes.get(i).getFechaNacimiento() +
                        "\nProfesion: " +  clientes.get(i).getProfesion() +
                        "\nEstado: " +  clientes.get(i).getEstado());
            }
        }else{
            System.out.println("[SQLite - Select All] La tabla esta vacia, o ha ocurrido un error.");
        }
    }

    public static void darClienteDeBaja(){
        ControladorCliente.darBajaCliente(new Cliente("123asd",null,null,null,null,null,null));
    }

    public static void darClienteDeAlta(){
        ControladorCliente.darAltaCliente(new Cliente("123asd",null,null,null,null,null,null));
    }

    public static void actualizarUnoWhere(){

        ControladorCliente.updateCliente(new Cliente("123asd","pepe1","dfd","2020-03-03","una profesion","activo", null));
    }

    public static void selectAllEmpleados(){
        ArrayList<Empleado> empleados = ControladorEmpleado.selectAll();

        // Comprobamos que no el array list no esta vacio
        if (!empleados.isEmpty()){
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println("\nDNI: " + empleados.get(i).getDni() +
                        "\nNombre: " + empleados.get(i).getNombre() +
                        "\nApellido: " + empleados.get(i).getApellidos() +
                        "\nFecha Nacimiento: " + empleados.get(i).getFechaNacimiento() +
                        "\nFecha Contratacion: " + empleados.get(i).getFechaContratacion() +
                        "\nNacionalidad: " + empleados.get(i).getNacionalidad() +
                        "\nCargo: " + empleados.get(i).getCargo() +
                        "\nPassword: " + empleados.get(i).getPassword() +
                        "\nEstado: " +  empleados.get(i).getEstado());
            }
        }else{
            System.out.println("[SQLite - Select All] La tabla esta vacia, o ha ocurrido un error.");
        }
    }

    public static void insertEmpleado(){
        ControladorEmpleado.insertEmpleado(new Empleado("DD333", "PEPE", "GOTERA", "2020-01-01", "2020-01-01", "ALGUNA", "UN CARGO", "PASSWORD", "ACTIVO"));
    }


}
