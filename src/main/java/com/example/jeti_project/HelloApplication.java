package com.example.jeti_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        // Définir les contraintes de colonne et de ligne
        for (int i = 0; i < 7; i++) {
            ColumnConstraints column = new ColumnConstraints();
            RowConstraints row = new RowConstraints();
            gridPane.getColumnConstraints().add(column);
            gridPane.getRowConstraints().add(row);
        }

        // Ajouter un Pane vide à chaque case du plateau de jeu
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Pane pane = new Pane();
                Rectangle boardGame = createBoardGame();

                Rectangle unitSquare = createUnitSquare();



                pane.getChildren().add(unitSquare);
                pane.getChildren().add(boardGame); // Ajouter le rectangle au pane

                gridPane.add(pane, i, j);
            }
        }

        Scene scene = new Scene(gridPane, 800, 800);
        primaryStage.setTitle("JETI - Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Rectangle createUnitSquare() {
        Rectangle unitSquare = new Rectangle(25, 25);
        unitSquare.setFill(Color.BLUE);
        return unitSquare;
    }
    private Rectangle createBoardGame(){
        int side = 100;
        Rectangle rectangle = new Rectangle( side,side); // Créer un rectangle de 230x230 pixels
        rectangle.setFill(Color.TRANSPARENT);

        rectangle.setStroke(Color.BLACK); // Mettre la couleur noire au rectangle
        rectangle.setStrokeWidth(1); // Ajuster la largeur de la bordure
        return rectangle;

    }

    public static void main(String[] args) {
        launch(args);
    }
}