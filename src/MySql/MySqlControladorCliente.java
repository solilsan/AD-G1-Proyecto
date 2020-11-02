package MySql;

import Clases.Cliente;
import Clases.Visita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MySqlControladorCliente {

    public static String insert(Connection connection, Cliente cliente) {

        try {

            String sql = "INSERT INTO CLIENTES(DNI, NOMBRE, APELLIDO, FECHA_NAC, PROFESION, ESTADO) VALUES(?,?,?,?,?,?);";

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(cliente.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(parsed.getTime());

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setDate(4, fecha_nac_sqlDate);
            pstmt.setString(5, cliente.getProfesion());
            pstmt.setString(6, cliente.getEstado());
            pstmt.execute();

            return "Cliente guardado";

        }
        catch (java.text.ParseException parseException) {
            return ("Error al hacer el parse de fechas, " + parseException.getMessage());
        }
        catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static String deleteWithDni(Connection connection, String dniCliente) {

        try {

            String sql = "UPDATE CLIENTES SET ESTADO = ? WHERE dni = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "baja");
            pstmt.setString(2, dniCliente);

            if (pstmt.executeUpdate() >= 1) {
                return "Cliente eliminado";
            }
            else {
                return "No existe un cliente con ese dni";
            }

        }
        catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static String updateWithDni(Connection connection, Cliente cliente) {

        try {

            String sql = "UPDATE CLIENTES\n" +
                         "SET NOMBRE = ?, APELLIDO = ?, FECHA_NAC = ?, PROFESION = ?, ESTADO = ?\n" +
                         "WHERE DNI = ?;";

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(cliente.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(parsed.getTime());

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setDate(3, fecha_nac_sqlDate);
            pstmt.setString(4, cliente.getProfesion());
            pstmt.setString(5, cliente.getEstado());
            pstmt.setString(6, cliente.getDni());

            if (pstmt.executeUpdate() >= 1) {
                return "Cliente actualizado";
            }
            else {
                return "No existe un cliente con ese dni";
            }

        } catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static Cliente selectWithDni(Connection connection, String dniCliente) {

        try {

            String sql = "SELECT * FROM CLIENTES WHERE dni = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dniCliente);
            ResultSet rs = pstmt.executeQuery();

            Cliente cliente = null;

            if (rs.next()) {

                cliente = new Cliente();
                cliente.setDni(rs.getString("DNI"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setApellidos(rs.getString("APELLIDO"));
                cliente.setFechaNacimiento(rs.getDate("FECHA_NAC").toString());
                cliente.setProfesion(rs.getString("PROFESION"));
                cliente.setEstado(rs.getString("ESTADO"));

            }

            return cliente;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Cliente> selectAll(Connection connection) {

        try {

            String sql = "SELECT * FROM CLIENTES;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
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

    public static ArrayList<Cliente> selectAllActivos(Connection connection) {

        try {

            String sql = "SELECT * FROM CLIENTES WHERE ESTADO = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "alta");
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

    public static ArrayList<Visita> selectAllApuntadas(Connection connection, String dniCliente) {

        try {

            String sql = "SELECT v.* FROM CLIENTES c, VISITAS v, V_GUIADA vg WHERE vg.DNI_CLI = c.DNI AND vg.ID_VISITA = v.ID AND c.DNI = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dniCliente);
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

}