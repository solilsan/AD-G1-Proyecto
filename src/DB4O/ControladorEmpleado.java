package DB4O;

import Clases.Cliente;
import Clases.Empleado;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

public class ControladorEmpleado {

    final static String BD = ".\\DB\\AgenciaDB4O.yap";

    public static String guardaEmpleado(Empleado e) {

        //Conexion con la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
        ObjectSet<Empleado> resultado = db.queryByExample(new Empleado(e.getDni(), null, null,
                null, null, null, null, null, null));


        //Se busca el cliente a guardar, si existe marcamos atributo "alta" y lo volvemos a guardar
        if (resultado.hasNext()) {
            Empleado empleado = resultado.next();
            empleado.setEstado("alta");
            db.store(empleado);
        } else {
            db.store(e);
        }
        db.close();
        return e.getNombre() + " " + e.getApellidos() + " Guardado!";
    }

    public static String eliminaEmpleado(Empleado e) {

        String mensaje = e.getNombre() + " " + e.getApellidos() + " Eliminado!";

        //Conexion con la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
        ObjectSet<Empleado> resultado = db.queryByExample(new Empleado(e.getDni(),null, null,
                null, null, null, null,null,null));

        //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
        if (resultado.size() < 1)
            mensaje = "Error " + e.getNombre() + " " + e.getApellidos() + " No está en la base de datos";
        else if (resultado.size() > 1)//Por si acaso
            mensaje = "Error " + e.getNombre() + " " + e.getApellidos() + " está Repetido en la base de datos";
        else {
            if (resultado.hasNext()) {
                Empleado empleado = resultado.next();
                empleado.setEstado("baja");
                db.store(empleado);
            }
        }
        db.close();
        return mensaje;
    }

    public static Empleado buscaEmpleado(String dni) {

        Empleado emp = null;

        //Conexion con la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
        ObjectSet<Empleado> resultado = db.queryByExample(new Empleado(dni, null, null,
                null,null, null, null,null,null));

        if (resultado.hasNext())
            emp = resultado.next();

        db.close();

        return emp;

    }


    public static List<Empleado> mostrarEmpleados() {

        List<Empleado> listaEmpleados = new ArrayList<>();

        //Conexion con la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        //se obtienen todos los clientes que coincidan con los datos del objeto (deberian ser todos)
        ObjectSet<Empleado> resultado = db.queryByExample(new Empleado(null,null, null,
                null, null, null, null,null,null));

        if (resultado.size() > 0) {
            //Se recorren todos los clientes de la base de datos
            while (resultado.hasNext()) {
                Empleado empleado = resultado.next();
                if (empleado.getEstado().equals("alta"))
                    listaEmpleados.add(empleado);
            }
        }

        db.close();
        return listaEmpleados;
    }

    public static String actualizaEmpleado(Empleado e) {

        String mensaje = e.getNombre() + "Actualizado!";

        //Conexion con la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        //se obtienen todos los clientes que coincidan con los datos del objeto (deberia haber solo 1)
        ObjectSet<Empleado> resultado = db.queryByExample(new Empleado(e.getDni(),null, null,
                null, null, null, null,null,null));

        //Se busca el cliente a eliminar, si existe marcamos atributo "baja" y lo volvemos a guardar
        if (resultado.size() < 1)
            mensaje = "Error " + e.getNombre() + " " + e.getApellidos() + " No está en la base de datos";
        else if (resultado.size() > 1)//Por si acaso
            mensaje = "Error " + e.getNombre() + " " + e.getApellidos() + " está Repetido en la base de datos";
        else {
            if (resultado.hasNext()) {
                Empleado empleado = resultado.next();
                //Actualiza el nombre
                empleado.setNombre(e.getNombre());
                //Actualiza los apellidos
                empleado.setApellidos(e.getApellidos());
                //Actualiza la fecha de nacimiento
                empleado.setFechaNacimiento(e.getFechaNacimiento());
                //Actualiza la fecha de contratación
                empleado.setFechaContratacion(e.getFechaContratacion());
                //Actualiza la nacionalidad
                empleado.setNacionalidad(e.getNacionalidad());
                //Actualiza el cargo
                empleado.setCargo(e.getCargo());
                //Actualiza la contraseña
                empleado.setPassword(e.getPassword());

                //Se guarda el empleado
                db.store(empleado);
            }
        }
        db.close();
        return mensaje;
    }
}