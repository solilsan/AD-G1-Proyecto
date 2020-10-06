package MySql;

import java.sql.*;

public class MySqlConexion {

    protected static Connection conn() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            //String sURL = "jdbc:mysql://127.0.0.1:3307/prueba";
            String sURL = "jdbc:mysql://127.0.0.1:3307/prueba?serverTimezone=UTC";
            conn = DriverManager.getConnection(sURL,"root","admin");

            if(conn != null) {

                return conn;

            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

}
