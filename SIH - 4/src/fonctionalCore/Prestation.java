package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
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

    public static String AjouterResultat(String resultat, String  id_p){
   
        try{ 

           // update le champ résultat de la prestation de l'id  correspondant '
            String query = "UPDATE prestation SET resultat='"+ resultat+"'WHERE id_prestation='"+id_p+"'";

                        cnx=connecterDB();
                        st=cnx.createStatement();
                        st.executeUpdate(query);

            String message = "Le résultat a bien été enregistré";
            return message;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    
}
