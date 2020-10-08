package DB4O;

import Clases.Cliente;

import java.util.List;

public class ModeloCliente {

    public static String guardar(Cliente cli) {
        return ControladorCliente.guardaCliente(cli);
    }

    public static String eliminar(Cliente cli) {
        return ControladorCliente.eliminaCliente(cli);
    }

    public static String actualiza(Cliente cli) {
        return ControladorCliente.actualizaCliente(cli);
    }

    public static Cliente buscar(String dni){return ControladorCliente.buscaCliente(dni);}

    public static List<Cliente> mostrar() {
        return ControladorCliente.mostrarClientes();
    }


}
