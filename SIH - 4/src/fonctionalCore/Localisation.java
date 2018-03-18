package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;



public class Localisation {

  private String id_loca;
  private String lit;
  private String chambre;
  private String secteur;
  private String service;
  private String ipp;

    public Localisation(String lit, String chambre, String secteur) {
        this.lit = lit;
        this.chambre = chambre;
        this.secteur = secteur;
    }
    
    public Localisation() {

    }
    
    public Localisation(String id_loca,String lit, String chambre, String secteur,String service,String ipp) {
        this.id_loca=id_loca;
        this.lit = lit;
        this.chambre = chambre;
        this.secteur = secteur;
        this.service = service;
        this.ipp = ipp;
    }
    
    
/********************************************************
      DEFINIR LISTE CHAMBRE DISPO DANS UN SERVICE        
********************************************************/
    public static ArrayList DefinirListChambre(String service){ 
        ArrayList<Localisation> listDesLoca = new ArrayList();
         try{ 
            String query="SELECT * FROM localisation WHERE Secteur='"+service+"'";      
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while(rst.next()){
                Localisation laLoca = new Localisation(rst.getString("id_localisation"),rst.getString("lit"),rst.getString("chambre"),rst.getString("Secteur"),rst.getString("Service"),rst.getString("IPP"));
                listDesLoca.add(laLoca);
            }    
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }    
        return listDesLoca;
    }
    
/********************************************************
                    DEFINIR LISTE CHAMBRE                
********************************************************/ 
     public static ArrayList DefinirListChambre(){
        ArrayList<Localisation> listDesLoca = new ArrayList();
         try{ 
            String query="SELECT * FROM localisation";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while(rst.next()){
                Localisation laLoca = new Localisation(rst.getString("id_localisation"),rst.getString("lit"),rst.getString("chambre"),rst.getString("Secteur"),rst.getString("Service"),rst.getString("IPP"));
                listDesLoca.add(laLoca);
            }    
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }    
        return listDesLoca;
    }
    
/********************************************************
              LOCALISATION DU PATIENT X                  
********************************************************/ 
     public static Localisation TrouverPatient(String ipp){
        Localisation loca =new Localisation();
        try{ 
            String query="SELECT * FROM localisation WHERE IPP='"+ipp+"'";
            String chambre ="NA";
            String secteur ="NA";
            System.out.println(query);
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();          
            if(rst.getRow()>= 1){
                chambre =rst.getString("chambre");
                secteur =rst.getString("secteur");
            }
            
            loca.setChambre(chambre);
            loca.setSecteur(secteur);
              
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }    
        return loca;
    }
    

    /**
     * @return the id_loca
     */
    public String getId_loca() {
        return id_loca;
    }

    /**
     * @param id_loca the id_loca to set
     */
    public void setId_loca(String id_loca) {
        this.id_loca = id_loca;
    }

    /**
     * @return the lit
     */
    public String getLit() {
        return lit;
    }

    /**
     * @param lit the lit to set
     */
    public void setLit(String lit) {
        this.lit = lit;
    }

    /**
     * @return the chambre
     */
    public String getChambre() {
        return chambre;
    }

    /**
     * @param chambre the chambre to set
     */
    public void setChambre(String chambre) {
        this.chambre = chambre;
    }

    /**
     * @return the secteur
     */
    public String getSecteur() {
        return secteur;
    }

    /**
     * @param secteur the secteur to set
     */
    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the ipp
     */
    public String getIpp() {
        return ipp;
    }

    /**
     * @param ipp the ipp to set
     */
    public void setIpp(String ipp) {
        this.ipp = ipp;
    }
    
    
  
}