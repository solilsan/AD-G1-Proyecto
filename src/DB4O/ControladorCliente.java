package DB4O;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import Clases.Cliente;

public class ControladorCliente {

  final static String BD = ".\\DB\\AgenciaDB4O.yap";

  public static void guardaCliente(Cliente c) {

    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    db.store(c);

    db.close();
  }

  public static void eliminaCliente(Cliente c) {

    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

    db.delete(c);

    db.close();
  }

}






