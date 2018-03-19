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
import static fonctionalCore.Sejour.RecupererSejour;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author camille
 */
public class Obersvation {
        private String id_obs;
        private String obs;
        private String date;
        
        
        public Obersvation(){
            
        }
        public Obersvation(String id, String obs, String date){
            this.id_obs=id;
            this.obs=obs;
            this.date=date;
        }
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
                idDmAnes =rst.getString("id_DMane");
                String query3="INSERT INTO observation_iddmane VALUES('"+idDeLObs+"','"+idDmAnes+"')";
                st.executeUpdate(query3);
            }

            //liaison avec dm clinique
            query2="Select id_DMcli FROM dmcli WHERE id_sejour='"+s.getId_Sejour()+"'";
            rst=st.executeQuery(query2);
            rst.next();
            String idDmCli ="";

            if(rst.getRow()>=1){
                idDmCli =rst.getString("id_DMcli");
                String query4="INSERT INTO observation_iddmcli  VALUES ('"+idDeLObs+"','"+idDmCli+"')";
                st.executeUpdate(query4);
            }

        // liaison avec dm medico tehnique

            query2="Select id_DMmt FROM dmmt WHERE id_sejour='"+s.getId_Sejour()+"'";
            rst=st.executeQuery(query2);
            rst.next();
            String idDmMt ="";

            if(rst.getRow()>=1){
                idDmMt =rst.getString("id_DMmt");
                String query5="INSERT INTO observation_iddmmt  VALUES ('"+idDeLObs+"','"+idDmMt+"')";
                st.executeUpdate(query5);
            }

        }catch(SQLException e){
            return e.getMessage();
        }
        return "L'observation à bien été ajoutée";


    }

    public static ArrayList afficherObservation(String ipp){
        ArrayList<Obersvation> listObs = new ArrayList();
        ArrayList<String> ListDmAne =new ArrayList();
        ArrayList<String> ListDmcli =new ArrayList();
        ArrayList<String> ListDmmt =new ArrayList();
        ArrayList<String> ListIdObs =new ArrayList();
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
                String IdDmMt=rst.getString("id_DMmt");
                ListDmmt.add(IdDmMt);              
            }
            for (String idDm : ListDmAne){
                query="SELECT id_observation FROM observation_iddmane WHERE id_DMane='"+idDm+"'";
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_observation");
                    ListIdObs.add(Id_Pres);  
                    
                }
            }
            for (String idDm : ListDmcli){
                query="SELECT id_observation FROM observation_iddmcli WHERE id_DMcli='"+idDm+"'";                  
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_observation");
                    ListIdObs.add(Id_Pres);                      
                }
            }
            
            for (String idDm : ListDmcli){
                query="SELECT id_observation FROM observation_iddmmt WHERE id_DMmt='"+idDm+"'";                  
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_observation");
                    ListIdObs.add(Id_Pres);                      
                }
            }
            
            for (String Id_obs : ListIdObs){
                
                query="SELECT * FROM observation WHERE id_observation='"+Id_obs+"'";                 
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){                    
                    
                    Obersvation obs = new Obersvation(Id_obs,rst.getString("obs"),rst.getString("date"));
                    listObs.add(obs);                 
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return listObs;
    }
    
    
    
    
    
    
    /**
     * @return the id_obs
     */
    public String getId_obs() {
        return id_obs;
    }

    /**
     * @param id_obs the id_obs to set
     */
    public void setId_obs(String id_obs) {
        this.id_obs = id_obs;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
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
}
