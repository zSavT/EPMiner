package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Classe che gestisce le azioni dell'interfaccia utente
 */
public class Controller {
    /**
     * Attributo di input per il sever
     */
    private ObjectInputStream in;

    /**
     * Attributo di output per il sever
     */
    private ObjectOutputStream out;


    /**
     * ------------------------------------
     * Componenti dell'interfaccia grafica
     * ------------------------------------
     */

    /**
     * textField in cui verrà inserito dall'utente il minimo supporto
     */
    @FXML
    private TextField textFieldMinSupp;

    /**
     * textField in cui verrà inserito dall'utente il minimo GrowRate
     */
    @FXML
    private TextField textFieldMinGr;

    /**
     * textField in cui verrà inserita dall'utente la tabella target
     */
    @FXML
    private TextField textFieldTabTarget;

    /**
     * textField in cui verrà inserita dall'utente la tabella background
     */
    @FXML
    private TextField textFieldTabBackground;

    /**
     * textField in cui verrà inserito dall'utente l'indirizzo ip a cui connettersi
     */
    @FXML
    private TextField textFieldIndirizzoIp;

    /**
     * textField in cui verrà inserito dall'utente la porta a cui connettersi
     */
    @FXML
    private TextField textFieldPorta;

    /**
     * label del minimo supporto
     */
    @FXML
    private Label labelStatusMinSupp;

    /**
     * label del minimo GrowRate
     */
    @FXML
    private Label labelStatusMinGr;

    /**
     * label dell'errore di connessione
     */
    @FXML
    private Label labelErrorConn;

    /**
     * label ip errato
     */
    @FXML
    private Label labelIP;

    /**
     * label porta errata
     */
    @FXML
    private Label labelPorta;

    /**
     * label table target errata
     */
    @FXML
    private Label labelStatusTabTarget;

    /**
     * label table background errata
     */
    @FXML
    private Label labelStatusTabBackground;

    /**
     * textArea che conterrà i FP rusiltati della ricerca
     */
    @FXML
    private TextArea textAreaFP;

    /**
     * textArea che conterrà gli EP rusiltati della ricerca
     */
    @FXML
    private TextArea textAreaEP;

    /**
     * pannello dei tab
     */
    @FXML
    private TabPane tabPane;

    /**
     * tab relativo ai risultati
     */
    @FXML
    private Tab tabRisultati;

    /**
     * tab relativo alla connessione al server
     */
    @FXML
    private Tab tabModificaServer;


    /**
     * ----------------
     * variabili locali
     * ----------------
     */

    /**
     * variabile in cui verrà salvato l'ip
     */
    private InetAddress addr;

    /**
     * variabile in cui verrà salvata la porta
     */
    private int port = 8080;

    /**
     * variabile in cui verrà salvato im minsup
     */
    private float minSup = 0f;

    /**
     * variabile in cui verrà salvato il mingr
     */
    private float minGr = 0f;

    /**
     * variabile in cui verrà salvato il valore del textfield relativo alla tabella target
     */
    private String tabTarget;

    /**
     * variabile in cui verrà salvato il valore del textfield relativo alla tabella background
     */
    private String tabBackground;

    /**
     * variabile che indica se è una nuova ricerca o ricerca risultati in archivio
     */
    private int opzione;

    /**
     * variabile che indica se il client è attualmente connesso al server
     */
    private boolean serverConnection = false;
    /**
     * variabile che contiene il socket
     */
    private Socket socket = null;

    /**
     * Imposta l'opzione di nuova scoperta e richiama la funzione Ricerca()
     *
     * @param actionEvent azione svolta cliccando il bottone nuova scoperta
     * @throws IOException Eccezione Input/Output
     */
    public void NuovaScoperta(ActionEvent actionEvent) throws IOException {
        opzione = 1;
        Ricerca();
    }

    /**
     * Avvia la connessione al server utilizzando i dati inseriti nelle TextField
     *
     * @param actionEvent azione svolta cliccando il bottone connetti
     * @throws IOException Eccezione Input/Output
     */
    public void StartServer(ActionEvent actionEvent) throws IOException {
        StartServer();
    }

