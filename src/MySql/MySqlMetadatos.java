package MySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlMetadatos {

    public static String getMetadatos(Connection connection) {

        String resultado = "<html><body style='font-size: 10px; padding-left: 50px;'>";

        String sql = "SHOW TABLE STATUS FROM sql7373975;";

        try {
            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                resultado += "<h1>Nombre Tabla: "+rs.getString("Name")+"<p><br>"+rs.getString("Collation");

            }

        } catch (SQLException throwables) {
            System.out.println("[Metadatos] Error: \n " +
                    throwables.getMessage());
        }

        return (resultado + "</body></html>");
    }

}
