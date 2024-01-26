package client;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;

public class Acceuil extends Parent {
    private Button playButton;
    private Button classementButton;
    private Button leaveButton;
    private MainGui mainGui;

    public Acceuil(MainGui mainGui){
        this.mainGui = mainGui;
        this.playButton = new Button("Jouer");
        this.classementButton = new Button ("Classement");
        this.leaveButton = new Button("Quitter");

        playButton.setPrefHeight(100);
        playButton.setPrefWidth(300);
        playButton.setLayoutX(150);
        playButton.setLayoutY(50);

        classementButton.setPrefHeight(100);
        classementButton.setPrefWidth(300);
        classementButton.setLayoutX(150);
        classementButton.setLayoutY(200);

        leaveButton.setPrefHeight(100);
        leaveButton.setPrefWidth(300);
        leaveButton.setLayoutX(150);
        leaveButton.setLayoutY(350);

        this.getChildren().add(playButton);
        this.getChildren().add(classementButton);
        this.getChildren().add(leaveButton);

        leaveButton.setOnAction(e -> Platform.exit());
        playButton.setOnAction(e -> lancerPartie());
        classementButton.setOnAction(e -> afficherClassement());
    }

    public void  lancerPartie(){
        this.mainGui.plateau();
    }

    public void afficherClassement(){ this.mainGui.classement();}
}
