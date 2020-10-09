package Pruebas;

import Clases.Cliente;
import MySql.MySqlConexion;
import MySql.MySqlControladorCliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public class MySqlTest {

    public static void main(String[] args) {

        try {

            Connection connection = MySqlConexion.connection();

            Cliente cliente = new Cliente("12345678Z", "Pepe", "Lopéz", "29/03/1998", "Programador", "alta");

            //MySqlControladorCliente.insert(connection, cliente);
            //MySqlControladorCliente.deleteWithDni(connection, cliente.getDni());

            /*
            cliente.setNombre("Calos");
            cliente.setApellidos("Kola");
            cliente.setFechaNacimiento("30/08/1997");
            cliente.setProfesion("Guardia");
            cliente.setEstado("baja");
            MySqlControladorCliente.updateWithDni(connection, cliente);
             */

            /*
            Cliente c = MySqlControladorCliente.selectWithDni(connection, "12345678Z");
            assert c != null;
            System.out.println(c.toString());
             */

            /*
            ArrayList<Cliente> listaClientes = MySqlControladorCliente.selectAll(connection);
            for (int i = 0; i < Objects.requireNonNull(listaClientes).size(); i++) {
                System.out.println(listaClientes.get(i).toString());
            }
             */

            assert connection != null;
            connection.close();

        }
        catch(Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
