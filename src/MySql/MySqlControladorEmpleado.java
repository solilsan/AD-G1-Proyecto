package MySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySqlControladorEmpleado {

    public static String login(Connection connection, String dni, String password) {

        String dniLogin = "";

        try {

            String sql = "SELECT DNI FROM EMPLEADOS WHERE dni = ? AND PASSWORD = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dni);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                dniLogin = rs.getString("DNI");
            }

            return dniLogin;

        } catch (Exception sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return null;

    }

}
