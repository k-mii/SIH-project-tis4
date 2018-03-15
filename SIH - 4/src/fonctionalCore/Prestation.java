package fonctionalCore;

import java.util.ArrayList;



/*
 */
public class Prestation {

    public String resultat;

    public String libelle;

    public double cout;

    public ArrayList<CodeActes> lCodeActes;

    public Prestation(String resultat, String libelle, double cout, ArrayList<CodeActes> lCodeActes) {
        this.resultat = resultat;
        this.libelle = libelle;
        this.cout = cout;
        this.lCodeActes = lCodeActes;
    }

}
