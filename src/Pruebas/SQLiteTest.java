package Pruebas;

import SQLite.ControladorCliente;
import Clases.Cliente;

import java.util.ArrayList;

public class SQLiteTest {

    public static void main(String[] args) {
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
}
