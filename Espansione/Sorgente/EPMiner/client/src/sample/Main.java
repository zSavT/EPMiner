package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * classe principale per l'avvio del client
 */
public class Main extends Application {

    /**
     * primaryStage definisce l'interfaccia
     */
    public static Stage primaryStage;


    /**
     * imposta i parametri dell'interfaccia
     *
     * @param stage interfaccia
     * @throws Exception Eccezione generica
     */
    @Override
    public void start(Stage stage) throws Exception{
        this.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("guiEpMiner.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("EPMiner");
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(620);

        Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
        stage.getIcons().add(icon);

        scene.getStylesheets().add(getClass().getResource("styling.css").toString());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * Il codice iniziale dell'applicazione
     *
     * @param args gli argomenti passati come input
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
}
