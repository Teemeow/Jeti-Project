package client;

import common.Message;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    private Unite uniteAttaquante;
    private Armee armee;
    private Client client;

    public Plateau(GridPane gridPane){
        this.plateau = gridPane;
    }

    public GridPane initPlateau(Client client){
        Armee armee = new Armee("T");
        Unite unite1 = new Unite("Bob", 600, 20,2,1,8,4);
        Unite unite2 = new Unite("Bob", 4, 20,2,9,2,5);
        Armee armee2 = new Armee("B");
        Unite unite3 = new Unite("Bob", 27, 20,2,18,9,3);
        this.armee = armee;
        armee.ajouterUnite(unite1);
        armee.ajouterUnite(unite2);
        armee.ajouterUnite(unite3);
        for (int ligne = 0; ligne < nbLigne; ligne++){
            for (int colonne = 0; colonne < nbColonnes; colonne++){
                Case caseJeu = creerCase(ligne , colonne);
                this.plateau.add(caseJeu, colonne, ligne);
                setMove(caseJeu, armee, client);
            }
        }
        placerUnite(unite1);
        placerUnite(unite2);
        placerUnite(unite3);
        Button attaqueButton = new Button("Attaquer");
        attaqueButton.setOnAction(event -> handleAttaqueButtonClick());
        this.plateau.add(attaqueButton, nbColonnes, nbLigne);
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

    public void handleCaseClick(Case caseJeu, Unite unite, Client client){
        if(this.uniteChoose != null){
            this.uniteChoose.deplacer(GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));
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

            // Au clic sur le pion, envoie un message au serveur pour indiquer l'action
            Message moveMessage = new Message("move",this.uniteChoose.getNumero() + "," + this.uniteChoose.getPositionX() + "," + this.uniteChoose.getPositionY());
            client.sendMessage(moveMessage);
            this.uniteChoose = null;
        }
    }

    public void setMove(Case caseJeu, Armee armee, Client client) {
        for (Unite unite : armee.getLesUnites()) {
            caseJeu.setOnMouseClicked(event -> handleCaseClick(caseJeu, unite, client));

            unite.getCircle().setOnMouseClicked(even -> {
                handleUniteClick(unite);
            });

        }
    }

    public void handleAttaqueButtonClick() {
        if (uniteAttaquante != null) {
            // L'unité attaquante a déjà été choisie, permet à l'utilisateur de choisir l'unité cible
            for (Unite unite : armee.getLesUnites()) {
                unite.getCircle().setStroke(Color.BLACK);
                unite.getCircle().setOnMouseClicked(event -> handleUniteCibleClick(unite));
            }
        } else {
            // Aucune unité attaquante n'a été choisie, permet à l'utilisateur de choisir l'unité attaquante
            for (Unite unite : armee.getLesUnites()) {
                unite.getCircle().setStroke(Color.GREEN);
                unite.getCircle().setOnMouseClicked(event -> handleUniteAttaquanteClick(unite));
            }
        }
    }

    private void handleUniteAttaquanteClick(Unite unite) {
        unite.getCircle().setStroke(null); // Annule la sélection de l'unité précédente (si applicable)
        uniteAttaquante = unite;
        // Désactive la possibilité de choisir cette unité à nouveau
        unite.getCircle().setOnMouseClicked(null);
    }

    private void handleUniteCibleClick(Unite uniteCible) {
        uniteCible.getCircle().setStroke(null); // Annule la sélection de l'unité cible précédente (si applicable)

        // Effectuez l'attaque avec l'unité attaquante et l'unité cible
        uniteAttaquante.attaquer(uniteCible);
        int positionX = uniteCible.getPositionX();
        int positionY = uniteCible.getPositionY();

        // Assurez-vous que la positionX et positionY sont valides
        if (positionX >= 0 && positionX < nbColonnes && positionY >= 0 && positionY < nbLigne) {
            uniteCible.deplacer(positionX, positionY);
        }

        uniteCible.updateVieTextPosition();
        // Réinitialise les sélections et les gestionnaires de clic
        uniteAttaquante = null;
        for (Unite unite : armee.getLesUnites()) {
            unite.getCircle().setStroke(null);
            unite.getCircle().setOnMouseClicked(event -> handleUniteClick(unite));
        }
    }



    public Unite getUniteById(int unitID, Armee armee) {
        for (Unite unite : armee.getLesUnites()) {
            if (unite.getNumero() == unitID) {
                return unite;
            }
        }
        return null; // Unité non trouvée
    }
    public Case getCaseAt(int ligne, int colonne) {
        Node node = null;
        for (Node child : plateau.getChildren()) {
            if (GridPane.getRowIndex(child) == ligne && GridPane.getColumnIndex(child) == colonne) {
                node = child;
                break;
            }
        }
        if (node instanceof Case) {
            return (Case) node;
        } else {
            return null;
        }
    }
    public void  test(int unitID, int x, int y){
        Armee a = this.armee;
        Platform.runLater((new Runnable() {
            @Override
            public void run() {
                Unite bouf = getUniteById(unitID, a);
                System.out.println(bouf.getNumero());
                bouf.deplacer(x, y);

                // Obtient la case à la position (1, 1)
                Case caseCible = getCaseAt(x, y);


                // Met à jour les positions X et Y de l'unité en utilisant la position de la case
                plateau.getChildren().remove(bouf.getCircle());
                plateau.add(bouf.getCircle(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                bouf.setPositionX(GridPane.getRowIndex(caseCible));
                bouf.setPositionY(GridPane.getColumnIndex(caseCible));

                plateau.getChildren().remove(bouf.getVieText());
                plateau.add(bouf.getVieText(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                bouf.updateVieTextPosition();
            }
        }));
    }
}
