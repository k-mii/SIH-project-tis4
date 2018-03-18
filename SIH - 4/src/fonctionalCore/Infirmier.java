package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;



public class Infirmier extends Personne {

  public String id_connec;

  public String mdp;

  public String service;

    public Infirmier(String id_connec, String mdp, String service,String nom, String prenom, String nTel, String sexe) {
        super(nom, prenom,  nTel,  sexe);
          
        this.id_connec = id_connec;
        this.mdp = mdp;
        this.service = service;
    }

    public static String AfficherNomInfirmier(String id_inf){
        String query="";
        query ="SELECT * FROM infirmier WHERE id_Infirmier='"+id_inf+"'";
        try{
            cnx=DB_Link.connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
            return rst.getString("nom");
    
        }catch(SQLException e){
           System.out.println(e.getMessage());
           return "";
        }
    }
    
    
}