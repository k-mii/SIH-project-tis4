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
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author camille
 */
public class Patient extends Personne {  
    private String ipp;
  
    
    private String nomDeNaissance;
    private String lieuDeNaissance;
    private String medecin;
    private String adresse;
    private String dateDeNaissance;
   
    private String id_confiance; // relatif a l'id de la personne de confiance
    private String code_postal;
    private String ville;

    public Patient(String ipp, String nom, String prenom, String nTel, String nomNaiss, String lieuNaiss, String med, String adresse, String dateNaiss, Sexe sexe, String persConf, String cp, String ville) {
         super(nom, prenom,nTel, sexe );
        this.ipp=ipp;
       
        
        this.nomDeNaissance=nomNaiss;
        this.lieuDeNaissance=lieuNaiss;
        this.medecin=med;
        this.adresse=adresse;
        this.dateDeNaissance=dateNaiss;
        
        this.id_confiance = persConf;
        this. code_postal = cp;
        this.ville = ville;
    
    }
    public Patient() {
         super();
    }
    public String getIpp() {
        return ipp;
    }
    public void setIpp(String ipp) {
        this.ipp = ipp;
    }

    public String getNomDeNaissance() {
        return nomDeNaissance;
    }
    public void setNomDeNaissance(String nomDeNaissance) {
        this.nomDeNaissance = nomDeNaissance;
    }
   
    public String getLieuDeNaissance() {
        return lieuDeNaissance;
    }
    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }
    
    public String getMedecin() {
        return medecin;
    }
    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }
    
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getDateDeNaissance() {
        return dateDeNaissance;
    }
    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
   
  
 
    public String getId_confiance() {
        return id_confiance;
    }
    public void setId_confiance(String id_confiance) {
        this.id_confiance = id_confiance;
    }


    public String getCode_postal() { return code_postal;    }
    public void setCode_postal(String code_postal) {this.code_postal = code_postal;}

    public String getVille() { return ville;  }
    public void setVille(String ville) { this.ville = ville;}
    
    
    public static ArrayList rechercherPatient(String nom, String prenom, String ipp){
/* TEST console */    System.out.println("Méthode DB_Link.rechercherPatient()");    
    
ArrayList <Patient> listDesPatient = new ArrayList();
     String query="";
     
     if(nom.equals("") && prenom.equals("") && ipp.equals("")){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Veuillez saisir le nom, le prenom ou l'IPP du patient.");
  
     }else if(nom.equals("A") && prenom.equals("A") && ipp.equals("A")){
        query ="SELECT * FROM patient ORDER BY nom, prenom";
         
        
     }else if(!nom.equals("") && prenom.equals("") && ipp.equals("")){
        System.out.println("NOM");
        query ="SELECT * FROM patient WHERE nom=LOWER('"+nom+"') ORDER BY nom, prenom";
        
     }else if(!nom.equals("") && !prenom.equals("") && ipp.equals("")){
        System.out.println("NOM + PRENOM");
        query ="SELECT * FROM patient WHERE nom=LOWER('"+nom+"') AND prenom=LOWER('"+prenom+"') ORDER BY nom, prenom";
        
     }else if(!nom.equals("") && prenom.equals("") && !ipp.equals("")){
        System.out.println("NOM + IPP");
        query ="SELECT * FROM patient WHERE nom=LOWER('"+nom+"') AND IPP='"+ipp+"' ORDER BY nom, prenom";
        
     }else if(nom.equals("") && !prenom.equals("") && ipp.equals("")){
        System.out.println("PRENOM");
        query ="SELECT * FROM patient WHERE prenom=LOWER('"+prenom+"') ORDER BY nom, prenom ";
         
     }else if(nom.equals("") && !prenom.equals("") && !ipp.equals("")){
        System.out.println("PRENOM + IPP");
        query ="SELECT * FROM patient WHERE prenom=LOWER('"+prenom+"') AND IPP='"+ipp+"' ORDER BY nom, prenom";
         
     }else if(nom.equals("") && prenom.equals("") && !ipp.equals("")){
        System.out.println("IPP");
        query ="SELECT * FROM patient WHERE IPP='"+ipp+"' ORDER BY nom, prenom";

    }
     
     try{
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        
        while(rst.next()){
            Patient p = new Patient();       
            p.setAdresse(rst.getString("adresse"));
            p.setDateDeNaissance(rst.getString("datenaissance"));
            p.setIpp(rst.getString("IPP"));
            p.setLieuDeNaissance(rst.getString("lieunaissance"));
            p.setNom(rst.getString("nom"));
            p.setPrenom(rst.getString("prenom"));
            p.setNomDeNaissance(rst.getString("nomnaissance"));
            p.setnTel( rst.getString("ntel"));
            p.setSexe(Sexe.valueOf(rst.getString("sexe")));
            p.setMedecin(rst.getString("medecinG"));
            p.setVille(rst.getString("ville"));
            p.setCode_postal(rst.getString("code_postal"));
            p.setId_confiance(rst.getString("id_confiance"));
            
            listDesPatient.add(p);
        }   
    }catch(SQLException e){
       System.out.println(e.getMessage());
       return listDesPatient;
    }
     return listDesPatient;
}  
 
    
    public static String AjouterPatient(int IPP,String prenom, String nom,String tel, String nomnNaiss,String lieuNaiss, String medG,String adresse,String cp, String ville, String dateNaiss, String sexe, int idPersConf){
/* TEST console */    System.out.println("Méthode DB_Link.AjouterPatient()"); 

// ajout d'un dm  et dma en meme tps
   
try{ 
        String query="INSERT INTO patient VALUE ('"+IPP+"',LOWER('"+nom+"'),LOWER('"+prenom+"'),'"+tel+"','"+nomnNaiss+"','"+lieuNaiss+"','"+medG+"','"+adresse+"', '"+dateNaiss+"','"+sexe+"','"+idPersConf+"','"+cp+"','"+ville+"')";
/*TEST CONSOLE*/      System.out.println("La requete pour l'insertion d'in nouveau patient : "+query);   
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
        String message = "La patient à bien été enregistré";
        return message;
       
    }catch(SQLException e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }
}
    
       
   
