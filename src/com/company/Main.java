package com.company;

public class Main {

  public static void main(String[] args) {

    int DB4O = 1;
    int mysqlite = 2;
    int mysql = 3;
    // pruebav pv = new pruebav();
    //pv.setVisible(true);

    /*clienteVentana cv = new clienteVentana();
    cv.setVisible(true);*/

    empleadoVentana ev = new empleadoVentana(DB4O);
    ev.setVisible(true);

    /*visitaVentana vv = new visitaVentana();
    vv.setVisible(true);*/

  }
}
