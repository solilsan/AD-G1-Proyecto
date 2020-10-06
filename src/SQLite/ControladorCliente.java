package SQLite;

import Clases.Cliente;

import java.sql.*;
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
                            rs.getString("APELLIDO"), rs.getDate("FECHA_NAC").toString(),
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

    public static Boolean insertCliente(String DNI, String NOMBRE, String APELLIDO, String FECHA_NAC, String PROFESION, String ESTADO) {
        Connection conn = conn();
        String query = "INSERT INTO CLIENTES (DNI, NOMBRE, APELLIDO, FECHA_NAC, PROFESION, ESTADO) VALUES(?, ?, ?, ?, ?, ?)";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, DNI);
            sentencia.setString(2, NOMBRE);
            sentencia.setString(3, APELLIDO);
            sentencia.setDate(4, Date.valueOf(FECHA_NAC));
            sentencia.setString(5, PROFESION);
            sentencia.setString(6, ESTADO);

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

    public static ArrayList<Cliente> selectWhere(String parametro, String tiene) {
        Connection conn = conn();
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        String query = "SELECT * FROM CLIENTES WHERE "+ parametro +" = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            sentencia.setString(1, tiene);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                listaClientes.add(new Cliente(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"), rs.getDate("FECHA_NAC").toString(),
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


    /**
     * ATENCION !!!!! NO SE LE PUEDE PASAR EL CAMPO DE WHERE CON ALGO QUE SEA DISTINTO DE STRING
     */
    public static boolean updateUnCampo(String campo, String valor, String campoWhere, String valorWhere){
        Connection conn = conn();
        String query = "UPDATE CLIENTES SET "+ campo +" = 'alta' WHERE "+ campoWhere +" = ?";

        // Comprobamos que el DNI del objeto no este vacio
        if (campo.equals("") || campo == null || campoWhere.equals("") || valorWhere == null){
            // No se puede seguir sin un DNI
            System.out.println("[SQLite - ControladorCliente] Se ha intentado alterar un campo del cliente, pero el parametro es\n" +
                    "nulo, o esta vacio.");
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, valor);
            sentencia.setString(2, valorWhere);

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
