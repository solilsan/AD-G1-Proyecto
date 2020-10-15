package SQLite;

import Clases.Visita;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Metadatos extends Conexion {

    public static String getMetadatos() {
        Connection conn = conn();
        String resultado = "<html>";
        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null) {
            String query = "SELECT name, sql FROM sqlite_master WHERE type='table' ORDER BY name";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {

                    resultado += "<h1>Nombre Tabla: </h1><p>"+rs.getString("name")+"<p><br><h2>Estructura: </h2><br>"+rs.getString("sql");

                }

                conn.close();
            } catch (SQLException throwables) {
                System.out.println("[SQLite - ControladorVisita] Error: \n " +
                        throwables.getMessage());
            }

        }
        return (resultado + "</html>");
    }
}
