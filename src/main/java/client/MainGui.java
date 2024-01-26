package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class MainGui extends Application {
    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        acceuil();
    }


    public void acceuil(){
        Acceuil acceuil = new Acceuil(this);
        Group root = new Group();
        root.getChildren().add(acceuil);
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Jeti");
        stage.setScene(scene);
        stage.show();
    }

    public void plateau(){
        GridPane plateau = new Plateau().initPlateau();
        Scene scene = new Scene(plateau, 600, 600);
        stage.setTitle("LE JEU");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(MainGui.class, args);
    }
}
