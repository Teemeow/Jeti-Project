package jeu;

public class Unite {
    private String nom;
    private int vie;
    private int attaque;
    private int defense;
    private int numero;

    public Unite(String nom, int vie, int attaque, int defense) {
        this.nom = nom;
        this.vie = vie;
        this.attaque = attaque;
        this.defense = defense;
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

    public void attaquer(Unite ennemie){
        int degat = 0;
        degat = this.attaque - ennemie.getDefense();
        if (ennemie.faiblesse(this)){
            degat = degat * 2;
        }
        int trueDamage = (degat < 0) ? 0 : degat;
        ennemie.perdreVie(trueDamage);
    }

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

    public boolean estMort(){
        return this.vie <= 0 ? true : false;
    }

    public void appliquerTerrain(Case position){
        switch (position.getNom()){
            case "foret":
                this.defense++;
                break;
            case "desert":
                //moins de déplacement
                break;
            case "fort":
                defense += 2;
                break;
        }
    }
}
