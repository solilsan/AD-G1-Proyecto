package MySql;

import Clases.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySqlControladorCliente {

    public static void insert(Cliente cliente) throws SQLException, ParseException {

        Connection conn = MySqlConexion.conn();

        String sql = "INSERT INTO CLIENTES(DNI, NOMBRE, APELLIDO, FECHA_NAC, PROFESION, ESTADO) VALUES(?,?,?,?,?,?)";

        Date fecha_nac = new SimpleDateFormat("dd/MM/yyyy").parse(cliente.getFechaNacimiento());

        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement(sql); {
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setDate(4, (java.sql.Date) fecha_nac);
            pstmt.setString(5, cliente.getProfesion());
            pstmt.setString(6, cliente.getEstado());
            pstmt.executeUpdate();
        }

        conn.close();

    }

}