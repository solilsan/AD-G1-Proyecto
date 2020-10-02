package DB4O;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectSet;
import com.db4o.ObjectContainer;
import Clases.Clientes;

public class ControladorBBDD {

    final static String BD = "AgenciaDB4O.yap";


    public static void guardaCliente(Clientes cliente) {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        Clientes c1 = new Clientes("35575600M","Santi","Gonszalez","03/11/1984","informatico","activo");

        db.store(c1);
    }

}





