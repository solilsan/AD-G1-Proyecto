package MySql;

import Clases.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySqlControladorCliente {

    public static void insert(Connection connection, Cliente cliente) {

        try {

            connection = MySqlConexion.connection();

            String sql = "INSERT INTO CLIENTES(DNI, NOMBRE, APELLIDO, FECHA_NAC, PROFESION, ESTADO) VALUES(?,?,?,?,?,?)";

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(cliente.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(parsed.getTime());

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            {
                pstmt.setString(1, cliente.getDni());
                pstmt.setString(2, cliente.getNombre());
                pstmt.setString(3, cliente.getApellidos());
                pstmt.setDate(4, fecha_nac_sqlDate);
                pstmt.setString(5, cliente.getProfesion());
                pstmt.setString(6, cliente.getEstado());
                pstmt.executeUpdate();
            }

            connection.close();

        }
        catch (java.text.ParseException parseException) {
            System.out.println("Error al hacer el parse de fechas, " + parseException.getMessage());
        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

    }

}