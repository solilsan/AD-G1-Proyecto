package SQLite;

import Clases.Cliente;

import java.awt.desktop.QuitEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ControladorCliente extends Conexion{

    public static ArrayList<Cliente> selectAll(){
        Connection conn = conn();
        ArrayList<Cliente> resultado = new ArrayList<>();

        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null){
            String query = "SELECT * FROM CLIENTES";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    resultado.add(new Cliente(rs.getString("DNI"), rs.getString("NOMBRE"),
                            rs.getString("APELLIDO"), rs.getDate("FECHA_NAC").toString(),
                            rs.getString("PROFESION"), rs.getString("ESTADO"), null));
                }

                conn.close();
            } catch (SQLException throwables) {
                System.out.println("[SQLite - ControladorCliente] Error: \n "+
                        throwables.getMessage());
            }
        }


        // Devolvemos el resultado
        return resultado;
    }

}
