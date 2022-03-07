package terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static Scanner ns = new Scanner(System.in);
    public static String nombre;
    public static DataInputStream fentrada;
    public static DataOutputStream fsalida;
    public static String mensajes;

    private static final long serialVersionULD = 1;

    public static void enviarMensaje() {

        System.out.println("Introduce tu mensaje: ");
        String msg = ns.nextLine();

        String finalMsg = nombre + "> " + msg;

        try {
            fsalida.writeUTF(finalMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerMensajes() {
        System.out.println("Leyendo: ");
        try {
            mensajes = fentrada.readUTF();
            System.out.println(mensajes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exit() {

        String msg = "> Abandona el chat: " + nombre;

        try {
            fsalida.writeUTF(msg);
            fsalida.writeUTF("*");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        int puerto = 44444;

        System.out.println("Introduce tu nombre: ");
        nombre = ns.nextLine();

        Socket s = null;

        try {
            // grafico.cliente y servidor se ejecutan en la máquina local
            // InetAddress.getByName("10.101.19.124");
            //EN EL CLIENTE USE la de arriba
            s = new Socket("localhost", puerto);

            fentrada = new DataInputStream(s.getInputStream());
            fsalida = new DataOutputStream(s.getOutputStream());

        } catch (IOException e) {
            System.out.println("Imposible conectarse con el servidor");
            e.printStackTrace();
            System.exit(0);
        }

        fsalida.writeUTF("> Entra en el chat: " + nombre );

        if (!nombre.trim().equals("")) {

            int op;

            while(true) {

                System.out.println("Elegir una opcion: " +
                        "\n\t - 1: mandar un mensaje" +
                        "\n\t - 2: ver los mensajes mandados");

                op = ns.nextInt();

                if (op == 1){
                    ns.nextLine();
                    enviarMensaje();
                } else if (op == 2) {
                    ns.nextLine();
                    leerMensajes();
                } else {
                    exit();
                    break;
                }
            }
            s.close();
        } else {
            System.out.println("Nombre no valido");
        }
    }
}