    /**
     * avvia la connessione al server utilizzando i dati inseriti nelle TextField
     *
     * @throws IOException Eccezione Input/Output
     */
    private void StartServer() throws IOException {
        InetAddress a = null;


        try {
            a = InetAddress.getByName(textFieldIndirizzoIp.getText());
            labelIP.setVisible(false);
        } catch (Exception e) {
            labelIP.setVisible(true);
        }

        int p = -1;
        try {
            p = Integer.parseInt(textFieldPorta.getText());
            labelPorta.setVisible(false);
        } catch (Exception e) {
            labelPorta.setVisible(true);
        }

        if (addr != a || port != p) {

            if (textFieldIndirizzoIp.getText() == "" || textFieldIndirizzoIp.getText() == null) {

                addr = InetAddress.getByName("127.0.0.1");
                textFieldIndirizzoIp.setText("127.0.0.1");
            } else addr = a;

            if (textFieldPorta.getText() == "" || textFieldPorta.getText() == null) {
                labelPorta.setVisible(false);
                port = 8080;
                textFieldPorta.setText("8080");
            } else port = p;


            try {
                if (socket != null) socket.close();
                socket = new Socket(addr, new Integer(port));
                labelErrorConn.setVisible(false);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                serverConnection = true;
            } catch (Exception e) {
                labelErrorConn.setVisible(true);
                serverConnection = false;
                tabPane.getSelectionModel().select(tabModificaServer);
            }

        }

    }

    /**
     * Imposta l'opzione di ricerca in archivio e richiama la funzione Ricerca()
     *
     * @param actionEvent azione svolta cliccando il bottone risultati in archivio
     * @throws IOException Eccezione Input/Output
     */
    public void RisultatiInArchivio(ActionEvent actionEvent) throws IOException {
        opzione = 2;
        Ricerca();
    }

    /**
     * comunica con il server
     */
    private void sendDataToServer() {
        if(serverConnection){
            try {
                out.writeObject(opzione);
                out.writeObject(minSup);
                out.writeObject(minGr);
                out.writeObject(tabTarget);
                out.writeObject(tabBackground);
                textAreaEP.setText("Errore\n");

                String fpMiner = (String) (in.readObject());
                textAreaFP.setText(fpMiner.replace("Frequent patterns\n", ""));
                String epMiner = (String) (in.readObject());
                textAreaEP.setText(epMiner.replace("Emerging patterns\n", ""));
            } catch (IOException | ClassNotFoundException var4) {
                textAreaFP.setText("Errore\n");
                textAreaEP.setText("Errore\n");
            }
        }
    }


    /**
     * controlla la validità del minimo supporto
     *
     * @return boolean vero in caso di minsup corretto falso altrimenti
     */
    private boolean checkMinSup() {
        if(this.textFieldMinSupp.getText()!="") {
            try {
                this.minSup = Float.parseFloat(this.textFieldMinSupp.getText());
            } catch (NumberFormatException e) {
                labelStatusMinSupp.setVisible(true);
                this.minSup=-1;
            }

            if (this.minSup > 0.0F) {
                if (this.minSup <= 1.0F) return true;
                else return false;
            } else return false;
        }else return false;
    }

    /**
     * controlla la validità del minimo GrowRate
     *
     * @return boolean vero in caso di minsup corretto falso altrimenti
     */
    private boolean checkMinGr() {
        if (this.textFieldMinGr.getText() != "") {

            try {
                this.minGr = Float.parseFloat(this.textFieldMinGr.getText());
            } catch (NumberFormatException e) {
                textFieldMinGr.setVisible(true);
                this.minGr=-1;
            }
            return this.minGr > 0.0F;
        }else return false;
    }

    /**
     * Controlla la validità dei campi e qualora sono validi richiama StartServer() e SendDataToServer()
     * @throws IOException Eccezione Input/Output
     */
    private void Ricerca() throws IOException {
        if(!labelErrorConn.isVisible()){

            if (checkMinSup()) labelStatusMinSupp.setVisible(false);
            else labelStatusMinSupp.setVisible(true);

            if (checkMinGr()) labelStatusMinGr.setVisible(false);
            else labelStatusMinGr.setVisible(true);

            if (textFieldTabTarget.getText() != "") {
                labelStatusTabTarget.setVisible(false);
                tabTarget = textFieldTabTarget.getText();
            } else labelStatusTabTarget.setVisible(true);

            if (textFieldTabBackground.getText() != "") {
                labelStatusTabBackground.setVisible(false);
                tabBackground = textFieldTabBackground.getText();
            } else labelStatusTabBackground.setVisible(true);


            if (!serverConnection) {
                addr = InetAddress.getByName("127.0.0.1");
                StartServer();
            }

            textAreaEP.setText("");
            textAreaFP.setText("");

            if (checkMinSup() && checkMinGr() && !labelStatusTabTarget.isVisible() && !labelStatusTabBackground.isVisible()) {
                sendDataToServer();
                if(serverConnection) tabPane.getSelectionModel().select(tabRisultati);
            }
        }else tabPane.getSelectionModel().select(tabModificaServer);
    }
}