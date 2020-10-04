package com.company;

import Clases.Cliente;
import Clases.Empleado;
import Clases.Visita;
import DB4O.ModeloCliente;

public class Main {

    public static void main(String[] args) {

        //********** A falta de la Ventana temporalmente he creado estos datos de ejemplo *********//
        Empleado emp1 = new Empleado("35575601Y",
            "pablo",
            "perez",
            "03/11/1985",
            "05/06/2010",
            "española",
            "guia",
            "pass",
            "En activo");

        Visita v1 = new Visita(1,
            "inserso1",
            1,
            "lakua",
            "2020",
            "educativa",
            520,
            "en curso",
            emp1);

        Cliente c1 = new Cliente(
            "35575600M",
            "Santi",
            "Gonszalez",
            "03/11/1984",
            "informatico",
            "activo", v1);
        /////////////////////////////////////////////////////////////////////////////////////////////

        ModeloCliente.guardar(c1);
        ModeloCliente.eliminar(c1);

    }
}
