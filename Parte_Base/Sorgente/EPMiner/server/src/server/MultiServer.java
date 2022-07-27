package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *   La classe ha il compito di gestire molteplici richieste dai client grazie al multy-threading.
 */
public class MultiServer {

    /**
     * Porta del server.
     */
    public static final int PORT = 8080;

    /**
     * Istanzia l'oggetto relativo alla classe.
     * @param args  Argomenti per l'avvio del programma.
     * @throws IOException eccezione per gli I/O
     */
    public static void main(String[] args) throws IOException {
        MultiServer ms = new MultiServer();
    }

    /**
     * Avvia il metodo run per il multy-threding.
     * @throws IOException Eccezzione Input/Output
     */
    MultiServer() throws IOException {
        run();
    }


    /**
     * Il seguente metodo attende la richiesta di connessione di un client e poi avvia la singola istanza di connessione con il client stesso.
     * @throws IOException Eccezzione Input/Output
     */
    private void run() throws IOException {

        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server avviato");

        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    System.out.println("Connesso con --> " + socket.getInetAddress() + " - port=" + socket.getPort());
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    socket.close();
                }
            }
        }
        finally{
            s.close();
        }
    }
}