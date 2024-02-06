package client;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Acceuil extends Parent {
    private Button playButton;
    private Button leaveButton;
    private MainGui mainGui;
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Acceuil(MainGui mainGui){
        this.mainGui = mainGui;
        this.playButton = new Button("Jouer");
        this.leaveButton = new Button("Quitter");

        playButton.setPrefHeight(100);
        playButton.setPrefWidth(300);
        playButton.setLayoutX(150);
        playButton.setLayoutY(150);


        leaveButton.setPrefHeight(100);
        leaveButton.setPrefWidth(300);
        leaveButton.setLayoutX(150);
        leaveButton.setLayoutY(300);


        this.getChildren().add(playButton);
        this.getChildren().add(leaveButton);

        leaveButton.setOnAction(e -> Platform.exit());
        playButton.setOnAction(e -> lancerPartie());
    }

    public void  lancerPartie(){
        this.mainGui.plateau();
    }
}