/***********************************************************************
                  AFFICHER LES INFO DU PATIENT SELECTIONNER             
***********************************************************************/
public static Patient AfficherInfoPatient(String nom, String prenom,String dateNaiss){
/* TEST console */    System.out.println("Méthode DB_Link.AfficherInfoPatient()");     
    try{ 
        String query="SELECT * FROM patient WHERE nom='"+nom+"' AND prenom ='"+prenom+"' AND datenaissance='"+dateNaiss+"'";
/* TEST console*/        System.out.println(query);
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        rst.next();
        
        String ippP=rst.getString("IPP");
        String nTelP =rst.getString("ntel");
        String nomNaissP =rst.getString("nomnaissance");
        String lieuNaissP =rst.getString("lieunaissance");
        String medP=rst.getString("medecinG");
        String adresseP =rst.getString("adresse");
        String sexeP=rst.getString("sexe");
        String ville =rst.getString("ville");
        String cp = rst.getString("code_postal");
        String persConf=rst.getString("id_confiance");
        Sexe sexePc= Sexe.valueOf(sexeP);
        Patient monPatient = new Patient(ippP,nom,prenom,nTelP,nomNaissP,lieuNaissP,medP,adresseP,dateNaiss,sexePc, persConf,cp,ville);
        return monPatient;
       
    }catch(SQLException e){
        System.out.println(e.getMessage());
        Patient monPatient = new Patient();
        return monPatient;
    }
}
 
/*************************************************************
                  COMPTER NB DE PATIENT CETTE ANNEE           
*************************************************************/
// sert a définir l'IPP des patient                           
public static int CountLeNombreDePatientCetteAnnee(int annee){
    int nbPatient=0;
    
    try{ 
        String query="SELECT COUNT(*) as nombreP FROM patient WHERE IPP LIKE '18%' ";
/*TEST CONSOLE */ System.out.println("la requete pour compter le nombre de patient : "+query);
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        rst.next();
        nbPatient=Integer.parseInt(rst.getString("nombreP"));      
    }catch(SQLException e){
        System.out.println(e.getMessage());
        System.out.println("ya un soucisssss");
    }
/*TEST CONSOLE */ System.out.println("nombre de patient trouvé : "+nbPatient);
    return nbPatient;
}
   
/*************************************************************
                      DEFINIR CODE POSTAL                     
*************************************************************/
public static ArrayList DefinirListeCodePostal(){
    ArrayList<String> list_CP = new ArrayList<String>();
    try{ 
        String query="SELECT ville_code_postal FROM villes_france_free ORDER BY ville_code_postal";
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        while(rst.next()){
                list_CP.add(rst.getString("ville_code_postal"));
        } 
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return list_CP;
}

/********************************************************
                      DEFINIR VILLE                      
********************************************************/
public static ArrayList DefinirListeVille(String cp){
    ArrayList <String> listVille= new ArrayList();
    try{ 
        String query="SELECT ville_nom_reel FROM villes_france_free WHERE ville_code_postal='"+cp+"'";
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        while(rst.next()){
            listVille.add(rst.getString("ville_nom_reel"));
        }    
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    
    return listVille;
}

/***********************************************************************
                          MODIFIER UN PATIENT                           
***********************************************************************/
public static String ModifierPatient (String ipp,String nom,String prenom, String tel, String nomNaiss,String lieuNaiss, String medG,String adresse,String cp, String ville, String dateNaiss, String sexe){
    try{
        String query = "UPDATE patient SET nom = LOWER('"+nom+"'),prenom =LOWER('"+prenom+"'), ntel='"+tel+"', nomnaissance='"+nomNaiss+"',lieunaissance='"+lieuNaiss+"',medecinG='"+medG+"',adresse='"+adresse+"',datenaissance='"+dateNaiss+"',sexe='"+sexe+"',code_postal='"+cp+"',ville='"+ville+"' WHERE IPP='"+Integer.parseInt(ipp)+"'";
/*TEST CONSOLE*/  System.out.println("La requete de la modification du patient :"+query);     
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
        
/*TEST CONSOLE*/ System.out.println("MODIF PERSONNE OK");
        return "Les modifications ont bien été enregistrés.";
        
    }catch(SQLException e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }

}
}
