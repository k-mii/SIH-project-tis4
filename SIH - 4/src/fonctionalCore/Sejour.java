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
  private String motif;

    private String phRef;

    public Sejour(String idsejour,String IPP,String hospitalisation, String dateEntree,  String phRef) {
       this.id_Sejour = idsejour;
        this.hospitalisation = hospitalisation;
        this.dateEntree = dateEntree;
        this.phRef = phRef;
        this.IPP=IPP;
        
    }
    
     public Sejour(String idsejour,String IPP,String hospitalisation, String dateEntree,String dateSortie,String lettre,  String phRef,String motif) {
       this.id_Sejour = idsejour;
        this.hospitalisation = hospitalisation;
        this.dateEntree = dateEntree;
        this.phRef = phRef;
        this.IPP=IPP;
        this.dateSortie=dateSortie;
        this.lettreSortie=lettre;
        this.motif=motif;
        
    }
   public Sejour() {
        
    } 
   
   
    public static Sejour RecupererSejour(String ipp){
        Sejour s=new Sejour();
        try{    
            String query="SELECT * FROM sejour WHERE IPP='"+ipp+"' AND dateSortie IS NULL";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
                 
            s = new Sejour(rst.getString("id_Sejour"),ipp,rst.getString("hospitalisation"),rst.getString("dateEntree"),rst.getString("dateSortie"),rst.getString("lettreSortie"),rst.getString("idPHref"),rst.getString("motifAdmission"));
            
            
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
           
            return s;
        } 
        return s;
   }
    
     public static String checkSejour(String ipp){
        String s="NoSej";
        try{    
            String query="SELECT * FROM sejour WHERE IPP='"+ipp+"' AND dateSortie IS NULL";    
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
            if(rst.getRow()>=1){
                s="sejourEnCour";
            }  
            
            
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
           
            return s;
        } 
        return s;
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
   
   public static ArrayList ListDesHospitalisation(String ipp){
        ArrayList<Sejour> ListHospi = new ArrayList();
       try{    
            String query="SELECT * FROM sejour WHERE IPP='"+ipp+"' AND hospitalisation = 1";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            
            while(rst.next()){
                Sejour s = new Sejour(rst.getString("id_Sejour"),ipp,rst.getString("hospitalisation"),rst.getString("dateEntree"),rst.getString("dateSortie"),rst.getString("lettreSortie"),rst.getString("idPHref"),rst.getString("motifAdmission"));
            
                ListHospi.add(s);
            }
                 
            
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }    
       return ListHospi;
   }
   
    public static ArrayList ListDesConsultation(String ipp){
        ArrayList<Sejour> ListConsult = new ArrayList();
       try{    
            String query="SELECT * FROM sejour WHERE IPP='"+ipp+"' AND hospitalisation = 0";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
            while(rst.next()){     
                Sejour s = new Sejour(rst.getString("id_Sejour"),ipp,rst.getString("hospitalisation"),rst.getString("dateEntree"),rst.getString("dateSortie"),rst.getString("lettreSortie"),rst.getString("idPHref"),rst.getString("motifAdmission"));
            
                ListConsult.add(s);
            }
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }    
       return ListConsult;
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
        return getHospitalisation();
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

    /**
     * @return the hospitalisation
     */
    public String getHospitalisation() {
        return hospitalisation;
    }

    /**
     * @return the IPP
     */
    public String getIPP() {
        return IPP;
    }

    /**
     * @param IPP the IPP to set
     */
    public void setIPP(String IPP) {
        this.IPP = IPP;
    }

    /**
     * @return the motif
     */
    public String getMotif() {
        return motif;
    }

    /**
     * @param motif the motif to set
     */
    public void setMotif(String motif) {
        this.motif = motif;
    }
  
}