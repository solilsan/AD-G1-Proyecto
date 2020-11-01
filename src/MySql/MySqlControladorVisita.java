package MySql;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MySqlControladorVisita {

    public static String insert(Connection connection, Visita visita) {

        try {

            String sql = "INSERT INTO VISITAS(NOMBRE, N_MAX_CLI, PUNTO_PARTIDA, CURSO_ACADEMICO, TEMATICA, COSTE, ESTADO, DNI_EMPLEADO, FECHA_HORA) VALUES(?,?,?,?,?,?,?,?,?);";

            String a = visita.getFecha_hora();
            String str = a.replace("/", "-");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateTime);

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, visita.getNombre());
            pstmt.setInt(2, visita.getNmaxCli());
            pstmt.setString(3, visita.getPuntoPartida());
            pstmt.setString(4, visita.getCursoAcademico());
            pstmt.setString(5, visita.getTematica());
            pstmt.setDouble(6, visita.getCoste());
            pstmt.setString(7, visita.getEstado());
            pstmt.setString(8, visita.getEmpleado().getDni());
            pstmt.setTimestamp(9, sqlDate);
            pstmt.execute();

            return "Visita guardada";

        } catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static String deleteWithDni(Connection connection, int idVisita) {

        try {

            String sql = "UPDATE VISITAS SET ESTADO = ? WHERE id = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "baja");
            pstmt.setInt(2, idVisita);

            if (pstmt.executeUpdate() >= 1) {
                return "Visita eliminada";
            }
            else {
                return "No existe una visita con ese id";
            }

        }
        catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static String updateWithId(Connection connection, Visita visita) {

        try {

            String sql = "UPDATE VISITAS\n" +
                    "SET NOMBRE = ?, N_MAX_CLI = ?, PUNTO_PARTIDA = ?, CURSO_ACADEMICO = ?, TEMATICA = ?, COSTE = ?, ESTADO = ?, DNI_EMPLEADO = ?, FECHA_HORA = ?\n" +
                    "WHERE ID = ?;";

            String a = visita.getFecha_hora();
            String str = a.replace("/", "-");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateTime);

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, visita.getNombre());
            pstmt.setInt(2, visita.getNmaxCli());
            pstmt.setString(3, visita.getPuntoPartida());
            pstmt.setString(4, visita.getCursoAcademico());
            pstmt.setString(5, visita.getTematica());
            pstmt.setDouble(6, visita.getCoste());
            pstmt.setString(7, visita.getEstado());
            pstmt.setString(8, visita.getEmpleado().getDni());
            pstmt.setTimestamp(9, sqlDate);
            pstmt.setInt(10, visita.getId());

            if (pstmt.executeUpdate() >= 1) {
                return "Visita actualizada";
            }
            else {
                return "No existe una visita con ese dni";
            }

        } catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static Visita selectWithId(Connection connection, int idVisita) {

        try {

            String sql = "SELECT * FROM VISITAS WHERE ID = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idVisita);
            ResultSet rs = pstmt.executeQuery();

            Visita visita = null;

            if (rs.next()) {

                visita = new Visita();
                visita.setId(rs.getInt("ID"));
                visita.setNombre(rs.getString("NOMBRE"));
                visita.setNmaxCli(rs.getInt("N_MAX_CLI"));
                visita.setPuntoPartida(rs.getString("PUNTO_PARTIDA"));
                visita.setCursoAcademico(rs.getString("CURSO_ACADEMICO"));
                visita.setTematica(rs.getString("TEMATICA"));
                visita.setCoste(rs.getFloat("COSTE"));
                visita.setEstado(rs.getString("ESTADO"));
                visita.setEmpleado(MySqlControladorEmpleado.selectWithDni(connection, rs.getString("DNI_EMPLEADO")));
                visita.setFecha_hora(rs.getTimestamp("FECHA_HORA").toString());

            }

            return visita;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Visita> selectAll(Connection connection) {

        try {

            String sql = "SELECT * FROM VISITAS;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Visita> listaVisitas = new ArrayList<>();

            while (rs.next()) {

                Visita visita = new Visita();
                visita.setId(rs.getInt("ID"));
                visita.setNombre(rs.getString("NOMBRE"));
                visita.setNmaxCli(rs.getInt("N_MAX_CLI"));
                visita.setPuntoPartida(rs.getString("PUNTO_PARTIDA"));
                visita.setCursoAcademico(rs.getString("CURSO_ACADEMICO"));
                visita.setTematica(rs.getString("TEMATICA"));
                visita.setCoste(rs.getFloat("COSTE"));
                visita.setEstado(rs.getString("ESTADO"));
                visita.setEmpleado(MySqlControladorEmpleado.selectWithDni(connection, rs.getString("DNI_EMPLEADO")));
                visita.setFecha_hora(rs.getTimestamp("FECHA_HORA").toString());

                listaVisitas.add(visita);

            }

            return listaVisitas;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Visita> selectAllActivas(Connection connection) {

        try {

            String sql = "SELECT * FROM VISITAS WHERE ESTADO = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "alta");
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Visita> listaVisitas = new ArrayList<>();

            while (rs.next()) {

                Visita visita = new Visita();
                visita.setId(rs.getInt("ID"));
                visita.setNombre(rs.getString("NOMBRE"));
                visita.setNmaxCli(rs.getInt("N_MAX_CLI"));
                visita.setPuntoPartida(rs.getString("PUNTO_PARTIDA"));
                visita.setCursoAcademico(rs.getString("CURSO_ACADEMICO"));
                visita.setTematica(rs.getString("TEMATICA"));
                visita.setCoste(rs.getFloat("COSTE"));
                visita.setEstado(rs.getString("ESTADO"));
                visita.setEmpleado(MySqlControladorEmpleado.selectWithDni(connection, rs.getString("DNI_EMPLEADO")));
                visita.setFecha_hora(rs.getTimestamp("FECHA_HORA").toString());

                listaVisitas.add(visita);

            }

            return listaVisitas;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Cliente> selectAllClientesEnVisita(Connection connection, int idVisita) {

        try {

            String sql = "SELECT c.* FROM CLIENTES c, VISITAS v, V_GUIADA vg WHERE vg.DNI_CLI = c.DNI AND vg.ID_VISITA = v.ID AND v.ID = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idVisita);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Cliente> listaClientes = new ArrayList<>();

            while (rs.next()) {

                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString("DNI"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setApellidos(rs.getString("APELLIDO"));
                cliente.setFechaNacimiento(rs.getDate("FECHA_NAC").toString());
                cliente.setProfesion(rs.getString("PROFESION"));
                cliente.setEstado(rs.getString("ESTADO"));

                listaClientes.add(cliente);

            }

            return listaClientes;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static Boolean comprobarClienteEnVisita(Connection connection, int idVisita, String dniCliente) {

        boolean existe = false;

        try {

            String sql = "SELECT c.* FROM CLIENTES c, VISITAS v, V_GUIADA vg WHERE vg.DNI_CLI = c.DNI AND vg.ID_VISITA = v.ID AND v.ID = ? AND c.DNI = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idVisita);
            pstmt.setString(2, dniCliente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                existe = true;

            }

            return existe;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return false;
    }

    public static String insertClienteVisita(Connection connection, int idVisita, String dniCliente) {

        try {

            String sql = "INSERT INTO V_GUIADA (ID_VISITA, DNI_CLI) VALUES(?,?);";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idVisita);
            pstmt.setString(2, dniCliente);
            pstmt.execute();

            return "Cliente apuntado.";

        } catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static Boolean deleteClienteVisita(Connection connection, int idVisita, String dniCliente) {

        boolean borrado = false;

        try {

            String sql = "DELETE FROM V_GUIADA WHERE ID_VISITA = ? AND DNI_CLI = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idVisita);
            pstmt.setString(2, dniCliente);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {

                borrado = true;

            }

            return borrado;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return false;
    }

}
