package Pruebas;

import Clases.Cliente;
import MySql.MySqlControladorCliente;

public class MySqlTest {

    public static void main(String[] args) {

        Cliente c1 = new Cliente("12345678Z", "prueba", "prueba", "29/03/1998", "prueba", "alta", null);

        try {
            MySqlControladorCliente.insert(c1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
