package MySql;

import java.sql.*;

public class MySqlMetadatos {

    public static String getMetadatos(Connection connection) {

        String resultado = "<html><body style='font-size: 10px; padding-left: 50px;'>";

        try {

            DatabaseMetaData dbmd = connection.getMetaData();

            String nombre = dbmd.getDatabaseProductName();

            String driver = dbmd.getDriverName();

            String url = dbmd.getURL();

            String usuario = dbmd.getUserName();

            resultado += "<h1>Información sobre la base de datos:</h1><br>";
            resultado += "<h2>Nombre: " + nombre + "</h2><br>";
            resultado += "<h2>Driver: " + driver + "</h2><br>";
            resultado += "<h2>Url: " + url + "</h2><br>";
            resultado += "<h2>Usuario: " + usuario + "</h2><br>";

            ResultSet result = dbmd.getTables("sql7373975", "", null, null);

            while (result.next()) {

                resultado += "<h3>Tabla : " + result.getString(3) + ", Esquema: " + result.getString(2);

                try (ResultSet primaryKeys = dbmd.getPrimaryKeys(result.getString("TABLE_CAT"), result.getString("TABLE_SCHEM"), result.getString("TABLE_NAME"))) {
                    while (primaryKeys.next()) {
                        resultado += ", Primary key: " + primaryKeys.getString("COLUMN_NAME");
                    }
                }

                ResultSet columns = dbmd.getColumns("sql7373975", null, result.getString(3), null);

                resultado += "<br> Columnas: ";

                while (columns.next()) {

                    resultado += "Nombre: " + columns.getString("COLUMN_NAME") + ", Tipo: " + columns.getString("TYPE_NAME") + ", Nulo: " + columns.getString("IS_NULLABLE") + ".   ";

                }

                resultado += "</h3><br>";

            }

        } catch (SQLException throwables) {
            System.out.println("[Metadatos] Error: \n " +
                    throwables.getMessage());
        }

        return (resultado + "</body></html>");
    }

}
