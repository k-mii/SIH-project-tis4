package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;



/*
 */
public class Prestation {

    private String resultat;

    private String id;
    
    private String date;
    private String type;

  //  public double cout;

 //   public ArrayList<CodeActes> lCodeActes;

    public Prestation(String resultat, String id, String date,String type) {
        this.resultat = resultat;
        this.id = id;
        this.date=date;
        this.type=type;
    }

    public static String AjouterResultat(String resultat, String  id_p){
   
        try{ 

           // update le champ résultat de la prestation de l'id  correspondant '
            String query = "UPDATE prestation SET resultat='"+ resultat+"'WHERE id_prestation='"+id_p+"'";

                        cnx=connecterDB();
                        st=cnx.createStatement();
                        st.executeUpdate(query);

            String message = "Le résultat a bien été enregistré";
            return message;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    
    public static String DemandeDePrestation(String ipp,String date,String type, PH p){
        String message="";
        try{ 

            String query="INSERT INTO prestation(TypePrestation,date) VALUE ('"+type+"','"+date+"')";     
System.out.println(query);                  
            cnx=connecterDB();
            st=cnx.createStatement();
            st.executeUpdate(query);           
            query="SELECT id_prestation FROM prestation WHERE TypePrestation='"+type+"' AND date='"+date+"'"; 
System.out.println(query);           
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
            String idpresta=rst.getString("id_prestation");           
            Sejour s = Sejour.RecupererSejour(ipp);
            String id_dm="";
            if(!p.getSpecialite().equals("5")){// si pas anesthesiste
                query="SELECT id_DMcli FROM dmcli WHERE id_sejour='"+s.getId_Sejour()+"'";
System.out.println(query); 
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                rst.next();
                id_dm =rst.getString("id_DMcli");          
                query="INSERT INTO prestation_iddmcli(id_prestation,id_DMcli) VALUE ('"+idpresta+"','"+id_dm+"')"; 
System.out.println(query); 
                cnx=connecterDB();
                st=cnx.createStatement();
                st.executeUpdate(query);              
                message = "La demande d'analyse à bien été envoyée.";
            }else{
                query="SELECT id_DMane FROM dmane WHERE id_sejour='"+s.getId_Sejour()+"'";
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                rst.next();
                id_dm =rst.getString("id_DMane");        
                query="INSERT INTO prestation_iddmane(id_prestation,id_DMid_DMane) VALUE ('"+idpresta+"','"+id_dm+"')"; 
                cnx=connecterDB();
                st=cnx.createStatement();
                st.executeUpdate(query);          
                message = "La demande d'analyse à bien été envoyée.";
            }
            


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }
    
    public static ArrayList ListDesPrestation(String ipp){     
        ArrayList<Prestation> listPresta = new ArrayList();
        ArrayList<String> ListDmAne =new ArrayList();
        ArrayList<String> ListDmcli =new ArrayList();
        ArrayList<String> ListDmmt =new ArrayList();
        ArrayList<String> ListidPresta =new ArrayList();
        try{ 
            String query="SELECT id_DMane FROM dmane WHERE IPP='"+ipp+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                String IdDmAne=rst.getString("id_DMane");
                ListDmAne.add(IdDmAne);                  
            }
            
            query="SELECT id_DMcli FROM dmcli WHERE IPP='"+ipp+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                String IdDmcli=rst.getString("id_DMcli");
                ListDmcli.add(IdDmcli);              
            }
            
            query="SELECT id_DMmt FROM dmmt WHERE IPP='"+ipp+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                String IdDmmt=rst.getString("id_DMmt");
                ListDmmt.add(IdDmmt);              
            }
            
            for (String idDm : ListDmAne){
                query="SELECT id_prestation FROM prestation_iddmane WHERE id_DMane='"+idDm+"'";
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_prestation");
                    ListidPresta.add(Id_Pres);  
                    
                }
            }
            for (String idDm : ListDmcli){
                query="SELECT id_prestation FROM prestation_iddmcli WHERE id_DMcli='"+idDm+"'";                  
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_prestation");
                    ListidPresta.add(Id_Pres);                      
                }
            }
            
            for (String idDm : ListDmmt){
                query="SELECT id_prestation FROM prestation_iddmmt WHERE id_DMmt='"+idDm+"'";                  
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_prestation");
                    ListidPresta.add(Id_Pres);                      
                }
            }
            for (String Id_pres : ListidPresta){
                
                query="SELECT * FROM prestation WHERE id_prestation='"+Id_pres+"'";                 
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){                    
                    
                    Prestation presta = new Prestation(rst.getString("resultat"),Id_pres,rst.getString("date"),rst.getString("TypePrestation"));
                   
                    listPresta.add(presta);                 
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        
        return listPresta;
        
    }

    /**
     * @return the resultat
     */
    public String getResultat() {
        return resultat;
    }

    /**
     * @param resultat the resultat to set
     */
    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
