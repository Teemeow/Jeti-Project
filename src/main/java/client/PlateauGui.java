package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlateauGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Plateau plateau = new Plateau(new GridPane());
        Group root = new Group();
        root.getChildren().add(plateau);
        Scene scene = new Scene(root, 600, 1200);
        stage.setFullScreen(true);
        stage.setTitle("Plateau");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){Application.launch(PlateauGui.class, args);}
}
