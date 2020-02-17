/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.tcp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author jorge
 */
public class ServidorChat extends javax.swing.JFrame implements ActionListener, Runnable {

    private TCPServer tcps;
    private JTextField mensaje = new JTextField("");
    private JTextField mensaje2 = new JTextField("");
    private JScrollPane scrollpanel;
    private JTextArea textarea;
    
    //Variables añadidas a la solución
    private static int CONEXIONES = 0;
    private static int ACTUALES = 0;
    public static final int NUMMAXCONEX = 10;
    public static Socket tabla[] = null;

    public TCPServer getTcps() {
        return tcps;
    }

    public JTextField getMensaje() {
        return mensaje;
    }

    public void setMensaje(JTextField mensaje) {
        this.mensaje = mensaje;
    }

    public JTextField getMensaje2() {
        return mensaje2;
    }

    public void setMensaje2(JTextField mensaje2) {
        this.mensaje2 = mensaje2;
    }

    public JTextArea getTextarea() {
        return textarea;
    }

    public void setTextarea(JTextArea textarea) {
        this.textarea = textarea;
    }

    JButton salir = new JButton("Salir");
    /**
     * Creates new form ServidorChat1
     */
    public ServidorChat(int puerto) {
        super(" VENTANA DEL SERVIDOR DE CHAT ");
        initComponents();
        //Se inicializa el array de conexiones
        tabla = new Socket[10];
        try {
            tcps = new TCPServer(puerto);
            setLayout(null);
            mensaje.setBounds(10, 10, 400, 30);
            add(mensaje);

            mensaje.setEditable(false);
            mensaje2.setBounds(10, 348, 400, 30);
            add(mensaje2);

            mensaje2.setEditable(false);
            textarea = new JTextArea();
            scrollpanel = new JScrollPane(textarea);

            scrollpanel.setBounds(10, 50, 400, 300);
            add(scrollpanel);

            salir.setBounds(420, 10, 100, 30);
            add(salir);

            textarea.setEditable(false);
            salir.addActionListener(this);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "IMPOSIBLE ARRANCAR SERVIDOR\n" + ex.getMessage(), " ERROR : ", JOptionPane.ERROR_MESSAGE);
        }

    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == salir) { 
            try {
                tcps.close();
            } catch (IOException e1) {
                System.out.println(e1);
            }

            this.dispose();
        }
    } //

    @Override
    public void run() {
        String historia = "";
        try {
            historia = getTextarea().getText();
            getTcps().envio(historia);
        } catch (IOException ex) {
            Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean repetir = true;
        while (repetir) {
            historia = "";
            try {
                historia = historia + "\n" + getTcps().recepcion();
                //cuando un cliente finaliza, envía un *
                if(historia.trim().equals("*")) {
                    if(ServidorChat.ACTUALES > 0) {
                        ServidorChat.ACTUALES--;
                    }
                    break;
                }                
                //Se envían los mensajes
                getTextarea().append(historia + "\n");
                // getTextarea().setText(historia);
                historia = getTextarea().getText();
                getTcps().envio(historia);
            } catch (IOException ex) {
                if (repetir) {
                    JOptionPane.showMessageDialog(null,
                            "DETENCIÓN DEL SERVIDOR SERVIDOR\n" + ex.getMessage(), " ERROR : ", JOptionPane.ERROR_MESSAGE);
                }
                repetir = false;
                this.dispose();
            }
        }
    }

    public static void main(String args[]) throws IOException {

        int puerto;
        System.out.println("Servidor iniciado...");
        do {
            puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce puerto:"));
        } while (puerto < 1 || puerto > 65535);

        ServidorChat sChat = new ServidorChat(puerto);
        sChat.setBounds(0, 0, 540, 400);
        sChat.setVisible(true);
        
//        sChat.getTcps().aceptarConexion();
//        sChat.getMensaje().setText("NUMERO DE CONEXIONES ACTUALES: " + 0);
//        new Thread(sChat).start();

        // SE ADMITEN HASTA 10 CONEXIONES 
        //Solución
        while (CONEXIONES < NUMMAXCONEX) {
            Socket s = new Socket();
            try {
                //Esperando al cliente
                s = sChat.getTcps().getsSocket().accept();
                
                //Establecemos el socket en el servidor
                sChat.getTcps().setSocket(s);
                
                //Se Crea el flujo de entrada
                sChat.getTcps().setFentrada(new DataInputStream(s.getInputStream()));
                
                tabla[CONEXIONES] = s; //Se almacena el socket
                CONEXIONES++;
                ACTUALES++;
                new Thread(sChat).start();
            } catch(SocketException e) {
                //Si se pulsa el botón salir no se ejecuta el bucle
                break; //Sale del bucle
            }
            
        }
        
        //Se comprueba si el servidor está cerrado
        if(!sChat.getTcps().getsSocket().isClosed()) {
            try {
                //Sale cuando el cliente llega al máximo de conexiones
                sChat.getMensaje2().setForeground(Color.red);
                sChat.getMensaje2().setText("Máximo número de conexiones "
                        + "establecidas: " + CONEXIONES);
                sChat.getTcps().getsSocket().close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            System.out.println("Servidor finalizado");
        }
    }//main 
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - unknown
    private void initComponents() {

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGap(0, 400, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGap(0, 300, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ServidorChat1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ServidorChat1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ServidorChat1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ServidorChat1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ServidorChat1().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    // End of variables declaration//GEN-END:variables
}
