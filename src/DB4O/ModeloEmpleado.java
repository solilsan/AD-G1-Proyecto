package DB4O;

import Clases.Empleado;

import javax.swing.*;
import java.util.List;

public class ModeloEmpleado {

    public static String guardar(Empleado emp) {

        return ControladorEmpleado.guardaEmpleado(emp);
    }

    public static String eliminar(Empleado emp) {
        return ControladorEmpleado.eliminaEmpleado(emp);
    }

    public static String actualiza(Empleado emp) {
        return ControladorEmpleado.actualizaEmpleado(emp);
    }

    public static Empleado buscar(String dni){return ControladorEmpleado.buscaEmpleado(dni);}

    public static List<Empleado> mostrar() {
        return ControladorEmpleado.mostrarEmpleados();
    }

    public static boolean login(){return true;}


}
