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
    private double xOffset = 0;
    private double yOffset = 0;
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
                Rectangle rectangle = new Rectangle(230, 230); // Créer un rectangle de 230x230 pixels
                rectangle.setFill(Color.TRANSPARENT);

                Rectangle unitSquare = createUnitSquare();

                // Ajouter un gestionnaire d'événements pour le déplacement
                unitSquare.setOnMousePressed(event -> {
                    xOffset = event.getSceneX() - unitSquare.getTranslateX();
                    yOffset = event.getSceneY() - unitSquare.getTranslateY();
                });

                unitSquare.setOnMouseDragged(event -> {
                    unitSquare.setTranslateX(event.getSceneX() - xOffset);
                    unitSquare.setTranslateY(event.getSceneY() - yOffset);
                });

                // Ajouter une bordure noire
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1); // Ajuster la largeur de la bordure
                pane.getChildren().add(rectangle); // Ajouter le rectangle au pane

                gridPane.add(pane, i, j);
            }
        }

        Scene scene = new Scene(gridPane, 800, 800);
        primaryStage.setTitle("JETI - Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Rectangle createUnitSquare() {
        Rectangle unitSquare = new Rectangle(50, 50);
        unitSquare.setFill(Color.BLUE);
        return unitSquare;
    }
    public static void main(String[] args) {
        launch(args);
    }
}