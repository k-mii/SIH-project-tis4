package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;



public class PH extends Personne {

    private String specialite;


    public PH(String specialite, String nom, String prenom,String nTel) {
        super(nom, prenom, nTel, null);
        this.specialite = specialite;

    }

    public PH() {
        super();
    }
    

/************************************************************
              DEFINIR LISTE PH EN FONCTION SERVICE           
 ***********************************************************/    
    public static ArrayList DefinirListeDesPH(String service){
        ArrayList listPH = new ArrayList();
        String query="";
        query ="SELECT * FROM ph ORDER BY nom, prenom WHERE Specialite='"+service+'"';

        try{
            cnx=DB_Link.connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);

            while(rst.next()){
                PH ph = new PH();       
                ph.setSpecialite(rst.getString("Specialite"));
                ph.setNom("nom");
                ph.setPrenom("prenom");
                ph.setnTel("nTel");

                listPH.add(ph);
            }   
        }catch(SQLException e){
           System.out.println(e.getMessage());
           return listPH;
        }
        return listPH;
    }

    
    
    public String getSpecialite() {return specialite;}
    public void setSpecialite(String specialite) {this.specialite = specialite;}
    
    public String getNom(){ return super.getNom();}
    public void setNom(String nom){ super.setNom(nom);}
    
    public String getPrenom(){ return super.getNom();}
    public void setPrenom(String prenom){ super.setNom(prenom);}
    
    public String getnTel(){ return super.getnTel();}
    public void setnTel(String ntel){ super.setnTel(ntel);}
    

}