package jeu;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Unite {
    private String nom;
    private int vie;
    private int attaque;
    private int defense;
    private  int defenseBase;
    private int numero;
    private Color couleur;
    private int positionX;
    private int positionY;
    private Text vieText;
    private Circle circle;
    private int porteeAttaque;
    private int porteeDeplacement;
    private int porteeDeplacementBase;
    private Armee armee;

    public Unite(String nom, int vie, int attaque, int defense, int numero, int positionX, int positionY, Color color, int porteeAttaque, int porteeDeplacement) {
        this.nom = nom;
        this.vie = vie;
        this.attaque = attaque;
        this.defense = defense;
        this.defenseBase = defense;
        this.numero = numero;
        this.couleur = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.circle = new Circle(20, this.couleur);
        this.vieText = new Text(String.valueOf(vie));
        this.vieText.setFill(Color.WHITE);
        this.porteeAttaque = porteeAttaque;
        this.porteeDeplacement = porteeDeplacement;
        this.porteeDeplacementBase = porteeDeplacement;
    }

    public Text getVieText() {
        return vieText;
    }

    public void setVieText(Text vieText) {
        this.vieText = vieText;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
        this.vieText.setText(String.valueOf(vie));
    }

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public int getPorteeAttaque() {
        return porteeAttaque;
    }

    public void setPorteeAttaque(int porteeAttauqe) {
        this.porteeAttaque = porteeAttauqe;
    }

    public int getPorteeDeplacement() {
        return porteeDeplacement;
    }

    public Armee getArmee() {
        return armee;
    }

    public void setArmee(Armee armee) {
        this.armee = armee;
    }

    public void setPorteeDeplacement(int porteeDeplacement) {
        this.porteeDeplacement = porteeDeplacement;
    }

    public void attack(Unite ennemie){
        int degat = 0;
        degat = this.attaque - ennemie.getDefense();
        if (ennemie.faiblesse(this)){
            degat = degat * 2;
        }
        int trueDamage = Math.max(degat, 0);
        ennemie.perdreVie(trueDamage);
    }

    /**
     * @return True si l'unite ennemie est un archer
     */
    public boolean faiblesse(Unite ennemie){
        boolean result = false;
        if (ennemie instanceof Archer && this instanceof Volant){
            result = true;
        }
        return result;
    }

    public void perdreVie(int degat){
        this.setVie(this.vie - degat);
    }


    /**
     * @return True si l'unite est morte
     */
    public boolean die(){
        return this.vie <= 0 ? true : false;
    }

    //Applique un bonus ou un malus en fonction du terrain
    public void appliquerTerrain(Case casejeu){
        switch (casejeu.getNom()){
            case "plaine":
                setDefense(defenseBase);
                setPorteeDeplacement( porteeDeplacementBase);
                break;
            case "foret":
                setDefense(defenseBase + 1);
                setPorteeDeplacement( porteeDeplacementBase);
                break;
            case "desert":
                setDefense(defenseBase);
                setPorteeDeplacement( porteeDeplacementBase - 1);
                break;
            case "fort":
                setDefense(defenseBase + 2);
                setPorteeDeplacement( porteeDeplacementBase);
                break;
        }
    }

    //Change les coordonées de l'unite
    public void move(int nouvellePositionX, int nouvellePositionY) {
        this.positionX = nouvellePositionX;
        this.positionY = nouvellePositionY;
    }
    public void updateVieTextPosition() {
        this.vieText.setX(this.positionX * 50);
        this.vieText.setY(this.positionY * 50);
    }
    public boolean inRange(Unite autreUnite) {
        int distanceX = Math.abs(this.getPositionX() - autreUnite.getPositionX());
        int distanceY = Math.abs(this.getPositionY() - autreUnite.getPositionY());

        return distanceX <= this.getPorteeAttaque() && distanceY <= this.getPorteeAttaque();
    }

    /**
     * @return True si case est dans la portée de déplacement de l'unité
     */
    public boolean canMove(int nouvellePositionX, int nouvellePositionY){
        int distanceX = Math.abs(this.getPositionX() - nouvellePositionX);
        int distanceY = Math.abs(this.getPositionY() - nouvellePositionY);
        return distanceX <= this.porteeDeplacement && distanceY <= this.porteeDeplacement;
    }
}
