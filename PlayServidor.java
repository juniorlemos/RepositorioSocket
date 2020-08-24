package repeticao;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
public class PlayServidor {
 
    private static ServerSocket serverSocket;
    private static Socket cliente = null;
 
    public static void main(String[] args) throws IOException {
 
        serverSocket = new ServerSocket(55555);
        System.out.println("Servidor Iniciado.");
 
        while (true) {
 
            cliente = serverSocket.accept();
            System.out.println("Conexão realizada IP  " + cliente.getInetAddress());
 
            Thread t = new Thread(new TesteServidorT(cliente));
 
            t.start();
 
        }
    }
}