package Pruebas;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;
import DB4O.ModeloEmpleado;

import java.util.List;

public class DB4OITest {
    public static void main(String[] args) {

        //********** A falta de la Ventana temporalmente he creado estos datos de ejemplo *********//
        Empleado emp1 = new Empleado("35575601Y",
                "pablo",
                "perez",
                "03/11/1985",
                "05/06/2010",
                "española",
                "guia",
                "pass",
                "En activo");

        Visita v1 = new Visita(1,
                "inserso1",
                1,
                "lakua",
                "2020",
                "educativa",
                520,
                "en curso",
                emp1);

        Cliente c1 = new Cliente(
                "35575600M",
                "Santi",
                "Gonszalez",
                "03/11/1984",
                "informatico",
                "alta", v1);

        Cliente c2 = new Cliente(
                "35575600M",
                "Santi",
                "none",
                "03/11/1984",
                "informatico",
                "alta", v1);
        /////////////////////////////////////////////////////////////////////////////////////////////

        /*System.out.println(ModeloCliente.guardar(c1));
        //System.out.println(ModeloCliente.eliminar(c1));

        Cliente cli = ModeloCliente.buscar("35575600M");


        if (cli != null)
            System.out.println("Encontrado - >" + cli.getNombre());
        else
            System.out.println("no encontrado");

        ModeloCliente.actualiza(c2);
        List<Cliente> clientes = ModeloCliente.mostrar();

        if (clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente.getNombre() + " " + cliente.getApellidos() + " " + cliente.getEstado());
            }
        } else
            System.out.println("No hay clientes que mostrar");*/



        System.out.println(ModeloEmpleado.guardar(emp1));
        //System.out.println(ModeloEmpleado.eliminar(emp1));

        Empleado emp = ModeloEmpleado.buscar("35575601Y");


        if (emp != null)
            System.out.println("Encontrado - >" + emp.getNombre());
        else
            System.out.println("no encontrado");

        ModeloEmpleado.actualiza(emp1);
        List<Empleado> empleados = ModeloEmpleado.mostrar();

        if (empleados.size() > 0) {
            for (Empleado empleado : empleados) {
                System.out.println(empleado.getNombre() + " " + empleado.getApellidos() + " " + empleado.getEstado());
            }
        } else
            System.out.println("No hay empleados que mostrar");

    }
}
