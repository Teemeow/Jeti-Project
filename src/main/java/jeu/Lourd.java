package jeu;

import javafx.scene.shape.Circle;

public class Lourd extends Unite{
    public Lourd(String nom, int vie, int attaque, int defense, int numero, String couleur, int positionX, int positionY, Circle circle) {
        super(nom, 50, 2, 10, numero, positionX, positionY);
    }
}
