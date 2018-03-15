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
import java.util.ArrayList;
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
/* TEST console */    System.out.println("Méthode DB_Link.connecterDB()");        
        try{
            Class.forName("com.mysql.jdbc.Driver");
/* TEST console */   System.out.println("Drivers Check"); 
            String url="jdbc:mysql://localhost:3306/sih-v5";   // a modifier en fonction du nom de la BDD définitive
            String usr="root";
            String password="";
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
/* TEST console */    System.out.println("Méthode DB_Link.Identification");        
        
        String type;
        
        // Modifier les requete en fonction des tables de la BDD définitive
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
                        String fonction =rst.getString("Specialite") ; // Récupère la string de la colonne "spécialite" de la BDD
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
