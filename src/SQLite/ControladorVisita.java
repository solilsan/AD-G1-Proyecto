package SQLite;

import Clases.Empleado;
import Clases.Visita;

import java.sql.*;
import java.util.ArrayList;

public class ControladorVisita extends Conexion{

    public static ArrayList<Visita> selectAll() {
        Connection conn = conn();
        ArrayList<Visita> resultado = new ArrayList<>();

        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null) {
            String query = "SELECT * FROM VISITAS";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    resultado.add(new Visita(rs.getInt("ID"), rs.getString("NOMBRE"),
                            rs.getInt("N_MAX_CLI"), rs.getString("PUNTO_PARTIDA"),
                            rs.getString("CURSO_ACADEMICO"), rs.getString("TEMATICA"),
                            rs.getFloat("COSTE"), rs.getString("ESTADO"),
                            ControladorEmpleado.selectByDni(rs.getString("DNI_EMPLEADO")).get(0)/*, rs.getDate("FECHA_HORA").toString()*/));
                }

                conn.close();
            } catch (SQLException throwables) {
                System.out.println("[SQLite - ControladorVisita] Error: \n " +
                        throwables.getMessage());
            }
        }


        // Devolvemos el resultado
        return resultado;
    }

    public static Boolean insertVisita(Visita objVisita) {
        Connection conn = conn();
        String query = "INSERT INTO VISITAS (ID, NOMBRE, N_MAX_CLI, PUNTO_PARTIDA, CURSO_ACADEMICO, TEMATICA, COSTE, ESTADO, DNI_EMPLEADO, FECHA_HORA) VALUES(?,?,?,?,?,?,?,?,?,?)";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, objVisita.getId());
            sentencia.setString(2, objVisita.getNombre());
            sentencia.setInt(3, objVisita.getNmaxCli());
            sentencia.setString(4, objVisita.getPuntoPartida());
            sentencia.setString(5, objVisita.getCursoAcademico());
            sentencia.setString(6, objVisita.getTematica());
            sentencia.setFloat(7, objVisita.getCoste());
            sentencia.setString(8, objVisita.getEstado());
            sentencia.setString(9, objVisita.getEmpleado().getDni());
            //sentencia.setDate(10, Date.valueOf(objVisita.get)); // Pendiente de actualiza la clase empleado.

            // Ejecutamos la sentencia
            Integer res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Si hemos llegado aqui, es que algo malo ha pasado.
        return false;
    }

    public static ArrayList<Visita> selectWhere(/*String parametro,*/ String tiene) {
        Connection conn = conn();
        ArrayList<Visita> listaVisitas = new ArrayList<>();
        //String query = "SELECT * FROM VISITAS WHERE "+ parametro +" = ?";
        String query = "SELECT * FROM VISITAS WHERE DNI = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            sentencia.setString(1, tiene);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                listaVisitas.add(new Visita(rs.getInt("ID"), rs.getString("NOMBRE"),
                        rs.getInt("N_MAX_CLI"), rs.getString("PUNTO_PARTIDA"),
                        rs.getString("CURSO_ACADEMICO"), rs.getString("TEMATICA"),
                        rs.getFloat("COSTE"), rs.getString("ESTADO"),
                        ControladorEmpleado.selectByDni(rs.getString("DNI_EMPLEADO")).get(0)));
            }

            return listaVisitas;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaVisitas;
    }

    /**
     * ESTA FUNCION, Y LA DE ALTA, SE PODRIAN FUSIONAR EN UNA
     * TODO: POSIBLE OPTIMIZACION.
     */
    public static boolean darBajaVisita(Visita objVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET ESTADO = 'baja' WHERE DNI = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, objVisita.getId());

            // Ejecutamos la sentencia
            Integer res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Si hemos llegado aqui, es que algo malo ha pasado.
        return false;
    }

    public static boolean darAltaVisita(Visita objVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET ESTADO = 'alta' WHERE DNI = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, objVisita.getId());

            // Ejecutamos la sentencia
            Integer res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Si hemos llegado aqui, es que algo malo ha pasado.
        return false;
    }

    public static boolean updateUnCampo(Visita objVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET NOMBRE = ?, N_MAX_CLI = ?, PUNTO_PARTIDA = ?, CURSO_ACADEMICO = ?, TEMATICA = ?, COSTE = ?, ESTADO = ?, DNI_EMPLEADO = ?, FECHA_HORA = ? WHERE ID = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, objVisita.getId());
            sentencia.setString(2, objVisita.getNombre());
            sentencia.setInt(3, objVisita.getNmaxCli());
            sentencia.setString(4, objVisita.getPuntoPartida());
            sentencia.setString(5, objVisita.getCursoAcademico());
            sentencia.setString(6, objVisita.getTematica());
            sentencia.setFloat(7, objVisita.getCoste());
            sentencia.setString(8, objVisita.getEstado());
            sentencia.setString(9, objVisita.getEmpleado().getDni());
            //sentencia.setDate(10, Date.valueOf(objVisita.get)); // Pendiente de actualiza la clase empleado.

            // Ejecutamos la sentencia
            Integer res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Si hemos llegado aqui, es que algo malo ha pasado.
        return false;
    }

}
