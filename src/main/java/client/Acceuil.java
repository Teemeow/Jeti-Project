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


/*public class Acceuil extends Parent {
    private ScrollPane scrollReceivedText;
    private TextArea textToSend;
    private Button clearBtn;
    private Button sendBtn;
    private TextFlow receivedText;
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Acceuil() {
        //this.client = new Client("127.0.0.1", 1025);

        this.textToSend = new TextArea();
        textToSend.setLayoutX(75);
        textToSend.setLayoutY(335);
        textToSend.setPrefHeight(100);
        textToSend.setPrefWidth(300);
        this.textToSend.setPromptText("Message...");


        this.receivedText = new TextFlow();
        this.receivedText.setPrefWidth(325);
        // receivedText.setVisible(true);


        this.scrollReceivedText = new ScrollPane();
        this.scrollReceivedText.setLayoutX(75);
        this.scrollReceivedText.setLayoutY(50);
        this.scrollReceivedText.setPrefWidth(380);
        this.scrollReceivedText.setPrefHeight(250);
        this.scrollReceivedText.setContent(receivedText);
        this.scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());



        this.sendBtn = new Button("Send");
        this.sendBtn.setVisible(true);
        this.sendBtn.setLayoutX(400);
        this.sendBtn.setLayoutY(350);

        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Message message = new Message("Moi", textToSend.getText()) ;
                printNewMessage(message);
                client.sendMessage(message);
                textToSend.clear();
            }
        });

        this.clearBtn = new Button("Clear");
        clearBtn.setVisible(true);
        clearBtn.setLayoutX(400);
        clearBtn.setLayoutY(400);

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textToSend.clear();
            }
        });


        this.getChildren().add(scrollReceivedText);
        this.getChildren().add(textToSend);
        this.getChildren().add(clearBtn);
        this.getChildren().add(sendBtn);
    }


}*/
