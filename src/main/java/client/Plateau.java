package client;

import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import jeu.Armee;
import jeu.Case;
import jeu.Unite;

import java.util.ArrayList;
import java.util.Random;

public class Plateau extends Parent {
    private int tailleCase = 50;
    private int nbColonnes = 10;
    private int nbLigne = 10;
    private GridPane plateau;
    private Unite uniteChoisie;
    private Unite uniteAttaquante;
    private ArrayList<Unite> unitesJeu;
    private Client client;
    private ScrollPane scrollReceivedText;
    private TextFlow receivedText;
    private TextArea textToSend;
    private Button sendBtn;
    private Button clearBtn;
    public Plateau(GridPane gridPane){
        this.plateau = gridPane;
    }

    public GridPane initPlateau(Client client){
        this.client = client;

        //Création des unitées et des armmées
        this.unitesJeu = new ArrayList<>();
        Armee armee = new Armee("Bleu");
        Unite unite1 = new Unite("Bob", 50, 20,2,1,1,1, Color.BLUE, 1, 2);
        Unite unite2 = new Unite("Bob", 50, 20,2,2,2,1, Color.BLUE, 1, 2);
        Unite unite3 = new Unite("Bob", 50, 20,2,3,1,2, Color.BLUE, 1, 2);
        Unite unite4 = new Unite("Bob", 50, 20,2,4,2,2, Color.BLUE, 1, 2);
        Armee armee2 = new Armee("Rouge");
        Unite unite5 = new Unite("Bob", 50, 20,2,5,8,8, Color.RED, 1, 2);
        Unite unite6 = new Unite("Bob", 50, 20,2,6,9,8, Color.RED, 1, 2);
        Unite unite7 = new Unite("Bob", 50, 20,2,7,8,9, Color.RED, 1, 2);
        Unite unite8 = new Unite("Bob", 50, 20,2,8,9,9, Color.RED, 1, 2);
        armee.ajouterUnite(unite1);
        armee.ajouterUnite(unite2);
        armee.ajouterUnite(unite3);
        armee.ajouterUnite(unite4);
        armee2.ajouterUnite(unite5);
        armee2.ajouterUnite(unite6);
        armee2.ajouterUnite(unite7);
        armee2.ajouterUnite(unite8);

        //Création du plateau
        for (int ligne = 0; ligne   < nbLigne ; ligne++){
            for (int colonne = 0; colonne < nbColonnes; colonne++){
                if (ligne != 0 && colonne != 0){
                    Case caseJeu = creerCase(ligne  , colonne);
                    this.plateau.add(caseJeu, colonne, ligne);
                    setMove(caseJeu, armee);
                    setMove(caseJeu, armee2);
                }
            }
        }
        for (Unite unite : armee.getLesUnites()){
            this.unitesJeu.add(unite);
            placerUnite(unite);
            unite.appliquerTerrain(getCaseAt(unite.getPositionY(), unite.getPositionX()));
        }
        for (Unite unite : armee2.getLesUnites()){
            this.unitesJeu.add(unite);
            placerUnite(unite);
            unite.appliquerTerrain(getCaseAt(unite.getPositionY(), unite.getPositionX()));
        }
        Button attaqueButton = new Button("Attaquer");
        attaqueButton.setOnAction(event -> handleAttaqueButtonClick());
        this.plateau.add(attaqueButton, 15, 4);



        this.receivedText = new TextFlow();
        this.receivedText.setPrefWidth(325);
        this.plateau.add(receivedText, 23,0);

        this.scrollReceivedText = new ScrollPane();
        this.scrollReceivedText.setLayoutX(50);
        this.scrollReceivedText.setLayoutY(50);
        this.scrollReceivedText.setPrefWidth(350);
        this.scrollReceivedText.setPrefHeight(300);
        this.scrollReceivedText.setContent(receivedText);
        this.scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
        this.plateau.add(scrollReceivedText, 25, 0 );


        this.textToSend = new TextArea();
        this.textToSend.setPrefWidth(375);
        this.textToSend.setPrefHeight(25);
        this.textToSend.setPromptText("Message...");

        this.plateau.add(textToSend, 15 , 0);

        this.sendBtn = new Button();
        this.sendBtn.setVisible(true);
        this.sendBtn.setPrefWidth(100);
        this.sendBtn.setPrefHeight(25);
        this.sendBtn.setText("Envoyer");
        this.sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!textToSend.getText().trim().isEmpty() && textToSend.getText().length() > 6) {
                    Message message = new Message("User", textToSend.getText());

                    printNewMessage(message);
                    client.sendMessage(message);

                    textToSend.clear();
                }
                else {
                    printNewMessage(new Message("", "Le message doit faire plus de 6 caractères"));
                }
            }
        });
        this.plateau.add(sendBtn, 15, 1);

        this.clearBtn = new Button();
        this.clearBtn.setVisible(true);
        this.clearBtn.setPrefWidth(100);
        this.clearBtn.setPrefHeight(25);
        this.clearBtn.setText("Effacer");
        this.clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textToSend.clear();
            }
        });
        this.plateau.add(clearBtn, 15, 2);

        return this.plateau;
    }

    public Case creerCase(int ligne, int colonne){
        Case caseJeu = null;
        Random random = new Random();
        if (ligne == 3 && colonne == 2 || ligne == 7 && colonne == 8){
            caseJeu = createFort();
        }else if (ligne >= 4 &&  ligne <=5 && colonne >= 4 && colonne <= 7){
            caseJeu = createDesert();
        }else if (ligne >= 3 &&  ligne <=6 && colonne >= 3 && colonne <= 8){

            int nombreAleatoire = random.nextInt(2) + 1;

            if (nombreAleatoire == 1){
                caseJeu = createDesert();
            }
            else {
                caseJeu = createPlaine();
            }
        }
        else {
            int nombreAleatoire = random.nextInt(7) + 1;
            if (nombreAleatoire == 1){
                caseJeu = createForet();
            }
            else {
                caseJeu = createPlaine();
            }
        }

        return caseJeu ;
    }


    public Case createDesert(){
        Case caseJeu = new Case("desert", tailleCase, tailleCase);
        caseJeu.setFill(Color.YELLOW);
        return caseJeu;
    }
    public Case createPlaine(){
        Case caseJeu = new Case("plaine", tailleCase, tailleCase);
        caseJeu.setFill(Color.MEDIUMSEAGREEN);
        return caseJeu;
    }

    public Case createForet(){
        Case caseJeu = new Case("foret", tailleCase, tailleCase);
        caseJeu.setFill(Color.DARKGREEN);
        return caseJeu;
    }
    public Case createFort(){
        Case caseJeu = new Case("fort", tailleCase, tailleCase);
        caseJeu.setFill(Color.GREY);
        return caseJeu;
    }

    public void placerUnite(Unite unite){
        this.plateau.add(unite.getCircle(), unite.getPositionX(), unite.getPositionY());
        this.plateau.add(unite.getVieText(), unite.getPositionX(), unite.getPositionY());
        unite.updateVieTextPosition();
    }

    public void handleUniteClick(Unite unite){
        if (this.uniteChoisie != null){
            this.uniteChoisie.getCircle().setStroke(null);
        }

        unite.getCircle().setStroke(Color.BLUE);
        this.uniteChoisie = unite;
    }

    public void handleCaseClick(Case caseJeu){
        if(this.uniteChoisie != null){
            if (this.uniteChoisie.peuxdeplacer(GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu))){
                this.uniteChoisie.deplacer(GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));
                // Déplace l'unité vers la nouvelle case
                this.plateau.getChildren().remove(this.uniteChoisie.getCircle());
                this.plateau.add(this.uniteChoisie.getCircle(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

                // Met à jour la position de l'unité
                this.uniteChoisie.setPositionX(GridPane.getColumnIndex(caseJeu));
                this.uniteChoisie.setPositionY(GridPane.getRowIndex(caseJeu));

                // Met à jour la position du texte de la vie
                this.uniteChoisie.updateVieTextPosition();

                // Déplace le texte de la vie vers la nouvelle case
                this.plateau.getChildren().remove(this.uniteChoisie.getVieText());
                this.plateau.add(this.uniteChoisie.getVieText(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

                this.uniteChoisie.getCircle().setStroke(null);

                // Au clic sur le pion, envoie un message au serveur pour indiquer l'action
                Message moveMessage = new Message("move",this.uniteChoisie.getNumero() + "," + this.uniteChoisie.getPositionX() + "," + this.uniteChoisie.getPositionY());
                this.client.sendMessage(moveMessage);
            }else {
                String messageMort = "L'unité peux se déplacer d'uniquement " + uniteChoisie.getPorteeDeplacement() + " cases";
                printNewMessage(new Message("Info", messageMort));
            }
            uniteChoisie.appliquerTerrain(caseJeu);
            this.uniteChoisie = null;
        }
    }

    public void setMove(Case caseJeu, Armee armee) {
        for (Unite unite : armee.getLesUnites()) {
            caseJeu.setOnMouseClicked(event -> handleCaseClick(caseJeu));
            unite.getCircle().setOnMouseClicked(event -> {
                handleUniteClick(unite);
            });
        }
    }

    public void handleAttaqueButtonClick() {
        if (uniteAttaquante != null) {
            // L'unité attaquante a déjà été choisie, permet à l'utilisateur de choisir l'unité cible
            for (Unite unite : this.unitesJeu) {
                unite.getCircle().setStroke(Color.BLACK);
                unite.getCircle().setOnMouseClicked(event -> handleUniteCibleClick(unite));
            }
        } else {
            // Aucune unité attaquante n'a été choisie, permet à l'utilisateur de choisir l'unité attaquante
            for (Unite unite : this.unitesJeu) {
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
        // Annule la sélection de l'unité cible précédente (si applicable)
        uniteCible.getCircle().setStroke(null);
        if (uniteAttaquante != null && uniteAttaquante.estDansPortee(uniteCible) && uniteAttaquante != uniteCible && uniteAttaquante.getArmee() != uniteCible.getArmee()) {
            // Effectuez l'attaque avec l'unité attaquante et l'unité cible
            uniteAttaquante.attaquer(uniteCible);
            //Vérifie si l'unite est morte
            if (uniteCible.estMort()) {
                String messageMort = "L'unité " + uniteCible.getNom() + " est morte.";
                printNewMessage(new Message("Info", messageMort));
                this.plateau.getChildren().remove(uniteCible.getCircle());
                this.plateau.getChildren().remove(uniteCible.getVieText());
            }
            int positionX = uniteCible.getPositionX();
            int positionY = uniteCible.getPositionY();

            Message attackMessage = new Message("attack", uniteAttaquante.getNumero() + "," + uniteCible.getNumero());
            this.client.sendMessage(attackMessage);

            // Assurez-vous que la positionX et positionY sont valides
            if (positionX >= 0 && positionX < nbColonnes && positionY >= 0 && positionY < nbLigne) {
                uniteCible.deplacer(positionX, positionY);
            }

            uniteCible.updateVieTextPosition();
        }else {
            // Gère le cas où l'unité cible est hors de portée
            String  messageErreur = "Cible invalide ou hors de portée.";
            printNewMessage(new Message("Info", messageErreur));
            System.out.println(messageErreur);
        }
        if (uniteCible.getArmee().encoreUnite()){
            String  messageFin = "Parti terminée l'armée " + uniteAttaquante.getArmee().getNom() + " à remportée la parti.";
            printNewMessage(new Message("Info", messageFin));
        }
        // Réinitialise les sélections et les gestionnaires de clic
        uniteAttaquante = null;
        for (Unite unite : this.unitesJeu) {
            unite.getCircle().setStroke(null);
            unite.getCircle().setOnMouseClicked(event -> handleUniteClick(unite));
        }
    }


    public Unite getUniteById(int unitID) {
        for (Unite unite : this.unitesJeu) {
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
    public void moveServer(int unitID, int x, int y){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Unite bouf = getUniteById(unitID);
                bouf.deplacer(x, y);

                // Obtient la case à la position (1, 1)
                Case caseCible = getCaseAt(x, y);
                bouf.appliquerTerrain(caseCible);
                // Met à jour les positions X et Y de l'unité en utilisant la position de la case
                plateau.getChildren().remove(bouf.getCircle());
                plateau.add(bouf.getCircle(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                bouf.setPositionX(GridPane.getRowIndex(caseCible));
                bouf.setPositionY(GridPane.getColumnIndex(caseCible));

                plateau.getChildren().remove(bouf.getVieText());
                plateau.add(bouf.getVieText(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                bouf.updateVieTextPosition();
            }
        });
    }
    public void attackServer(int attackId, int attackedId){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Unite uniteAttaquante = getUniteById(attackId);
                Unite uniteCible = getUniteById(attackedId);
                uniteAttaquante.attaquer(uniteCible);
                uniteCible.updateVieTextPosition();
                if (uniteCible.estMort()) {
                    String messageMort = "L'unité " + uniteCible.getNom() + " est morte";
                    printNewMessage(new Message("Info", messageMort));
                    plateau.getChildren().remove(uniteCible.getCircle());
                    plateau.getChildren().remove(uniteCible.getVieText());
                }
            }
        });
    }
    public void printNewMessage(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label("\n" + mess);
                if (receivedText != null) {
                    text.setPrefWidth(receivedText.getPrefWidth() - 20);
                    text.setAlignment(Pos.CENTER_LEFT);
                    receivedText.getChildren().add(text);
                }
            }
        });
    }
}
