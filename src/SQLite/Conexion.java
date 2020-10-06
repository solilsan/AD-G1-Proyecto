package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    protected static Connection conn(){
        String direccionDB = "jdbc:sqlite:.\\DB\\agenciaGrupo1_sqlite.db";
        Connection conn = null;
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(direccionDB);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("[SQLite] Error al realizar la conexion a la base de datos SQLite.\n " +
                    "Ha ocurrido el siguiente error: \n" + e);
        }

        // Devolvemos la conexion
        return conn;
    }



}
