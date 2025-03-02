package SQLite;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;

import javax.print.attribute.HashAttributeSet;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                            reformatearFechas(rs.getDate("FECHA_HORA").toString()), ControladorEmpleado.selectByDni(rs.getString("DNI_EMPLEADO")).get(0)));
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

    public static ArrayList<Visita> selectActivos(){
        Connection conn = conn();
        ArrayList<Visita> resultado = new ArrayList<>();

        // Comprobamos que la conexion haya tenido exito.
        // Si sale null es que ha habido un error.
        if (conn != null) {
            String query = "SELECT * FROM VISITAS WHERE ESTADO = 'alta'";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    resultado.add(new Visita(rs.getInt("ID"), rs.getString("NOMBRE"),
                            rs.getInt("N_MAX_CLI"), rs.getString("PUNTO_PARTIDA"),
                            rs.getString("CURSO_ACADEMICO"), rs.getString("TEMATICA"),
                            rs.getFloat("COSTE"), rs.getString("ESTADO"),
                            reformatearFechas(rs.getDate("FECHA_HORA").toString()), ControladorEmpleado.selectByDni(rs.getString("DNI_EMPLEADO")).get(0)));
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
            sentencia.setDate(10, formatearFechaDb(objVisita.getFecha_hora())); // Pendiente de actualiza la clase empleado.

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

    public static ArrayList<Visita> selectWhere(int id) {
        Connection conn = conn();
        ArrayList<Visita> listaVisitas = new ArrayList<>();
        //String query = "SELECT * FROM VISITAS WHERE "+ parametro +" = ?";
        String query = "SELECT * FROM VISITAS WHERE ID = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            sentencia.setInt(1, id);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                Visita v = new Visita(rs.getInt("ID"), rs.getString("NOMBRE"),
                        rs.getInt("N_MAX_CLI"), rs.getString("PUNTO_PARTIDA"),
                        rs.getString("CURSO_ACADEMICO"), rs.getString("TEMATICA"),
                        rs.getFloat("COSTE"), rs.getString("ESTADO"),
                        reformatearFechas(rs.getDate("FECHA_HORA").toString()), ControladorEmpleado.selectByDni(rs.getString("DNI_EMPLEADO")).get(0));

                ArrayList<Cliente> clientes = ControladorCliente.selectWhereVisita(v.getId());

                // TEMPORAL
                for (int i = 0; i < clientes.size(); i++) {
                    v.addCliente(clientes.get(i));
                }

                listaVisitas.add(v);
            }

            return listaVisitas;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaVisitas;
    }

    public static  Boolean getNCliVisita(int idVisita){
        Connection conn = conn();
        String query = "SELECT COUNT(*) AS NCLI FROM V_GUIADA WHERE ID_VISITA = ?";
        boolean hayEspacio = false;

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, idVisita);

            // Ejecutamos la sentencia
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                if (rs.getInt("NCLI") < selectWhere(idVisita).get(0).getNmaxCli()){
                    hayEspacio = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return hayEspacio;
    }

    public static Boolean addClienteVisita(String dniCli, int idVisita){
        Connection conn = conn();
        String query = "INSERT INTO V_GUIADA (ID_VISITA, DNI_CLI) VALUES (?, ?)";

        if (!getNCliVisita(idVisita)){
            return false;
        }

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, idVisita);
            sentencia.setString(2, dniCli);


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

    public static boolean eliminaClienteVisita(String dniCli, int idVisita){
        Connection conn = conn();
        String query = "DELETE FROM V_GUIADA WHERE ID_VISITA = ? AND DNI_CLI = ?";


        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, idVisita);
            sentencia.setString(2, dniCli);


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
     * ESTA FUNCION, Y LA DE ALTA, SE PODRIAN FUSIONAR EN UNA
     * TODO: POSIBLE OPTIMIZACION.
     */
    public static boolean darBajaVisita(int idVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET ESTADO = 'baja' WHERE ID = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, idVisita);

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

    public static boolean darAltaVisita(int idVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET ESTADO = 'alta' WHERE ID = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setInt(1, idVisita);

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

    public static boolean updateVisita(Visita objVisita){
        Connection conn = conn();
        String query = "UPDATE VISITAS SET NOMBRE = ?, N_MAX_CLI = ?, PUNTO_PARTIDA = ?, CURSO_ACADEMICO = ?, TEMATICA = ?, COSTE = ?, ESTADO = ?, DNI_EMPLEADO = ?, FECHA_HORA = ? WHERE ID = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(query);

            // Introducimos los datos
            sentencia.setString(1, objVisita.getNombre());
            sentencia.setInt(2, objVisita.getNmaxCli());
            sentencia.setString(3, objVisita.getPuntoPartida());
            sentencia.setString(4, objVisita.getCursoAcademico());
            sentencia.setString(5, objVisita.getTematica());
            sentencia.setFloat(6, objVisita.getCoste());
            sentencia.setString(7, objVisita.getEstado());
            sentencia.setString(8, objVisita.getEmpleado().getDni());
            sentencia.setDate(9, formatearFechaDb(objVisita.getFecha_hora()));
            sentencia.setInt(10, objVisita.getId());

            // Ejecutamos la sentencia
            int res = sentencia.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (SQLException throwables) {
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
