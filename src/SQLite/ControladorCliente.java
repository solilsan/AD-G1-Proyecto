package SQLite;

import Clases.Cliente;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ControladorCliente extends Conexion {

    public static ArrayList<Cliente> selectAll() {
        Connection conn = conn();
        ArrayList<Cliente> resultado = new ArrayList<>();

        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null) {
            String query = "SELECT * FROM CLIENTES";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    resultado.add(new Cliente(rs.getString("DNI"), rs.getString("NOMBRE"),
                            rs.getString("APELLIDO"), reformatearFechas(rs.getDate("FECHA_NAC").toString()),
                            rs.getString("PROFESION"), rs.getString("ESTADO"), null));
                }

                conn.close();
            } catch (SQLException throwables) {
                System.out.println("[SQLite - ControladorCliente] Error: \n " +
                        throwables.getMessage());
            }
        }


        // Devolvemos el resultado
        return resultado;
    }

    public static Boolean insertCliente(Cliente objCliente) {
        Connection conn = conn();
        String query = "INSERT INTO CLIENTES (DNI, NOMBRE, APELLIDO, FECHA_NAC, PROFESION, ESTADO) VALUES(?, ?, ?, ?, ?, ?)";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objCliente.getDni());
            sentencia.setString(2, objCliente.getNombre());
            sentencia.setString(3, objCliente.getApellidos());
            sentencia.setDate(4, formatearFechaDb(objCliente.getFechaNacimiento()));
            sentencia.setString(5, objCliente.getProfesion());
            sentencia.setString(6, objCliente.getEstado());

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

    public static ArrayList<Cliente> selectWhereDni(/*String parametro,*/ String tiene) {
        Connection conn = conn();
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        //String query = "SELECT * FROM CLIENTES WHERE "+ parametro +" = ?";
        String query = "SELECT * FROM CLIENTES WHERE DNI = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            sentencia.setString(1, tiene);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                listaClientes.add(new Cliente(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"), reformatearFechas(rs.getDate("FECHA_NAC").toString()),
                        rs.getString("PROFESION"), rs.getString("ESTADO"), null));
            }

            return listaClientes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaClientes;
    }

    /**
     * ESTA FUNCION, Y LA DE ALTA, SE PODRIAN FUSIONAR EN UNA
     * TODO: POSIBLE OPTIMIZACION.
     */
    public static boolean darBajaCliente(Cliente objCliente){
        Connection conn = conn();
        String query = "UPDATE CLIENTES SET ESTADO = 'baja' WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objCliente.getDni().equals("") || objCliente.getDni() == null){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorCliente] Se ha intentado dar de baja un cliente, pero se ha\n" +
                    "pasado un objeto cliente sin DNI.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objCliente.getDni());

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

    public static boolean darAltaCliente(Cliente objCliente){
        Connection conn = conn();
        String query = "UPDATE CLIENTES SET ESTADO = 'alta' WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objCliente.getDni().equals("") || objCliente.getDni() == null){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorCliente] Se ha intentado dar de alta un cliente, pero se ha\n" +
                    "pasado un objeto cliente sin DNI.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objCliente.getDni());

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

    public static boolean updateCliente(Cliente objCliente){
        Connection conn = conn();
        String query = "UPDATE CLIENTES SET NOMBRE = ?, APELLIDO = ?, FECHA_NAC = ?, PROFESION = ?, ESTADO = ? WHERE DNI = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (objCliente.getDni() == ""){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorCliente] Se ha intentado alterar un campo del cliente, pero el parametro es\n" +
                    "nulo, o esta vacio.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objCliente.getNombre());
            sentencia.setString(2, objCliente.getApellidos());
            sentencia.setDate(3, formatearFechaDb(objCliente.getFechaNacimiento()));
            sentencia.setString(4, objCliente.getProfesion());
            sentencia.setString(5, objCliente.getEstado());
            sentencia.setString(6, objCliente.getDni());

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

    private static String reformatearFechas(String fecha){
        String dia = fecha.substring(8, 10);
        String mes = fecha.substring(5, 7);
        String anio = fecha.substring(0, 4);

        return (dia + "/" + mes + "/" + anio);
    }

    private static Date formatearFechaDb(String fecha){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        java.util.Date fecha_nac = null;
        try {
            fecha_nac = format.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date fecha_sqlDate = new java.sql.Date(fecha_nac.getTime());

        return fecha_sqlDate;
    }
}
