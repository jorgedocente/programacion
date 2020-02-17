/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Jorge
 */
public class TCPServer {

    DataInputStream fentrada;
    DataOutputStream fsalida;
    ServerSocket sSocket;
    Socket socket;
    String historia;
    private final int puerto;

    //iniciar el socket servidor
    TCPServer(int puerto) throws IOException {
        this.puerto = puerto;

        //tu código aquí
        sSocket = new ServerSocket(puerto);
        socket = new Socket();
        fentrada = null;
        fsalida = null;

        historia = "";

    }

    //Getters and Setters
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

    public ServerSocket getsSocket() {
        return sSocket;
    }

    public void setsSocket(ServerSocket sSocket) {
        this.sSocket = sSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    //iniciar los flujos y el socket
    /**
     * Espera al cliente. Acepta la conexión del Server socket (sSocket)
     *
     * @throws IOException
     */
    public void aceptarConexion() throws IOException {
        this.sSocket.accept();
    }

    //cerrar el socketServidor
    void close() throws IOException {
        this.sSocket.close();
    }
//leer mensaje del socket y devolverlo

    String recepcion() throws IOException {
        //tu código aquí
        return this.fentrada.readUTF();
    }

    //enviar mensaje por el socket
    void envio(String historia) throws IOException {
        Socket s = null;
        for (int i = 0; i < ServidorChat.NUMMAXCONEX; i++) {
            if (ServidorChat.tabla[i] != null) {
                s = ServidorChat.tabla[i];
                try {
                    this.fsalida = new DataOutputStream(s.getOutputStream());
                    this.fsalida.writeUTF(historia);
                } catch(SocketException e) {
                    //Excepción que ocurre cuando un cliente ha finalizado
                    e.getMessage();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
