package Pruebas;

import SQLite.ControladorCliente;
import Clases.Cliente;

import java.sql.Connection;
import java.util.ArrayList;

public class SQLiteTest {

    public static void main(String[] args) {
        //insertCliente();
        //selectWhereCliente();
        //darClienteDeBaja();
       // selectAllClientes();
       // darClienteDeAlta();
        actualizarUnoWhere();

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
        ArrayList<Cliente> clientes = ControladorCliente.selectWhere("dfd");

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

        ControladorCliente.updateUnCampo(new Cliente("123asd","pepe1","dfd","2020-03-03","una profesion","activo", null));
    }

}
