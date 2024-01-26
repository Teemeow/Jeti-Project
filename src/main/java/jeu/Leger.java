package jeu;

import javafx.scene.shape.Circle;

public class Leger extends Unite{
    public Leger(String nom, int vie, int attaque, int defense, int numero, String couleur, int positionX, int positionY, Circle circle) {
        super(nom, 40, 3, 2, numero, positionX, positionY);
    }
}
