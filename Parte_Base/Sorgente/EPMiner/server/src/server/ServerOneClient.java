package server;

import data.Data;
import data.EmptySetException;
import database.DatabaseConnectionException;
import database.NoValueException;
import mining.EmergingPatternMiner;
import mining.FrequentPatternMiner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * La classe ha il compito di gestire le singole istanze della connessione con il client
 *
 * @see MultiServer
 */
public class ServerOneClient extends Thread {

    /**
     * Socket del server
     */
    final private Socket socket;
    /**
     * Stream di input
     */
    final private ObjectInputStream in;
    /**
     * Stream di output
     */
    final private ObjectOutputStream out;

    /**
     * Inizializza gli attributi della classe ed invoca il metodo start per il multiThread.
     *
     * @param socket socket per la connessione con il client
     * @throws IOException eccezzione Input/Output
     */
    ServerOneClient(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream((socket.getInputStream()));
        this.out = new ObjectOutputStream(socket.getOutputStream());
        start();

    }

    /**
     * Il seguente metodo, è la porzione di codice che sarà avviata in multy-threding e si occupa della gestione di tutti gli I/O tra il client ed il server.
     */
    @Override
    public void run() {
        try {
            while (true) {
                try {
                    int opzione = (int) in.readObject();
                    float minsup = (float) in.readObject();
                    float minGr = (float) in.readObject();
                    String targetName = (String) in.readObject();
                    String backgroundName = (String) in.readObject();
                    String archive;
                    Data dataTarget;
                    Data dataBackground;
                    FrequentPatternMiner fpMiner;
                    String s;
                    System.out.println("Il client ha inserito i seguenti dati:\nMinSup: " + minsup + " MinGr: " + minGr + " TargetName: " + targetName + " TargetBackground: " + backgroundName);
                    if (opzione == 1) {
                        try {
                            dataTarget = new Data(targetName);
                            try {
                                fpMiner = new FrequentPatternMiner(dataTarget, minsup);
                                try {
                                    archive = ("FP_" + targetName + "_minSup" + minsup);
                                    fpMiner.salva(archive);
                                } catch (IOException e) {
                                    s = "Errore";
                                    System.out.println("Errore scrittura dati.");
                                    out.writeObject(s);
                                    out.writeObject(s);
                                }
                                s = fpMiner.toString();
                                out.writeObject(s);
                                try {
                                    dataBackground = new Data(backgroundName);
                                    try {
                                        EmergingPatternMiner epMiner = new EmergingPatternMiner(dataBackground, fpMiner, minGr);
                                        try {
                                            archive = "EP_" + backgroundName + "_minSup" + minsup + "_minGr" + minGr;
                                            epMiner.salva(archive);
                                        } catch (IOException e) {
                                            s = "Errore";
                                            out.writeObject(s);
                                            System.out.println("Errore scrittura dati.");
                                        }
                                        s = epMiner.toString();
                                        out.writeObject(s);
                                    } catch (EmptySetException e) {
                                        s = "Errore";
                                        out.writeObject(s);
                                        System.out.println("Errore, i dati inseriti dall'client + " + socket + " non hanno prodotto risultati.");
                                    }
                                } catch (SQLException e) {
                                    s = "Errore";
                                    out.writeObject(s);
                                    System.out.println("Errore ricerca dati.");
                                }
                            } catch (EmptySetException e) {
                                s = "Errore";
                                out.writeObject(s);
                                System.out.println("Errore, i dati inseriti dall'client + " + socket + " non hanno prodotto risultati.");
                                out.writeObject(s);
                            }
                        } catch (SQLException e) {
                            s = "Errore";
                            System.out.println("Errore ricerca dati.");
                            out.writeObject(s);
                            out.writeObject(s);
                        }
                    }
                    if (opzione == 2) {
                        try {
                            archive = "FP_" + targetName + "_minSup" + minsup;
                            fpMiner = FrequentPatternMiner.carica(archive);
                            s = fpMiner.toString();
                        } catch (ClassNotFoundException | IOException e) {
                            s = "Errore";
                            System.out.println("Errore, dati non presenti in archivio.");
                        }
                        out.writeObject(s);
                        try {
                            archive = "EP_" + backgroundName + "_minSup" + minsup + "_minGr" + minGr;
                            EmergingPatternMiner epMiner = EmergingPatternMiner.carica(archive);
                            s = epMiner.toString();
                        } catch (ClassNotFoundException | IOException e) {
                            s = "Errore";
                            System.out.println("Errore, dati non presenti in archivio.");
                        }
                        out.writeObject(s);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | NoValueException | DatabaseConnectionException e) {
            System.out.println("Connessione interrotta con il client: " + socket);
        } finally {
            try {
                socket.close();
                System.out.println("Chiusura socket.");
            } catch (IOException e) {
                System.out.println("Socket non chiuso correttamente.");
            }
        }
    }
}
