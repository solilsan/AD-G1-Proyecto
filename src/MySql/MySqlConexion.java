package MySql;

import java.sql.*;

public class MySqlConexion {

    public static Connection connection() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String sURL = "jdbc:mysql://127.0.0.1:3306/agencia_mysql?serverTimezone=UTC";
            conn = DriverManager.getConnection(sURL,"root","ascent");
            //String sURL = "jdbc:mysql://127.0.0.1:3307/agencia_mysql?serverTimezone=UTC";
            //conn = DriverManager.getConnection(sURL,"root","admin");

            if(conn != null) {

                return conn;

            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

}