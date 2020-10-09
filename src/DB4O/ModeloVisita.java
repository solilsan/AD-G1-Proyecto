package DB4O;

import Clases.Visita;

import java.util.List;

public class ModeloVisita {

    public static String guardar(Visita vis) {
        return ControladorVisita.guardaVisita(vis);
    }

    public static String eliminar(Visita vis) {
        return ControladorVisita.eliminaVisita(vis);
    }

    public static String actualiza(Visita vis) {
        return ControladorVisita.actualizaVisita(vis);
    }

    public static Visita buscar(Integer dni){return ControladorVisita.buscaVisista(dni);}

    public static List<Visita> mostrar() {
        return ControladorVisita.mostrarVisistas();
    }


}
