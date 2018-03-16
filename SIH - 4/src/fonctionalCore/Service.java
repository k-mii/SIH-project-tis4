/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Clément
 */
public class Service {
    private int id_service;
    private String nom_service;

    public Service(int id_service,String nom_service){
        this.id_service=id_service;
        this.nom_service=nom_service;
    }
    
    public Service(){
        
    }


    public int getId_service() { return id_service;}
    public void setId_service(int id_service) {this.id_service = id_service;}

    public String getNom_service() {return nom_service;}
    public void setNom_service(String nom_service) {this.nom_service = nom_service;}
    
    
    public static ArrayList AfficherServices(){
        ArrayList <Service> listService = new ArrayList();
        String query="";
        query ="SELECT * FROM service";

        try{
            cnx=DB_Link.connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);

            while(rst.next()){
                Service s = new Service(rst.getInt("id_service"),rst.getString("nom"));       
                listService.add(s);
            }   
        }catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return listService;
    
    }
    
    
    
}
