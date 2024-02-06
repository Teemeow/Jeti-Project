package client;

import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

public class Acceuil extends Parent {
    private Button playButton;
    private Button leaveButton;
    private MainGui mainGui;
    private Client client;
    private TextFlow receivedText;

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
    public void printNewMessage(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label("\n" + mess);
                if (receivedText != null) {
                    text.setPrefWidth(receivedText.getPrefWidth() - 20);
                    text.setAlignment(Pos.CENTER_LEFT);
                    receivedText.getChildren().add(text);
                }
            }
        });
    }
}



