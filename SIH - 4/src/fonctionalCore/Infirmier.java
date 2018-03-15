package fonctionalCore;



public class Infirmier extends Personne {

  public String id_connec;

  public String mdp;

  public String service;

    public Infirmier(String id_connec, String mdp, String service,String nom, String prenom, String nTel, Sexe sexe) {
        super(nom, prenom,  nTel,  sexe);
          
        this.id_connec = id_connec;
        this.mdp = mdp;
        this.service = service;
    }

}