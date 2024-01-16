package client;

import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Plateau extends Parent {
    private Button test;
    public Plateau(){
        this.test = new Button("oui");
        test.setPrefHeight(100);
        test.setPrefWidth(300);
        this.getChildren().add(test);
    }
}
