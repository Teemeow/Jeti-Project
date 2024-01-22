package jeu;

import java.util.HashSet;
import java.util.Set;

public class Armee {
    private String nom;
    private Set<Unite> lesUnites;

    public Armee(String nom) {
        this.nom = nom;
        this.lesUnites = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Unite> getLesUnites() {
        return lesUnites;
    }

    public void setLesUnites(Set<Unite> lesUnites) {
        this.lesUnites = lesUnites;
    }

    public void ajouterUnite(Unite unite){
        unite.setNumero(this.lesUnites.size());
        this.lesUnites.add(unite);
    }

    public void enleverUnite(int numeroUnite){
        this.lesUnites.remove(numeroUnite);
    }

    public boolean encoreUnite(){
        return this.lesUnites.size() == 0 ? false : true;
    }
}
