/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploedcloned;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jbautista
 */
public class EjemploEDCloned extends JFrame{

     public EjemploEDCloned() {
        JLabel lblSaludo = new JLabel("Hola clase, ¿Coomo estáis?");
        add(lblSaludo);
        this.setSize(300,300);
        this.setTitle("Prueba Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EjemploEDCloned ej = new EjemploEDCloned();
        
    }
    
}
