package jeu;

import javafx.scene.shape.Rectangle;

public class Case extends Rectangle {
    private String nom;
    private int tailleCase;
    private int tailleCase2;

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

    public int getTailleCase2() {
        return tailleCase2;
    }

    public void setTailleCase2(int tailleCase2) {
        this.tailleCase2 = tailleCase2;
    }
}
