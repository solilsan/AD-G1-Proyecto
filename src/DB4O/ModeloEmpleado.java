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

    Empleado e = buscar(emp.getDni());

    //si no ha escrito nada en la contraseña le dejo la que tenia puesta
    if (emp.getPassword().length() < 1) emp.setPassword(e.getPassword());

    return ControladorEmpleado.actualizaEmpleado(emp);
  }

  public static Empleado buscar(String dni) {
    return ControladorEmpleado.buscaEmpleado(dni);
  }

  public static List<Empleado> mostrar() {
    return ControladorEmpleado.mostrarEmpleados();
  }

  public static List<Empleado> mostrarEmpleadosAlta() {

    List<Empleado> empleados = ControladorEmpleado.mostrarEmpleados();

    empleados.removeIf(empleado -> empleado.getEstado().equals("baja"));

    return empleados;
  }

  //public static boolean login(String dni, String contrasinal){return ControladorEmpleado.login(dni, contrasinal);}

  public static boolean login(String dni, String contrasinal) {
    return true;
  }

}
