package fonctionalCore;

import java.util.Vector;


public class Personne {

  private String nom;

  private String prenom;

  private String nTel;

  private Sexe sexe;

    public Personne(String nom, String prenom, String nTel, Sexe sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.nTel = nTel;
        this.sexe = sexe;
    }
public Personne() {
       
    }
    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the nTel
     */
    public String getnTel() {
        return nTel;
    }

    /**
     * @param nTel the nTel to set
     */
    public void setnTel(String nTel) {
        this.nTel = nTel;
    }

    /**
     * @return the sexe
     */
    public Sexe getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

}