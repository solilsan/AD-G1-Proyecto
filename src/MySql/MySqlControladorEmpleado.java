package MySql;

import Clases.Cliente;
import Clases.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MySqlControladorEmpleado {

    public static String login(Connection connection, String dni, String password) {

        String dniLogin = "";

        try {

            String sql = "SELECT DNI FROM EMPLEADOS WHERE ESTADO = ? AND dni = ? AND PASSWORD = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "alta");
            pstmt.setString(2, dni);
            pstmt.setString(3, password);
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

    public static String insert(Connection connection, Empleado empleado) {

        try {

            String sql = "INSERT INTO EMPLEADOS(DNI, NOMBRE, APELLIDO, FECHA_NAC, F_CONTRATACION, NACIONALIDAD, CARGO, PASSWORD, ESTADO) VALUES(?,?,?,?,?,?,?,?,?);";

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date fecha_nac = format.parse(empleado.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(fecha_nac.getTime());

            Date f_contratacion = format.parse(empleado.getFechaContratacion());
            java.sql.Date f_contratacion_sqlDate = new java.sql.Date(f_contratacion.getTime());

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, empleado.getDni());
            pstmt.setString(2, empleado.getNombre());
            pstmt.setString(3, empleado.getApellidos());
            pstmt.setDate(4, fecha_nac_sqlDate);
            pstmt.setDate(5, f_contratacion_sqlDate);
            pstmt.setString(6, empleado.getNacionalidad());
            pstmt.setString(7, empleado.getCargo());
            pstmt.setString(8, empleado.getPassword());
            pstmt.setString(9, empleado.getEstado());
            pstmt.execute();

            return "Empleado guardado";

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

    public static String deleteWithDni(Connection connection, String dniEmpleado) {

        try {

            String sql = "UPDATE EMPLEADOS SET ESTADO = ? WHERE dni = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "baja");
            pstmt.setString(2, dniEmpleado);

            if (pstmt.executeUpdate() >= 1) {
                return "Empleado eliminado";
            }
            else {
                return "No existe un empleado con ese dni";
            }

        }
        catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static String updateWithDni(Connection connection, Empleado empleado) {

        try {

            String sql = "UPDATE EMPLEADOS\n" +
                    "SET NOMBRE = ?, APELLIDO = ?, FECHA_NAC = ?, F_CONTRATACION = ?, NACIONALIDAD = ?, CARGO = ?, PASSWORD = ?, ESTADO = ?\n" +
                    "WHERE DNI = ?;";

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date fecha_nac = format.parse(empleado.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(fecha_nac.getTime());

            Date f_contratacion = format.parse(empleado.getFechaContratacion());
            java.sql.Date f_contratacion_sqlDate = new java.sql.Date(f_contratacion.getTime());

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellidos());
            pstmt.setDate(3, fecha_nac_sqlDate);
            pstmt.setDate(4, f_contratacion_sqlDate);
            pstmt.setString(5, empleado.getNacionalidad());
            pstmt.setString(6, empleado.getCargo());
            pstmt.setString(7, empleado.getPassword());
            pstmt.setString(8, empleado.getEstado());
            pstmt.setString(9, empleado.getDni());

            if (pstmt.executeUpdate() >= 1) {
                return "Empleado actualizado";
            }
            else {
                return "No existe un empleado con ese dni";
            }

        } catch (java.sql.SQLException sqlException) {
            return ("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            return ("Error general, " + e.getMessage());
        }

    }

    public static Empleado selectWithDni(Connection connection, String dniEmpleado) {

        try {

            String sql = "SELECT * FROM EMPLEADOS WHERE dni = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dniEmpleado);
            ResultSet rs = pstmt.executeQuery();

            Empleado empleado = null;

            if (rs.next()) {

                empleado = new Empleado();
                empleado.setDni(rs.getString("DNI"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDO"));
                empleado.setFechaNacimiento(rs.getDate("FECHA_NAC").toString());
                empleado.setFechaContratacion(rs.getDate("F_CONTRATACION").toString());
                empleado.setNacionalidad(rs.getString("NACIONALIDAD"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setPassword(rs.getString("PASSWORD"));
                empleado.setEstado(rs.getString("ESTADO"));

            }

            return empleado;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Empleado> selectAll(Connection connection) {

        try {

            String sql = "SELECT * FROM EMPLEADOS;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Empleado> listaEmpelados = new ArrayList<>();

            while (rs.next()) {

                Empleado empleado = new Empleado();
                empleado.setDni(rs.getString("DNI"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDO"));
                empleado.setFechaNacimiento(rs.getDate("FECHA_NAC").toString());
                empleado.setFechaContratacion(rs.getDate("F_CONTRATACION").toString());
                empleado.setNacionalidad(rs.getString("NACIONALIDAD"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setPassword(rs.getString("PASSWORD"));
                empleado.setEstado(rs.getString("ESTADO"));

                listaEmpelados.add(empleado);

            }

            return listaEmpelados;

        }
        catch (java.sql.SQLException sqlException) {
            System.out.println("Error de sql, " + sqlException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general, " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Empleado> selectAllActivos(Connection connection) {

        try {

            String sql = "SELECT * FROM EMPLEADOS WHERE ESTADO = ?;";

            assert connection != null;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "alta");
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Empleado> listaEmpelados = new ArrayList<>();

            while (rs.next()) {

                Empleado empleado = new Empleado();
                empleado.setDni(rs.getString("DNI"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDO"));
                empleado.setFechaNacimiento(rs.getDate("FECHA_NAC").toString());
                empleado.setFechaContratacion(rs.getDate("F_CONTRATACION").toString());
                empleado.setNacionalidad(rs.getString("NACIONALIDAD"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setPassword(rs.getString("PASSWORD"));
                empleado.setEstado(rs.getString("ESTADO"));

                listaEmpelados.add(empleado);

            }

            return listaEmpelados;

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
