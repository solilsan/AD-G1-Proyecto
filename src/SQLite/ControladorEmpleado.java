package SQLite;

import Clases.Empleado;
import Clases.Empleado;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;

public class ControladorEmpleado extends Conexion{

    public static ArrayList<Empleado> selectAll() {
        Connection conn = conn();
        ArrayList<Empleado> resultado = new ArrayList<>();

        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null) {
            String query = "SELECT * FROM EMPLEADOS";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    resultado.add(new Empleado(rs.getString("DNI"), rs.getString("NOMBRE"),
                            rs.getString("APELLIDO"), rs.getDate("FECHA_NAC").toString(),
                            rs.getDate("F_CONTRATACION").toString(), rs.getString("NACIONALIDAD"),
                            rs.getString("CARGO"), rs.getString("PASSWORD"), rs.getString("ESTADO")));
                }

                conn.close();
            } catch (SQLException throwables) {
                System.out.println("[SQLite - ControladorEmpleado] Error: \n " +
                        throwables.getMessage());
            }
        }


        // Devolvemos el resultado
        return resultado;
    }

    public static Boolean insertEmpleado(Empleado objEmpleado) {
        Connection conn = conn();
        String query = "INSERT INTO EMPLEADOS (DNI, NOMBRE, APELLIDO, FECHA_NAC, F_CONTRATACION, NACIONALIDAD, CARGO, PASSWORD, ESTADO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Verificamos las fechas
        // TODO: Validar las fechas antes de meterlas a la base de datos.
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            java.util.Date fecha_nac = format.parse(objEmpleado.getFechaNacimiento());
            java.sql.Date fecha_nac_sqlDate = new java.sql.Date(fecha_nac.getTime());

            java.util.Date f_contratacion = format.parse(objEmpleado.getFechaContratacion());
            java.sql.Date f_contratacion_sqlDate = new java.sql.Date(f_contratacion.getTime());


            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objEmpleado.getDni());
            sentencia.setString(2, objEmpleado.getNombre());
            sentencia.setString(3, objEmpleado.getApellidos());
            sentencia.setDate(4, fecha_nac_sqlDate);
            sentencia.setDate(5, f_contratacion_sqlDate);
            sentencia.setString(6, objEmpleado.getNacionalidad());
            sentencia.setString(7, objEmpleado.getCargo());
            sentencia.setString(8, objEmpleado.getPassword());
            sentencia.setString(9, objEmpleado.getEstado());

            // Ejecutamos la sentencia
            Integer res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }




        // Si hemos llegado aqui, es que algo malo ha pasado.
        return false;
    }

    public static ArrayList<Empleado> selectByDni(String tiene) {
        Connection conn = conn();
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        //String query = "SELECT * FROM EMPLEADOS WHERE "+ parametro +" = ?";
        String query = "SELECT * FROM EMPLEADOS WHERE DNI = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            sentencia.setString(1, tiene);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                listaEmpleados.add(new Empleado(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"), rs.getDate("FECHA_NAC").toString(),
                        rs.getDate("F_CONTRATACION").toString(), rs.getString("NACIONALIDAD"),
                        rs.getString("CARGO"), rs.getString("PASSWORD"), rs.getString("ESTADO")));
            }

            return listaEmpleados;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaEmpleados;
    }

    /**
     * ESTA FUNCION, Y LA DE ALTA, SE PODRIAN FUSIONAR EN UNA
     * TODO: POSIBLE OPTIMIZACION.
     */
    public static boolean darBajaEmpleado(Empleado objEmpleado){
        Connection conn = conn();
        String query = "UPDATE EMPLEADOS SET ESTADO = 'baja' WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objEmpleado.getDni().equals("") || objEmpleado.getDni() == null){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorEmpleado] Se ha intentado dar de baja un Empleado, pero se ha\n" +
                    "pasado un objeto Empleado sin DNI.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objEmpleado.getDni());

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

    public static boolean darAltaEmpleado(Empleado objEmpleado){
        Connection conn = conn();
        String query = "UPDATE EMPLEADOS SET ESTADO = 'alta' WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objEmpleado.getDni().equals("") || objEmpleado.getDni() == null){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorEmpleado] Se ha intentado dar de alta un Empleado, pero se ha\n" +
                    "pasado un objeto Empleado sin DNI.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objEmpleado.getDni());

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

    public static boolean updateEmpleado(Empleado objEmpleado){
        Connection conn = conn();
        String query = "UPDATE EMPLEADOS SET NOMBRE = ?, APELLIDO = ?, FECHA_NAC = ?, F_CONTRATACION = ?, NACIONALIDAD = ?, CARGO = ?, PASSWORD = ?, ESTADO = ? WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objEmpleado.getDni() == ""){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorEmpleado] Se ha intentado alterar un campo del Empleado, pero el parametro es\n" +
                    "nulo, o esta vacio.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objEmpleado.getDni());
            sentencia.setString(2, objEmpleado.getNombre());
            sentencia.setString(3, objEmpleado.getApellidos());
            sentencia.setDate(4, Date.valueOf(objEmpleado.getFechaNacimiento()));
            sentencia.setDate(5, Date.valueOf(objEmpleado.getFechaContratacion()));
            sentencia.setString(6, objEmpleado.getNacionalidad());
            sentencia.setString(7, objEmpleado.getCargo());
            sentencia.setString(8, objEmpleado.getPassword());
            sentencia.setString(9, objEmpleado.getEstado());

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
