package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class MainGui extends Application {

    private int tailleCase = 50;
    private int nbColonnes = 10;
    private int nbLigne = 10;
    private Stage stage;
    private Client client;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        acceuil();
    }


    public void acceuil(){
        String address = "127.0.0.1";
        int port = 1025;

        Acceuil acceuil = new Acceuil(this);
        this.client = new Client(address, port, acceuil);
        acceuil.setClient(client);
        Group root = new Group();
        root.getChildren().add(acceuil);
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Jeti");
        stage.setScene(scene);
        stage.show();
    }

    public void plateau(){
        Plateau p = new Plateau(new GridPane());
        this.client.setPlateau(p);
        GridPane plateau = p.initPlateau(this.client);
        Scene scene = new Scene(plateau, 600, 600);
        stage.setTitle("LE JEU");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(MainGui.class, args);
    }
}
