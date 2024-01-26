package client;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import jeu.Case;

public class Plateau extends Parent {
    private Button test;
    private int tailleCase = 50;
    private int nbColonnes = 10;
    private int nbLigne = 10;

    public Plateau(){
        this.test = new Button("oui");
        test.setPrefHeight(100);
        test.setPrefWidth(300);
        this.getChildren().add(test);
    }

    public GridPane initPlateau(){
        GridPane plateau = new GridPane();
        for (int ligne = 0; ligne < nbLigne; ligne++){
            for (int colonne = 0; colonne < nbColonnes; colonne++){
                Case caseJeu = creerCase(ligne , colonne);
                plateau.add(caseJeu, colonne, ligne);
            }
        }
        return plateau;
    }

    public Case creerCase(int ligne, int colonne){
        Case caseJeu = new Case("plaine", tailleCase, tailleCase);
        caseJeu.setFill(chooseColor(ligne, colonne));
        return caseJeu;
    }

    public Color chooseColor(int ligne, int colonne){
        return (ligne + colonne) % 2 == 0 ? Color.WHITE : Color.BLACK;
    }
}
