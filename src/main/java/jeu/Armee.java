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
        //unite.setNumero(this.lesUnites.size());
        this.lesUnites.add(unite);
        unite.setArmee(this);
    }

    public void enleverUnite(int numeroUnite){
        this.lesUnites.remove(numeroUnite);
    }


    /**
     * @return True si aucune unite est en vie
     */
    public boolean encoreUnite(){
        int result = 0;
        for (Unite unite : this.lesUnites){
            if(unite.getVie() <= 0){
                result++;
            }
        }
        return result == this.lesUnites.size() ? true : false;
    }
}
