/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Jorge
 */
class TCPClient {

    Socket socket; // streams
    int puerto;
    String host;
    DataInputStream fentrada;
    DataOutputStream fsalida;
    String nombre;

    TCPClient(String host, int puerto, String nombre) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.host = host;
        this.socket = new Socket();
        this.fentrada = null;
        this.fsalida = null;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public DataInputStream getFentrada() {
        return fentrada;
    }

    public void setFentrada(DataInputStream fentrada) {
        this.fentrada = fentrada;
    }

    public DataOutputStream getFsalida() {
        return fsalida;
    }

    public void setFsalida(DataOutputStream fsalida) {
        this.fsalida = fsalida;
    }
    
    
//iniciar el socket y los flujos asocidos y escribir un mensaje de bienbenida al chat

    void conectar() throws IOException {
        //tu código aquí
        this.socket = new Socket(this.host, this.puerto);
        
        try {        
            //Se inicia el flujo de entrada y de salida
            this.fentrada = new DataInputStream(this.socket.getInputStream());
            this.fsalida = new DataOutputStream(this.socket.getOutputStream());
            String texto = " > Entra en el Chat ..." + this.nombre;
            this.fsalida.writeUTF(texto); //Escribe mensaje de entrada
        } catch (IOException e) {
            System.out.println("Error de E/S");
            e.printStackTrace();
            System.exit(0);
        }
    }
//escribir el mensaje por el socket

    void enviar(String texto) throws IOException {
        //tu código aquí
        try {
        this.fsalida.writeUTF(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//escribir un mensaje de despedida y cerrar el socket

    void desConectar() throws IOException {
        //tu código aquí
        String texto = nombre + "> Abandona el chat... " + this.nombre;
        
        try {
            this.fsalida.writeUTF(texto);
            this.fsalida.writeUTF("*");
            this.socket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
//leer mensaje del flujo de entrada y devolverlo
    String recibir() throws IOException {
        //tu código aquí
        String texto = "";
        try {
            texto = this.fentrada.readUTF();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return texto;
    }

}
