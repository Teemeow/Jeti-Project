package jeu;

import javafx.scene.shape.Circle;

public class Archer extends Unite{

    public Archer(String nom, int vie, int attaque, int defense, int numero, String couleur, int positionX, int positionY, Circle circle) {
        super(nom, 30, 5, 3, numero, positionX, positionY);
    }
}
