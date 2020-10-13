package com.company;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {

    int DB4O = 1;
    int mysqlite = 2;
    int mysql = 3;

    /*clienteVentana cv = new clienteVentana();
    cv.setVisible(true);*/

    /*
    empleadoVentana ev = new empleadoVentana(DB4O);
    ev.setLocationRelativeTo(null); // para poner la ventana en el centro de la pantalla
    ev.setVisible(true);
    */

    /*visitaVentana vv = new visitaVentana();
    vv.setVisible(true);*/

    principalVentana pv = new principalVentana();
    pv.setLocationRelativeTo(null);
    pv.setVisible(true);

  }
}
