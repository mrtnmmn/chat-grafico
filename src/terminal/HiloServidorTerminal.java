package terminal;

import grafico.ServidorChat;
import terminal.Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloServidorTerminal extends Thread {

    DataInputStream fentrada;
    Socket socket = null;

    public HiloServidorTerminal(Socket s) {
        socket = s;
        try {
            // CREO FLUJO DE ENTRADA
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        } //

    }// constr


    // ENVIA LOS MENSAJES DEL TEXTAREA A LOS CLIENTES DEL CHAT
    private void EnviarMensajes(String texto) {
        int i;
        // recorremos tabla de sockets para enviarles los mensajes
        for (i = 0; i < Servidor.CONEXIONES; i++) {
            //ojo el socket esta en un array dianmico
            Socket si = Servidor.tabula.get(i);
            // obtener socket
            try {
                DataOutputStream fsalida = new DataOutputStream(si.getOutputStream());
                fsalida.writeUTF(texto);
                // escribir en el socket el texto
            } catch (SocketException se) {
                // esta excepción ocurre cuando escribimos en un socket
                // de un cliente que ha finalizado
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // for
    }// EnviarMensajes

}