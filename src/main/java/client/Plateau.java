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
        Armee armee2 = new Armee("Rouge");
        Unite unite1 = new Unite("Bob", 50, 20,2,1,1,1, Color.BLUE, 1, 2);
        Unite unite2 = new Unite("Gump", 50, 20,2,2,2,1, Color.BLUE, 1, 2);
        Unite unite3 = new Unite("Marth", 50, 20,2,3,1,2, Color.BLUE, 1, 2);
        Unite unite4 = new Unite("Lucina", 50, 20,2,4,2,2, Color.BLUE, 1, 2);
        Unite unite5 = new Unite("Roy", 50, 20,2,5,8,8, Color.RED, 1, 2);
        Unite unite6 = new Unite("Celica", 50, 20,2,6,9,8, Color.RED, 1, 2);
        Unite unite7 = new Unite("Alm", 50, 20,2,7,8,9, Color.RED, 1, 2);
        Unite unite8 = new Unite("Forest", 50, 20,2,8,9,9, Color.RED, 1, 2);
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
                    printNewMessage(new Message("Infos", "Le message doit faire plus de 6 caractères."));
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

    //Gestion du clic sur une unité
    public void handleUniteClick(Unite unite){
        if (this.uniteChoisie != null){
            this.uniteChoisie.getCircle().setStroke(null);
        }
        unite.getCircle().setStroke(Color.BLUE);
        this.uniteChoisie = unite;
    }

    //Gestion du clic sur une case
    public void handleCaseClick(Case caseJeu){
        if(this.uniteChoisie != null){
            //Verifie si l'unite peut aller jusqu'a la case
            if (this.uniteChoisie.canMove(GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu))){
                this.uniteChoisie.move(GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));
                // Déplace l'unité vers la nouvelle case
                this.plateau.getChildren().remove(this.uniteChoisie.getCircle());
                this.plateau.add(this.uniteChoisie.getCircle(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

                // Met a jour la position de l'unite et du texte de la vie
                this.uniteChoisie.setPositionX(GridPane.getColumnIndex(caseJeu));
                this.uniteChoisie.setPositionY(GridPane.getRowIndex(caseJeu));
                this.uniteChoisie.updateVieTextPosition();

                // Déplace le texte de la vie vers la nouvelle case
                this.plateau.getChildren().remove(this.uniteChoisie.getVieText());
                this.plateau.add(this.uniteChoisie.getVieText(), GridPane.getColumnIndex(caseJeu), GridPane.getRowIndex(caseJeu));

                this.uniteChoisie.getCircle().setStroke(null);

                // Au clic sur le pion, envoie un message au serveur pour indiquer l'action
                Message moveMessage = new Message("move",this.uniteChoisie.getNumero() + "," + this.uniteChoisie.getPositionX() + "," + this.uniteChoisie.getPositionY());
                this.client.sendMessage(moveMessage);
            }else {
                String messageMort = "L'unité peux se déplacer d'uniquement " + uniteChoisie.getPorteeDeplacement() + " cases.";
                printNewMessage(new Message("Infos", messageMort));
            }
            uniteChoisie.appliquerTerrain(caseJeu);
            this.uniteChoisie = null;
        }
    }

    //Configuration d'evenement pour les cases et unites
    public void setMove(Case caseJeu, Armee armee) {
        for (Unite unite : armee.getLesUnites()) {
            caseJeu.setOnMouseClicked(event -> handleCaseClick(caseJeu));
            unite.getCircle().setOnMouseClicked(event -> {
                handleUniteClick(unite);
            });
        }
    }

    //Gestion du clic sur le bouton attaquer
    public void handleAttaqueButtonClick() {
        if (uniteAttaquante != null) {
            // L'unité attaquante a déjà été choisie, permet à l'utilisateur de choisir l'unité cible
            for (Unite unite : this.unitesJeu) {
                unite.getCircle().setStroke(Color.BLACK);
                unite.getCircle().setOnMouseClicked(event -> handleUniteCibleClick(unite));
            }
            String  messageInfos = "Choisissez l'unité qui va se faire attaquer.";
            printNewMessage(new Message("Infos", messageInfos));
        } else {
            // Aucune unité attaquante n'a été choisie permet à l'utilisateur de choisir l'unité attaquante
            for (Unite unite : this.unitesJeu) {
                unite.getCircle().setStroke(Color.GREEN);
                unite.getCircle().setOnMouseClicked(event -> handleUniteAttaquanteClick(unite));
            }
            String  messageInfos = "Choisissez l'unité qui va attaquer, puis réappuyer sur le bouton d'attaque.";
            printNewMessage(new Message("Infos", messageInfos));
        }
    }

    private void handleUniteAttaquanteClick(Unite unite) {
        unite.getCircle().setStroke(null); // Annule la sélection de l'unité précédente (si applicable)
        uniteAttaquante = unite;
        // Désactive la possibilité de choisir cette unité à nouveau
        unite.getCircle().setOnMouseClicked(null);
    }

    //Gestion du clic sur une unite cible
    private void handleUniteCibleClick(Unite uniteCible) {
        // Annule la sélection de l'unité cible précédente (si applicable)
        uniteCible.getCircle().setStroke(null);
        if (uniteAttaquante != null && uniteAttaquante.inRange(uniteCible) && uniteAttaquante != uniteCible && uniteAttaquante.getArmee() != uniteCible.getArmee()) {
            uniteAttaquante.attack(uniteCible);
            if (uniteCible.die()) {
                String messageMort = "L'unité " + uniteCible.getNom() + " est morte.";
                printNewMessage(new Message("Infos", messageMort));
                this.plateau.getChildren().remove(uniteCible.getCircle());
                this.plateau.getChildren().remove(uniteCible.getVieText());
            }
            int positionX = uniteCible.getPositionX();
            int positionY = uniteCible.getPositionY();

            Message attackMessage = new Message("attack", uniteAttaquante.getNumero() + "," + uniteCible.getNumero());
            this.client.sendMessage(attackMessage);

            uniteCible.updateVieTextPosition();
        }else {
            // Gère le cas où l'unité cible est hors de portée
            String  messageErreur = "Cible invalide ou hors de portée.";
            printNewMessage(new Message("Infos", messageErreur));
            System.out.println(messageErreur);
        }
        if (uniteCible.getArmee().encoreUnite()){
            String  messageFin = "Partie terminée l'armée " + uniteAttaquante.getArmee().getNom() + " à gagné.";
            this.client.sendMessage(new Message("Infos", messageFin));
            printNewMessage(new Message("Infos", messageFin));
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
        return null;
    }

    /**
     * @return la case en fonction des coordonnées
     */
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

    //Gere le changement de position d'une unite quand il vient d'un autre client
    public void moveServer(int unitID, int x, int y){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Unite unite = getUniteById(unitID);
                unite.move(x, y);
                Case caseCible = getCaseAt(x, y);
                unite.appliquerTerrain(caseCible);

                // Met à jour les positions X et Y de l'unité en utilisant la position de la case
                plateau.getChildren().remove(unite.getCircle());
                plateau.add(unite.getCircle(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                unite.setPositionX(GridPane.getRowIndex(caseCible));
                unite.setPositionY(GridPane.getColumnIndex(caseCible));

                plateau.getChildren().remove(unite.getVieText());
                plateau.add(unite.getVieText(), GridPane.getRowIndex(caseCible), GridPane.getColumnIndex(caseCible));
                unite.updateVieTextPosition();
            }
        });
    }

    //Gère l'attaque quand elle vient d'un autre client
    public void attackServer(int attackId, int attackedId){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Unite uniteAttaquante = getUniteById(attackId);
                Unite uniteCible = getUniteById(attackedId);
                uniteAttaquante.attack(uniteCible);
                uniteCible.updateVieTextPosition();
                if (uniteCible.die()) {
                    String messageMort = "L'unité " + uniteCible.getNom() + " est morte.";
                    printNewMessage(new Message("Infos", messageMort));
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
                    text.setStyle("-fx-wrap-text: true;");
                    receivedText.getChildren().add(text);
                }
            }
        });
    }
}
