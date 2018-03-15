package fonctionalCore;

public class Secretaire extends Personne {

    public String id_connec;

    public String mdp;

    public Secretaire(String id_connec, String mdp, String nom, String prenom, String nTel, Sexe sexe) {
        super(nom, prenom, nTel, sexe);
        this.id_connec = id_connec;
        this.mdp = mdp;
    }

    public Secretaire() {
        super();
    }

}
