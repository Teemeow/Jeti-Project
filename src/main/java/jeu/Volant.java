package jeu;

import javafx.scene.shape.Circle;

public class Volant extends Unite{
    public Volant(String nom, int vie, int attaque, int defense, int numero, String couleur, int positionX, int positionY, Circle circle) {
        super(nom, 30, 2, 2, numero, positionX, positionY);
    }
}
