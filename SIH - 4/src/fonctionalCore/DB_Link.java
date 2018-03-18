/**************************************************************************************
                                                                                       
                 LA CLASSE SUIVANTE REUNIT TOUTE LES FONCTIONS                         
                  AYANT UNE CONNEXION AVEC LA BASE DE DONNEES                          
                                                                                       
 *************************************************************************************/

package fonctionalCore; 

import fonctionalCore.*;
import java.awt.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author camille
 */

public class DB_Link {
    static Connection cnx;
    static Statement st;
    static ResultSet rst;
    
    
    
/************************************************************
                 FONCTION DE CONNEXION A LA DB               
************************************************************/      
     public static Connection connecterDB(){      
        try{
            Class.forName("com.mysql.jdbc.Driver");
/* TEST console */   System.out.println("Drivers Check"); 
            String url="jdbc:mysql://localhost:3306/sih-v7";   // a modifier en fonction du nom de la BDD définitive
            String usr="root"; // a modifier en fonction du nom du usr pr l'acces a la DB
            String password="sihsql"; // a modifier en fonction du mdp du usr pr l'acces a la DB
            Connection cnx=DriverManager.getConnection(url,usr,password);
/* TEST console */    System.out.println("Connexion Check");
            return cnx;       
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    

    
    
/****************************************************************
                    METHODE D'IDENTIFICATION                     
****************************************************************/  
    public static String Identification(String id, String mdp){
        
        String type;
        
        String error ="Erreur : Conexion à la base de données impossible. ";
        String querySec="SELECT * FROM secretaire WHERE id_connec='"+id+"'AND mdp= PASSWORD('"+mdp+"')";
        String queryPh="SELECT * FROM ph WHERE id_connec='"+id+"'AND mdp=PASSWORD('"+mdp+"')";
        String queryIn="SELECT * FROM infirmier WHERE id_connec='"+id+"'AND mdp=PASSWORD('"+mdp+"')";
        
        try{ 
            cnx=connecterDB();
            if(cnx ==null){
                return error;
            }else{
                st=cnx.createStatement();
                rst=st.executeQuery(querySec);
                rst.next();
                if(rst.getRow()== 1){                 
                    type = "secretaire"; 
                    return type; 
                }else{ 
                    rst=st.executeQuery(queryPh);
                    rst.next();
                    if(rst.getRow()== 1){  
                        String fonction =rst.getString("Specialite") ; 
                        if(fonction.equals("urgence")){
                            type = "UR"; 
                            return type;
                        }else if(fonction.equals("anesthesie")){
                            type = "AN"; 
                            return type;
                        }else{
                            type = "PH"; 
                            return type;
                        }
                    }else{
                        rst=st.executeQuery(queryIn);
                        rst.next();
                        if(rst.getRow()== 1){                 
                            type = "IN"; 
                            return type; 
                        }else{
                           
                            return "Erreur : Identifiant ou mot de passe incorrect.";
                                
                        }
                    }
                }
            }
            
        }catch(SQLException e){
                return e.getMessage();
            }
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


/********************************************************
                      DEFINIR SERVICE                    
********************************************************/
public static ArrayList DefinirListService(){
    ArrayList<Service> listDesServices = new ArrayList();
     try{ 
        String query="SELECT * FROM service ORDER BY nom";
/*TEST CONSOLE*/ System.out.println("requete pour afficher liste des service : "+query);
        cnx=connecterDB();
        st=cnx.createStatement();
        rst=st.executeQuery(query);
        while(rst.next()){
            Service leService = new Service(Integer.parseInt(rst.getString("id_service")),rst.getString("nom"));
            listDesServices.add(leService);
        }    
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }    
    return listDesServices;
}





public static void AjouterPrescription(String prescri,int id_dmane,int id_dmcli){

  
    try{ 
        String query="INSERT INTO prescription VALUE('"+prescri+"')";
        
/*TEST CONSOLE*/      System.out.println("La requete pour l'insertion d'une prescription : "+query);   
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
       
        // recupère l'id de la prescription
        String query2="Select id_prescription FROM prescription WHERE prescri=LOWER('"+prescri+"')";
        rst=st.executeQuery(query2);
        rst.next();
        int idDeLaPrescri =rst.getInt("id_prescription");
/*TEST console*/ System.out.println("id de la prescription : "+idDeLaPrescri);

       // lier a dm anesthesie
       
        String query3="INSERT INTO prescription-iddmane VALUES('"+idDeLaPrescri+"','"+id_dmane+"')";
        st.executeUpdate(query3);
        
        
         //laison avec dm clinique
    
    String query4="INSERT INTO prescription-iddmcli  VALUES ('"+idDeLaPrescri+"','"+id_dmcli+"')";
    st.executeUpdate(query4);
        
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    
   
    
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

/* public static ArrayList<Sejour> InfoSejours(int IPP){
    ArrayList<Sejour> lSej= new ArrayList<Sejour>();
    try{ 
        String query="SELECT * FROM `sejour` WHERE IPP="+IPP;
        
TEST CONSOLE     System.out.println("La requete pour l'obtention desséjours: "+query);   
        cnx=connecterDB();
        st=cnx.createStatement();
        
       
       while(rst.next()){
        
            st.executeUpdate(query);
            rst=st.executeQuery(query);
            rst.next();

            String ipp=rst.getString("IPP");
            String idSej =rst.getString("id_Sejour");
            String hospi =rst.getString("hospitalisation");
            String dateE =rst.getString("dateEntree");
            String dateS=rst.getString("dateSortie");
            String letrreS =rst.getString("lettreSortie");
            String idPH=rst.getString("idPHref");
            String motifA =rst.getString("motifAdmission");
            boolean hospit=Boolean.parseBoolean(hospi);
            SimpleDateFormat dateEm= new SimpleDateFormat("dd/MM/yy hh:mi:ss");
            Date dateEt= dateEm.parse(dateE);
            Date dateSt= dateEm.parse(dateS);
            
            String query2="SELECT Specialite FROM `ph` WHERE id_PH="+idPH;
            
            st.executeUpdate(query2);
            ResultSet rst2=st.executeQuery(query2);
            rst2.next();
            String spe= rst2.getString("Specialite");
            PH phFictif=new PH(spe);
            
            Sejour sej= new Sejour(hospit,dateEt,dateSt, phFictif);
            lSej.add(sej);
    }
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    return lSej;
    
}*/

	
	  
    
    //______________________METHODE EXEMPLE   AJOUT / SUPPRESSION_________________________
    
    
     /************************************************************************************/
     /**************** __   METHODE TYPE D'AJOUT DANS LA BASE__***************************/
     /************************************************************************************/
    public static void AjouterSecrétaire(String id, String mdp, String nom, String prenom){
        try{ 
            String query="INSERT INTO secretaireadmin VALUE ('"+id+"','"+mdp+"','"+nom+"','"+prenom+"')";
            /*  Nom de la table et des colonnes et du nombre de colonne a changer en fonction de la nouvelle base*/
            cnx=connecterDB();
            st=cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("Add Check");// pour tester la fontion voir console
            // faire un interface de type machin à bien été enregistrer 
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
     
     /************************************************************************************/
     /**************** __   METHODE TYPE DE SUPPRESSION DANS LA BASE__***************************/
     /************************************************************************************/
    
    public static void SupprimerSecretaire(String id){
        try{ 
            String query="DELETE FROM secretaireadmin WHERE '"+id+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("Suppr Check"); //pour tester la fonction voir console
            // faire un e interface de type machin à bien été suprimer 
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    
    // lien youtube pour les autres truc : https://www.youtube.com/watch?v=tAcBNuuRnQc
    
    
}
