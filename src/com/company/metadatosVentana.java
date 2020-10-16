package com.company;

import SQLite.Metadatos;

import javax.swing.*;
import java.awt.*;

public class metadatosVentana extends JFrame {
    private JPanel metadatosJPanel;
    private JLabel labelMetadatos;

    public metadatosVentana(int tipoDB){
        add(metadatosJPanel);
        setTitle("Metadatos De "+ swithDB(tipoDB));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private String swithDB(int DB){
        switch (DB){
            case 1:// DB4O
                // TODO: Devolver los metadatos de la base de datos.
                labelMetadatos.setText("");
                return "DB4o";

            case 2:// SQLite
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double width = screenSize.getWidth();
                double height = screenSize.getHeight();

                labelMetadatos.setFont(new Font("Arial", Font.PLAIN, (int)width/50));

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
