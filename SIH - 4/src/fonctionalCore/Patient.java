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
import static fonctionalCore.Localisation.*;
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
    
    
    /************************************************************
                        RECHERCHER UN PATIENT                    
    ************************************************************/      
    public static ArrayList rechercherPatient(String nom, String prenom, String ipp){   

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

    /***********************************************************
                         AJOUTER UN PATIENT                     
    ***********************************************************/

    public static String AjouterPatient(int IPP,String prenom, String nom,String tel, String nomnNaiss,String lieuNaiss, String medG,String adresse,String cp, String ville, String dateNaiss, String sexe, int idPersConf){

        try{ 
            String query="INSERT INTO patient VALUE ('"+IPP+"',LOWER('"+nom+"'),LOWER('"+prenom+"'),'"+tel+"','"+nomnNaiss+"','"+lieuNaiss+"','"+medG+"','"+adresse+"', '"+dateNaiss+"','"+sexe+"','"+idPersConf+"','"+cp+"','"+ville+"')";  
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
        try{ 
            String query="SELECT * FROM patient WHERE nom='"+nom+"' AND prenom ='"+prenom+"' AND datenaissance='"+dateNaiss+"'";
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

    
/***********************************************************************
                          NOUVELLE ADMISSION                            
***********************************************************************/
    public static String NouvelleAdmission(String ipp,int hospi_consult,String date,String id_ph,String motifAdmission, String service ){
        try{
            
            String query="INSERT INTO sejour(IPP,hospitalisation,dateEntree,idPHref,motifAdmission) VALUE ('"+Integer.parseInt(ipp)+"','"+hospi_consult+"','"+date+"','"+Integer.parseInt(id_ph)+"','"+motifAdmission+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            st.executeUpdate(query);
            
            if(hospi_consult==1){
                ArrayList<Localisation> listDesLoca =DefinirListChambre(service);
                boolean estOccupe=true;
                int i =0;
                int id_LocationInnocupe;
                int sizeList = listDesLoca.size();
                while (estOccupe==true && i<=sizeList){
                    if(listDesLoca.get(i).getIpp()==null){
                        estOccupe=false;
                        id_LocationInnocupe=i;
                    }
                    i++;
                }
                return "Le pastient à bien été admis en hospitalisation.";
            }else{
                return "Le pastient à bien été admis en consultation.";
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }       
    }


public static int generIPP(){
    Date dateAndTime = Calendar.getInstance().getTime();
    int annee = dateAndTime.getYear();

    int digit = annee % 100;

                    String lipp = Integer.toString(digit);
                    int nbDePatientCetteAnnee = Patient.CountLeNombreDePatientCetteAnnee(digit);
                    int numDePatient = nbDePatientCetteAnnee + 1;
                    int log = (int) (log10((numDePatient)) + 1);
                    int nbDeChiffreAAjouter = 7 - log;
                    for (int i = 0; i < nbDeChiffreAAjouter; i++) {
                        lipp = lipp + "0";}
                     lipp = lipp + (numDePatient + 1);
                    int IPP = Integer.parseInt(lipp);
                    return IPP;}
}
