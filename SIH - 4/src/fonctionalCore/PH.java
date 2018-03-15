package fonctionalCore;



public class PH extends Personne {

  public String specialite;

  public String id_connec;

  public String mdp;

    public PH(String specialite, String id_connec, String mdp, String nom, String prenom, String nTel, Sexe sexe) {
        super(nom, prenom, nTel, sexe);
        this.specialite = specialite;
        this.id_connec = id_connec;
        this.mdp = mdp;
    }

    public PH() {
        super();
    }

  
}