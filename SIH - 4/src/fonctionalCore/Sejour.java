package fonctionalCore;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class Sejour {

    private String id_Sejour;
  private String hospitalisation;
 private String IPP;
  private String dateEntree;

  private String dateSortie;

  private String lettreSortie;

    private String phRef;
    private String motifA;
    public Sejour(String idsejour,String IPP,String hospitalisation, String dateEntree,  String phRef,String motifA) {
       this.id_Sejour = idsejour;
        this.hospitalisation = hospitalisation;
        this.dateEntree = dateEntree;
        this.phRef = phRef;
        this.IPP=IPP;
        this.motifA=motifA;
    }
  
   public Sejour() {
        
    } 
   
   
   public static Sejour RecupererSejour(String ipp){
        try{    
            String query="SELECT * FROM sejour WHERE IPP='"+ipp+"' AND dateSortie IS NULL";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
                 
            Sejour s = new Sejour(rst.getString("id_Sejour"),ipp,rst.getString("hospitalisation"),rst.getString("dateEntree"),rst.getString("idPHref"),rst.getString("motifAdmission"));
            
            return s;
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            Sejour s=new Sejour();
            return s;
        }       
   }
   
   public static String ajouterLettreDeSortie(String date,String lettre,String idsej){
       try{    
            String query="INSERT INTO sejour(dateSortie,lettreSortie) VALUE ('"+date+"','"+lettre+"') WHERE id_Sejour ='"+idsej+"'";     
            cnx=connecterDB();
            st=cnx.createStatement();
            st.executeUpdate(query);
                 
            
            return "La lettre de sortie à bien été enregistrée.";
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }       
   }
   
   
   
   
   /****************************AJOUTER OBSERVATION*********************************/

public static String AjouterObservation(String obs,String date, String ipp){
    
    try{ 
        String query="INSERT INTO observation(obs,date) VALUE('"+obs+"','"+date+"')";  
        cnx=connecterDB();
        st=cnx.createStatement();
        st.executeUpdate(query);
       
        String query2="Select id_observation FROM observation WHERE obs='"+obs+"' AND date='"+date+"'";
        rst=st.executeQuery(query2);
        rst.next();
        String idDeLObs =rst.getString("id_observation");

        Sejour s = RecupererSejour(ipp);
        query2="Select id_DMane FROM dmane WHERE id_sejour='"+s.getId_Sejour()+"'";
        rst=st.executeQuery(query2);
        rst.next();
        String idDmAnes ="";
        
       // lier a dm anesthesie
        if(rst.getRow()>=1){
            idDmAnes =rst.getString("id_observation");
            String query3="INSERT INTO observation_iddmane VALUES('"+idDeLObs+"','"+idDmAnes+"')";
            st.executeUpdate(query3);
        }
        
        //liaison avec dm clinique
        query2="Select id_DMcli FROM dmcli WHERE id_sejour='"+s.getId_Sejour()+"'";
        rst=st.executeQuery(query2);
        rst.next();
        String idDmCli ="";
  
        if(rst.getRow()>=1){
            idDmCli =rst.getString("id_observation");
            
    
            String query4="INSERT INTO observation_iddmcli  VALUES ('"+idDeLObs+"','"+idDmCli+"')";
            st.executeUpdate(query4);
        }
        
        
         
    
    // liaison avec dm medico tehnique

        query2="Select id_DMmt FROM dmmt WHERE id_sejour='"+s.getId_Sejour()+"'";
        rst=st.executeQuery(query2);
        rst.next();
        String idDmMt ="";
  
        if(rst.getRow()>=1){
            idDmMt =rst.getString("id_observation");
            String query5="INSERT INTO observation_iddmmt  VALUES ('"+idDeLObs+"','"+idDmMt+"')";
            st.executeUpdate(query5);
        }
        
    }catch(SQLException e){
        return e.getMessage();
    }
    return "L'observation à bien été ajoutée";
    
    
}

    /**
     * @return the id_Sejour
     */
    public String getId_Sejour() {
        return id_Sejour;
    }

    /**
     * @param id_Sejour the id_Sejour to set
     */
    public void setId_Sejour(String id_Sejour) {
        this.id_Sejour = id_Sejour;
    }

    /**
     * @return the hospitalisation
     */
    public String isHospitalisation() {
        return hospitalisation;
    }

    /**
     * @param hospitalisation the hospitalisation to set
     */
    public void setHospitalisation(String hospitalisation) {
        this.hospitalisation = hospitalisation;
    }

    /**
     * @return the dateEntree
     */
    public String getDateEntree() {
        return dateEntree;
    }

    /**
     * @param dateEntree the dateEntree to set
     */
    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }

    /**
     * @return the dateSortie
     */
    public String getDateSortie() {
        return dateSortie;
    }

    /**
     * @param dateSortie the dateSortie to set
     */
    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    /**
     * @return the lettreSortie
     */
    public String getLettreSortie() {
        return lettreSortie;
    }

    /**
     * @param lettreSortie the lettreSortie to set
     */
    public void setLettreSortie(String lettreSortie) {
        this.lettreSortie = lettreSortie;
    }


    /**
     * @return the phRef
     */
    public String getPhRef() {
        return phRef;
    }

    /**
     * @param phRef the phRef to set
     */
    public void setPhRef(String phRef) {
        this.phRef = phRef;
    }
  

  public static ArrayList<Sejour> listerSejours(String ipp1){   
        ArrayList <Sejour> listDesSejours = new ArrayList();
        String query="SELECT * FROM Sejour where IPP="+ipp1;

        try{
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);

            while(rst.next()){
               String ipp=rst.getString("IPP");
            String idSej =rst.getString("id_Sejour");
            String hospi =rst.getString("hospitalisation");
            String dateE =rst.getString("dateEntree");
            String dateS=rst.getString("dateSortie");
           
            String idPH=rst.getString("idPHref");
            String motifA =rst.getString("motifAdmission");
            boolean hospit=Boolean.parseBoolean(hospi);
            Sejour s= new Sejour(idSej, ipp, hospi, dateE, dateS,motifA);
                listDesSejours.add(s);
            }   
        }catch(Exception e){
           System.out.println(e.getMessage());
           return listDesSejours;
        }
         return listDesSejours;
    } 
}