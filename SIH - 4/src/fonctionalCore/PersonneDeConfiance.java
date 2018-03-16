/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;


/**
 *
 * @author agnès
 */
public class PersonneDeConfiance extends Personne{
    
    private String id_confiance;
    private String adresse;
    private String code_postal;
    private String  ville;
    
    private String relation;

    public PersonneDeConfiance(String id_confiance,String  nom, String prenom,String adresse, String code_postal,String ville,String ntel,String relation){
        super(nom, prenom,ntel, null );
        this.id_confiance = id_confiance;
    
        this.adresse =adresse;
  
        this.code_postal=code_postal;
        this.ville=ville;
      
        this.relation=relation;


    }

    public PersonneDeConfiance() {
    }
    

    /**
     * @return the id_confiance
     */
    public String getId_confiance() {
        return id_confiance;
    }

    /**
     * @param id_confiance the id_confiance to set
     */
    public void setId_confiance(String id_confiance) {
        this.id_confiance = id_confiance;
    }

    /**
     * @return the nom
     */
   

    /**
     * @return the code_postal
     */
    public String getCode_postal() {
        return code_postal;
    }

    /**
     * @param code_postal the code_postal to set
     */
    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @return the ntel
     */

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @param relation the relation to set
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
   
    
/***********************************************************************
                     AJOUTER UNE PERSONNE A CONTACTER                   
***********************************************************************/
public static int AjouterPersonneDeConfiance(String prenom,String nom, String adresse, String cp, String ville, String numTel, String relation){
  int idDeLaPers=0;
    try{ 
        String query="INSERT INTO p_confiance(nom,prenom,adresse,code_postal,ville,nTel,relation) VALUE (LOWER('"+nom+"'),LOWER('"+prenom+"'),'"+adresse+"', '"+cp+"','"+ville+"','"+numTel+"','"+relation+"')";
/*TEST CONSOLE*/      System.out.println("La requete pour l'insertion d'une pers de confiance : "+query);   
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
       
        // recupère l'id de la persone
        String query2="Select id_confiance FROM p_confiance WHERE nom=LOWER('"+nom+"') AND prenom=LOWER('"+prenom+"') AND adresse='"+adresse+"' AND code_postal='"+cp+"' AND ville='"+ville+"' AND nTel='"+numTel+"' AND relation='"+relation+"'";
        rst=st.executeQuery(query2);
        rst.next();
        idDeLaPers =rst.getInt("id_confiance");
/*TEST console*/ System.out.println("id de la pers de confiance : "+idDeLaPers );
        
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
  return idDeLaPers;
}


/***********************************************************************
                AFFICHER LES INFO DE PERSONNE A CONTACTER               
***********************************************************************/
public static PersonneDeConfiance AfficherInfoPersonneConfiance(String id){
/* TEST console */    System.out.println("Méthode DB_Link.AfficherInfoPersonneConfiance()");     
     PersonneDeConfiance maPersonneDeConf = new PersonneDeConfiance();
    try{ 
        String query="SELECT * FROM p_confiance WHERE id_confiance='"+Integer.parseInt(id)+"'";
/* TEST console*/        System.out.println(query);
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        rst.next();
        
        String nTel =rst.getString("ntel");
        String nom =rst.getString("nom");
        String prenom =rst.getString("prenom");
        String adresse =rst.getString("adresse");
        String cp =rst.getString("code_postal");
        String ville =rst.getString("ville");
        String relation=rst.getString("relation");
        maPersonneDeConf.setId_confiance(id);
        maPersonneDeConf.setNom(nom);
        maPersonneDeConf.setPrenom(prenom);
        maPersonneDeConf.setAdresse(adresse);
        maPersonneDeConf.setCode_postal(cp);
        maPersonneDeConf.setVille(ville);
        maPersonneDeConf.setRelation(relation);
        maPersonneDeConf.setnTel(nTel);
        
       
    }catch(SQLException e){
        System.out.println(e.getMessage());
       
    }
    return maPersonneDeConf;
}


/********************************************************
             MODIFIER PERSONNE DE CONFIANCE              
********************************************************/
public static void ModifierPersDeConfiance(String id,String prenom,String nom,String adresse,String cp,String ville,String ntel,String relation){
    try{
        String query="UPDATE p_confiance SET nom='"+nom+"',prenom='"+prenom+"',adresse='"+adresse+"',code_postal='"+cp+"',ville='"+ville+"',ntel='"+ntel+"',relation='"+relation+"'WHERE id_confiance='"+id+"'";
/*TEST CONSOLE*/  System.out.println("La requete de la modification du patient :"+query);  
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
        
/*TEST CONSOLE*/ System.out.println("MODIF PERS CONFIANCE OK");
       
        
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
}

}


