package DB4O;

import Clases.Visita;

import java.util.List;

public class ModeloVisita {

    public static String guardar(Visita vis) {
        return ControladorVisita.guardaVisita(vis);
    }

    public static String eliminar(int idVisita) {
        return ControladorVisita.eliminaVisita(idVisita);
    }

    public static String actualiza(Visita vis) {
        return ControladorVisita.actualizaVisita(vis);
    }

    public static Visita buscar(Integer id){return ControladorVisita.buscaVisista(id);}

    public static List<Visita> mostrar() {
        return ControladorVisita.mostrarVisistas();
    }

    public static List<Visita> mostrarVisitasAlta() {return ControladorVisita.mostrarVisitasAlta();}


    public static Visita apuntar(int id, String dni) {return ControladorVisita.apuntaCliente(id, dni);}

    public static Visita desapuntar(int id, String dni) {
        return ControladorVisita.desApuntar(id, dni);
    }
}
