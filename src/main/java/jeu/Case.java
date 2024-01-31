package jeu;

import javafx.scene.shape.Rectangle;

public class Case extends Rectangle {
    private String nom;
    private int tailleCase;


    public Case(String nom, double largeur, double hauteur) {
        super(largeur, hauteur);
        this.nom = nom;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTailleCase() {
        return tailleCase;
    }

    public void setTailleCase(int tailleCase) {
        this.tailleCase = tailleCase;
    }


}
