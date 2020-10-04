package DB4O;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import Clases.Cliente;
import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

public class ControladorCliente {

  final static String BD = ".\\DB\\AgenciaDB4O.yap";

  public static String guardaCliente(Cliente c) {

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Cliente> resultado = db.queryByExample(c);


    //Se busca el cliente a guardar, si existe marcamos atributo "alta" y lo volvemos a guardar
    if (resultado.hasNext()) {
      Cliente cliente = resultado.next();
      cliente.setEstado("alta");
      db.store(cliente);
    }
    else {
      db.store(c);
    }
    db.close();
    return c.getNombre() + " " + c.getApellidos() + " Guardado!";
  }

  public static String eliminaCliente(Cliente c) {

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Cliente> resultado = db.queryByExample(c);

    //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
    if (resultado.size() < 1)
      return "Error " + c.getNombre() + " " + c.getApellidos() + " No está en la base de datos";
    else if (resultado.size() > 1)//Por si acaso
      return "Error " + c.getNombre() + " " + c.getApellidos() + " está Repetido en la base de datos";
    else {
      if (resultado.hasNext()) {
        Cliente cliente = resultado.next();
        cliente.setEstado("baja");
        db.store(cliente);
      }
    }
    db.close();
    return c.getNombre() + " " + c.getApellidos() + " Eliminado!";
  }

  public static List<Cliente> mostrarClientes() {

    List<Cliente> listaClientes = new ArrayList<>();

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(null,
        null, null, null, null, null, null));

    //Se recorren todos los clientes de la base de datos
    while (resultado.hasNext()) {
      Cliente cliente = resultado.next();
      System.out.println(cliente.getNombre() + " " + cliente.getEstado());
      if (cliente.getEstado().equals("alta"))
        listaClientes.add(cliente);
    }

    db.close();
    return listaClientes;
  }

}






