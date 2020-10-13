package MySql;

import com.company.principalVentana;

import javax.swing.*;

public class MySqlVentanaPrincipal extends JFrame{
    private JPanel panel1;

    public MySqlVentanaPrincipal (){

        add(panel1);

        setTitle("Agencia MySql");

        setSize(400, 300);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                // Crear la nueva venta para abrir
                principalVentana pv = new principalVentana(){};
                pv.setLocationRelativeTo(null);
                pv.setVisible(true);
                // Cerrar ventana actual
                dispose();

            }
        });

    }

}
