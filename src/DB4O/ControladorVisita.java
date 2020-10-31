package DB4O;

import Clases.Cliente;
import Clases.Visita;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import java.util.ArrayList;
import java.util.List;


public class ControladorVisita {

  final static String BD = ".\\DB\\AgenciaDB4O.yap";

  public static String guardaVisita(Visita v) {

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos las visitas que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(v.getId(), null, null, null,
        null, null, null, null, null, null));


    //Se busca la visita a guardar, si existe marcamos atributo "alta" y lo volvemos a guardar
    if (resultado.hasNext()) {
      Visita visita = resultado.next();
      visita.setEstado("alta");
      db.store(visita);
    } else {
      db.store(v);
    }
    db.close();
    return v.getNombre() + " Guardado!";
  }

  public static String eliminaVisita(int idVisita) {

    String mensaje = "Visita Eliminada!";

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(idVisita, null, null,
        null, null, null, null, null, null, null));

    if (resultado.hasNext()) {
      Visita visita = resultado.next();
      visita.setEstado("baja");
      db.store(visita);
    }

    db.close();
    return mensaje;
  }

  public static Visita buscaVisista(Integer id) {

    Visita vis = null;

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(id, null, null,
        null, null, null, null, null, null, null));

    if (resultado.hasNext())
      vis = resultado.next();

    db.close();

    return vis;

  }


  public static List<Visita> mostrarVisistas() {

    List<Visita> listaVisitas = new ArrayList<>();

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(null, null, null,
        null, null, null, null, null, null, null));

    if (resultado.size() > 0) {
      //Se recorren todos los clientes de la base de datos
      while (resultado.hasNext()) {
        Visita visita = resultado.next();
        listaVisitas.add(visita);
      }
    }

    db.close();
    return listaVisitas;
  }

  public static List<Visita> mostrarVisitasAlta() {

    List<Visita> listaVisitas = new ArrayList<>();

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(null, null, null,
        null, null, null, null, null, null, null));

    if (resultado.size() > 0) {
      //Se recorren todos los clientes de la base de datos
      while (resultado.hasNext()) {
        Visita visita = resultado.next();
        if (visita.getEstado().equals("alta"))
          listaVisitas.add(visita);
      }
    }

    db.close();
    return listaVisitas;
  }

  public static String actualizaVisita(Visita v) {

    String mensaje = "Visita " + v.getNombre() + "Actualizada!";

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
    ObjectSet<Visita> resultado = db.queryByExample(new Visita(v.getId(), null, null,
        null, null, null, null, null, null, null));

    //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
    if (resultado.size() < 1)
      mensaje = "Error Visita " + v.getNombre() + " No está en la base de datos";
    else if (resultado.size() > 1)//Por si acaso
      mensaje = "Error Visita " + v.getNombre() + " está Repetida en la base de datos";
    else {
      if (resultado.hasNext()) {
        Visita visita = resultado.next();
        //Actualiza el nombre
        visita.setNombre(v.getNombre());
        //Actualiza numero maximo de clientes
        visita.setNmaxCli(v.getNmaxCli());
        //Actualiza punto de partida
        visita.setPuntoPartida(v.getPuntoPartida());
        //Actualiza curso academico
        visita.setCursoAcademico(v.getCursoAcademico());
        //Actualiza la tematica
        visita.setTematica(v.getTematica());
        //Actualiza el coste
        visita.setCoste(v.getCoste());
        //Actualiza fecha y hora
        visita.setFecha_hora(v.getFecha_hora());
        //Actualiza el empleado
        visita.setEmpleado(v.getEmpleado());

        //Se guarda el empleado
        db.store(visita);
      }
    }
    db.close();
    return mensaje;
  }

  public static Visita apuntaCliente(int id, String dni) {

    String mensaje = "";
    Visita vis;
    Cliente cli;

    EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();

    config.common().objectClass("Clases.Visita").cascadeOnUpdate(true);

    //Conexion con la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(config, BD);

    //se obtiene la visita que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Visita> resultadoVisita = db.queryByExample(new Visita(id, null, null,
        null, null, null, null, null, null, null));

    vis = resultadoVisita.next();

    //se obtiene el cliente que coincidan con los datos del objeto (deberian ser todos)
    ObjectSet<Cliente> resuladoCliente = db.queryByExample(new Cliente(dni, null, null, null,
        null, null, null));

    cli = resuladoCliente.next();

    //Se apunta el cliente a la visita
    vis.getClientes().add(cli);

    cli.getVisitas().add(vis);


    //Se guarda el empleado
    db.store(vis);


    //cierra conexion
    db.close();

    return vis;

  }
}