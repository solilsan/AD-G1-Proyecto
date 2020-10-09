package Pruebas;

import Clases.Cliente;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;

import java.sql.Connection;

public class MySqlTest {

    public static void main(String[] args) {

        try {

            Connection connection = MySqlConexion.connection();
            Cliente cliente = new Cliente("12345678Z", "Pepe", "Lopéz", "29/03/1998", "Programador", "alta");
            MySqlControladorCliente.insert(connection, cliente);

        }
        catch(Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
