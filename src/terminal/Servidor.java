package terminal;

import terminal.HiloServidorTerminal;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {

    public static Scanner ns = new Scanner(System.in);
    public static final long serialVersionUlD = 1L;
    public static ServerSocket servidor;
    public static final int PUERTO = 44444;
    public static int CONEXIONES = 0;
    public static int ACTUALES = 0; // n° de conexiones actuales activas

    static ArrayList<Socket> tabula = new ArrayList<>();

    public static void log() {
        System.out.println("Iniciando servidor");
    }

    public static void main(String[] args) {

        try {
            servidor = new ServerSocket(PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("Servidor iniciado... ");

        while (true) {

            Socket s = new Socket();

            try {
                s = servidor.accept();
            } catch (SocketException ns) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            tabula.add(s);
            CONEXIONES++;
            ACTUALES--;

            HiloServidorTerminal hilo = new HiloServidorTerminal(s);
            hilo.start();

        }

        if (!servidor.isClosed()) {
            try {
                // sale cuando se llega al máximo de conexiones
                System.out.println("Sobrecarga, cerrando servidor");
                servidor.close();
            } catch (IOException el) {
                el.printStackTrace();
                System.out.println("Servidor finalizado.");
            }
        } // if

    }
}
