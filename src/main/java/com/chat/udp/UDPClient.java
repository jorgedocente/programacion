/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Jorge
 */
class UDPClient {

    MulticastSocket msSocket;
    InetAddress grupo;
    int puerto;
    String host;
    byte[] buf;
    String nombre;

    UDPClient(String host, int puerto, String nombre) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.host = host;
        this.msSocket = null;
        this.buf = new byte[1000];
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public MulticastSocket getMsSocket() {
        return msSocket;
    }

    public void setMsSocket(MulticastSocket socket) {
        this.msSocket = socket;
    }

    public InetAddress getGrupo() {
        return grupo;
    }

    public void setGrupo(InetAddress grupo) {
        this.grupo = grupo;
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
    
//iniciar el socket y los flujos asocidos y escribir un mensaje de bienbenida al chat

    void conectar() throws IOException {
        //Se crea el socket
        this.msSocket = new MulticastSocket(this.puerto);
        this.grupo = InetAddress.getByName(this.host);
        
        //Se une al grupo
        this.msSocket.joinGroup(grupo);
    }
//escribir el mensaje por el socket

    void enviar(String texto) throws IOException {
        //tu código aquí
        try {
            DatagramPacket paquete = new DatagramPacket(texto.getBytes(), 
                    texto.length(), this.grupo, this.puerto);
            this.msSocket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//escribir un mensaje de despedida y cerrar el socket

    void desConectar() throws IOException {
        //tu código aquí
        String texto = nombre + ">> Abandona el chat... " + this.nombre;
        
        try {
            DatagramPacket paquete = new DatagramPacket(texto.getBytes(), 
                    texto.length(), this.grupo, this.puerto);
            this.msSocket.send(paquete);
            this.msSocket.leaveGroup(grupo);
            this.msSocket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
//leer mensaje del flujo de entrada y devolverlo
    String recibir() throws IOException {
        //tu código aquí
        String texto = "";
        try {
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            this.msSocket.receive(p);
            texto = new String(p.getData(), 0, p.getLength());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return texto;
    }

}
