package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 */
public class DM_mt {

  public String observation;

    /**
   * 
   * @element-type DM
   */
    public ArrayList<Prestation>  lPrestation;

    public DM_mt() {
        lPrestation = new ArrayList<Prestation>();
    }
    
/****************************AJOUTER OBSERVATION*********************************/

public static void AjouterObservation(String obs,int id_dmane,int id_dmcli, int id_dmmt){
    
    try{ 
        String query="INSERT INTO observation VALUE('"+obs+"')";
        
/*TEST CONSOLE*/      System.out.println("La requete pour l'insertion d'une observation : "+query);   
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
       
        // recupère l'id de l'observation
        String query2="Select id_observation FROM observation WHERE obs=LOWER('"+obs+"')";
        rst=st.executeQuery(query2);
        rst.next();
        int idDeLObs =rst.getInt("id_observation");
/*TEST console*/ System.out.println("id de l'observation : "+idDeLObs);

       // lier a dm anesthesie
       
        String query3="INSERT INTO observation-iddmane VALUES('"+idDeLObs+"','"+id_dmane+"')";
        st.executeUpdate(query3);
        
        
         //liaison avec dm clinique
    
    String query4="INSERT INTO observation-iddmcli  VALUES ('"+idDeLObs+"','"+id_dmcli+"')";
    st.executeUpdate(query4);
    
    // liaison avec dm medico tehnique
    
      String query5="INSERT INTO observation-iddmmt  VALUES ('"+idDeLObs+"','"+id_dmmt+"')";
    st.executeUpdate(query5);
        
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    
    
}
	
    /**
   * 
   * @element-type Patient
   */

}