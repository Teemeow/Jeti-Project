package jeu;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Volant extends Unite{

    public Volant(String nom, int vie, int attaque, int defense, int numero, int positionX, int positionY, Color color, int porteeAttauqe, int porteeDeplacement) {
        super(nom, vie, attaque, defense, numero, positionX, positionY, color, porteeAttauqe, porteeDeplacement);
    }
}
