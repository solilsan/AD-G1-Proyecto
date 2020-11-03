package DB4O;

import Clases.Visita;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import Clases.Cliente;
import com.db4o.ObjectSet;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class ControladorCliente {

  final static String BD = ".\\DB\\AgenciaDB4O.yap";

  public static String guardaCliente(Cliente c) {

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(c.getDni(),
        null, null, null, null, null, null));


    //Se busca el cliente a guardar, si existe marcamos atributo "alta" y lo volvemos a guardar
    if (resultado.hasNext()) {
      Cliente cliente = resultado.next();
      cliente.setEstado("alta");
      db.store(cliente);
    } else {
      db.store(c);
    }
    db.close();
    return c.getNombre() + " " + c.getApellidos() + " Guardado!";
  }

  public static String eliminaCliente(Cliente c) {

    String mensaje = c.getNombre() + " " + c.getApellidos() + " Eliminado!";

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(c.getDni(),
        null, null, null, null, null, null));

    //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
    if (resultado.size() < 1)
      mensaje = "Error " + c.getNombre() + " " + c.getApellidos() + " No está en la base de datos";
    else if (resultado.size() > 1)//Por si acaso
      mensaje = "Error " + c.getNombre() + " " + c.getApellidos() + " está Repetido en la base de datos";
    else {
      if (resultado.hasNext()) {
        Cliente cliente = resultado.next();
        cliente.setEstado("baja");
        db.store(cliente);
      }
    }
    db.close();
    return mensaje;
  }

  public static Cliente buscaCliente(String dni) {

    Cliente cli = null;

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(dni, null, null, null,
        null, null, null));

    if (resultado.hasNext())
      cli = resultado.next();

    db.close();

    return cli;

  }

  public static List<Cliente> mostrarClientes() {

    List<Cliente> listaClientes = new ArrayList<>();

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(null,
        null, null, null, null, null, null));

    if (resultado.size() > 0) {
      //Se recorren todos los clientes de la base de datos
      while (resultado.hasNext()) {
        Cliente cliente = resultado.next();
        listaClientes.add(cliente);
      }

    }

    db.close();
    return listaClientes;
  }


  public static String actualizaCliente(Cliente c) {
    String mensaje = c.getNombre() + "Actualizado!";

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Cliente> resultado = db.queryByExample(new Cliente(c.getDni(),
        null, null, null, null, null, null));

    //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
    if (resultado.size() < 1)
      mensaje = "Error " + c.getNombre() + " " + c.getApellidos() + " No está en la base de datos";
    else if (resultado.size() > 1)//Por si acaso
      mensaje = "Error " + c.getNombre() + " " + c.getApellidos() + " está Repetido en la base de datos";
    else {
      if (resultado.hasNext()) {
        Cliente cliente = resultado.next();
        //Actualiza el nombre
        cliente.setNombre(c.getNombre());
        //Actualiza los apellidos
        cliente.setApellidos(c.getApellidos());
        //Actualiza la fecha de nacimiento
        cliente.setFechaNacimiento(c.getFechaNacimiento());
        //Actualiza la profesion
        cliente.setProfesion(c.getProfesion());


        db.store(cliente);
      }
    }
    db.close();
    return mensaje;
  }
}