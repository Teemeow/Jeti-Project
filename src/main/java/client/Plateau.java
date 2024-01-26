package client;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import jeu.Armee;
import jeu.Case;
import jeu.Unite;

public class Plateau extends Parent {
    private int tailleCase = 50;
    private int nbColonnes = 10;
    private int nbLigne = 10;
    private GridPane plateau;
    private Unite uniteChoose;

    public Plateau(GridPane gridPane){
        this.plateau = gridPane;
    }

    public GridPane initPlateau(){
        Armee armee = new Armee("T");
        Unite unite1 = new Unite("Bob", 600, 2,2,1,8,4);
        Unite unite2 = new Unite("Bob", 4, 2,2,1,2,5);
        Unite unite3 = new Unite("Bob", 27, 2,2,1,9,3);
        armee.ajouterUnite(unite1);
        armee.ajouterUnite(unite2);
        armee.ajouterUnite(unite3);
        for (int ligne = 0; ligne < nbLigne; ligne++){
            for (int colonne = 0; colonne < nbColonnes; colonne++){
                Case caseJeu = creerCase(ligne , colonne);
                this.plateau.add(caseJeu, colonne, ligne);
                setMove(caseJeu, armee);
            }
        }
        placerUnite(unite1);
        placerUnite(unite2);
        placerUnite(unite3);
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

    public void placerUnite(Unite unite){
        this.plateau.add(unite.getCircle(), unite.getPositionX(), unite.getPositionY());
        this.plateau.add(unite.getVieText(), unite.getPositionX(), unite.getPositionY());
        unite.updateVieTextPosition();
    }

    public void handleUniteClick(Unite unite){
        if (this.uniteChoose != null){
            this.uniteChoose.getCircle().setStroke(null);
        }

        unite.getCircle().setStroke(Color.BLUE);
        this.uniteChoose = unite;
    }

    public void handleCaseClick(Case caseJeu, Unite unite){
        if(this.uniteChoose != null){
            // Déplace l'unité vers la nouvelle case
            this.plateau.getChildren().remove(this.uniteChoose.getCircle());
            this.plateau.add(this.uniteChoose.getCircle(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

            // Met à jour la position de l'unité
            this.uniteChoose.setPositionX(GridPane.getColumnIndex(caseJeu));
            this.uniteChoose.setPositionY(GridPane.getRowIndex(caseJeu));

            // Met à jour la position du texte de la vie
            this.uniteChoose.updateVieTextPosition();

            // Déplace le texte de la vie vers la nouvelle case
            this.plateau.getChildren().remove(this.uniteChoose.getVieText());
            this.plateau.add(this.uniteChoose.getVieText(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

            this.uniteChoose.getCircle().setStroke(null);
            this.uniteChoose = null;
        }
    }

    public void setMove(Case caseJeu, Armee armee){
        for (Unite unite: armee.getLesUnites()){
            caseJeu.setOnMouseClicked(event -> handleCaseClick(caseJeu, unite));
            unite.getCircle().setOnMouseClicked(even -> handleUniteClick(unite));
        }
    }
}
