package com.company;

import javax.swing.*;

public class metadatosVentana extends JFrame {
    private JPanel metadatosJPanel;
    private JLabel labelMetadatos;

    public metadatosVentana(int tipoDB){
        add(metadatosJPanel);
        setTitle("Metadatos De");
        setSize(400, 200);
    }

    private String swithDB(int DB){
        switch (DB){
            case 1:
                return "";

            case 2:
                return "";

            case 3:
                return "";

            default:
                return "";
                
        }
    }

}
