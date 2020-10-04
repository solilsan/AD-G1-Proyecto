package DB4O;

import Clases.Cliente;

import java.util.List;

public class ModeloCliente {

  public static String guardar(Cliente c1) {
    return ControladorCliente.guardaCliente(c1);
  }

  public static String eliminar(Cliente c1) {
    return ControladorCliente.eliminaCliente(c1);
  }

  public static List<Cliente> mostrar(){
    return ControladorCliente.mostrarClientes();

  }


}
