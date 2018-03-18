package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;



public class PH extends Personne {

    private String specialite;
    private String id_PH;


    public PH(String id_ph,String specialite, String nom, String prenom,String nTel) {
        super(nom, prenom, nTel, null);
        this.specialite = specialite;
        this.id_PH = id_ph;

    }

    public PH() {
        super();
    }
    
    public PH(String specialite) {
        this.specialite=specialite;
    }

/************************************************************
              DEFINIR LISTE PH EN FONCTION SERVICE           
 ***********************************************************/    
    public static ArrayList DefinirListeDesPH(int idService){
        ArrayList <PH> listPH = new ArrayList();
        String query="";
        query ="SELECT * FROM ph WHERE Specialite='"+idService+"' ORDER BY nom, prenom";

/*TEST CONSOLE*/ System.out.println("requete pour selectioner les ph enfonction de la specialité : "+query);
        try{
            cnx=DB_Link.connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);

            while(rst.next()){
                PH ph = new PH(rst.getString("id_PH"),rst.getString("Specialite"), rst.getString("nom"), rst.getString("prenom"),rst.getString("ntel"));       
                listPH.add(ph);
            }   
        }catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return listPH;
    }

    
    
    public String getSpecialite() {return specialite;}
    public void setSpecialite(String specialite) {this.specialite = specialite;}
    
    public String getNom(){ return super.getNom();}
    public void setNom(String nom){ super.setNom(nom);}
    
    public String getPrenom(){ return super.getPrenom();}
    public void setPrenom(String prenom){ super.setPrenom(prenom);}
    
    public String getnTel(){ return super.getnTel();}
    public void setnTel(String ntel){ super.setnTel(ntel);}
    
    public String getId_PH(){ return id_PH;}
    public void setId_Ph(String id_ph){ this.id_PH = id_ph;}

}