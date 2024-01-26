package client;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jeu.Case;
import jeu.Unite;

public class Plateau extends Parent {
    private int tailleCase = 50;
    private int nbColonnes = 10;
    private int nbLigne = 10;
    private GridPane plateau;

    public Plateau(GridPane gridPane){
        this.plateau = gridPane;
    }

    public GridPane initPlateau(){

        Unite unite = new Unite("Bob", 1, 2,2,1,1,1);
        for (int ligne = 0; ligne < nbLigne; ligne++){
            for (int colonne = 0; colonne < nbColonnes; colonne++){
                Case caseJeu = creerCase(ligne , colonne);
                Circle cercle = new Circle(5, Color.RED);
                this.plateau.add(caseJeu, colonne, ligne);
                this.plateau.add(cercle, 2, 4);
            }
        }
        placerPion(unite);
        return this.plateau;
    }

    public Case creerCase(int ligne, int colonne){
        Case caseJeu = new Case("plaine", tailleCase, tailleCase);
        caseJeu.setFill(chooseColor(ligne, colonne));
        return caseJeu;
    }

    public Color chooseColor(int ligne, int colonne){
        return (ligne + colonne) % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    public void placerPion(Unite unite){
        this.plateau.add(unite.getCircle(), unite.getPositionX(), unite.getPositionY());
    }
}
