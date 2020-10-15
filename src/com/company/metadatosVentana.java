package com.company;

import SQLite.Metadatos;

import javax.swing.*;

public class metadatosVentana extends JFrame {
    private JPanel metadatosJPanel;
    private JLabel labelMetadatos;

    public metadatosVentana(int tipoDB){
        add(metadatosJPanel);
        setTitle("Metadatos De "+ swithDB(tipoDB));
        setSize(400, 200);
    }

    private String swithDB(int DB){
        switch (DB){
            case 1:// DB4O
                // TODO: Devolver los metadatos de la base de datos.
                labelMetadatos.setText("");
                return "DB4o";

            case 2:// SQLite
                labelMetadatos.setText(Metadatos.getMetadatos());
                return "SQLite";

            case 3://MySQL
                // TODO: Devolver los metadatos de la base de datos.
                labelMetadatos.setText("");
                return "MySQL";

            default:
                return "";
                
        }
    }

}
