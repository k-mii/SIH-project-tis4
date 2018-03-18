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
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author camille
 */
public class Acte_Infirmier {
    private String id_acte;
    private String code_acte;
    private String acte;
    private String resultat;
    private String id_sejour;
    private String infirmier;
    private String date;
    
    public Acte_Infirmier(){

    }
    
    public Acte_Infirmier(String id_acte, String code_acte, String acte, String resultat, String id_sejour,String infirmier,String date){
        this.id_acte=id_acte;
        this.code_acte=code_acte;
        this.acte=acte;
        this.resultat=resultat;
        this.id_sejour=id_sejour;
        this.infirmier = infirmier;
        this.date = date;
    }
    
/************************************************************
       AFFICHER LISTE DES ACTES SUR UN PATIENT D'UN PATIENT          
 ***********************************************************/     
    
    public static ArrayList afficherActe(String ipp){     
        ArrayList<Acte_Infirmier> ListActeInf = new ArrayList();
       
        try{ 
            String query="SELECT id_Sejour FROM sejour WHERE IPP='"+ipp+"' AND dateSortie IS Null";
/*TEST CONSOLE*/        System.out.println("requete recupere id sejour"+query);    
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.next();
            String idSejour = rst.getString("id_Sejour");
            
            query="SELECT * FROM acte_inf WHERE id_sejour='"+idSejour+"'";     
/*TEST CONSOLE*/        System.out.println("requete recupere acte"+query);  
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                Acte_Infirmier acte=new Acte_Infirmier(rst.getString("id_acte"), rst.getString("code_acte"), rst.getString("acte"), rst.getString("resultat"),idSejour,null,rst.getString("date"));
                String infirmier = Infirmier.AfficherNomInfirmier(rst.getString("id_Infirmier"));
                acte.setInfirmier(infirmier);
                ListActeInf.add(acte);    
                
            }
            
           

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        
        return ListActeInf;
    }

    /**
     * @return the id_acte
     */
    public String getId_acte() {
        return id_acte;
    }

    /**
     * @param id_acte the id_acte to set
     */
    public void setId_acte(String id_acte) {
        this.id_acte = id_acte;
    }

    /**
     * @return the code_acte
     */
    public String getCode_acte() {
        return code_acte;
    }

    /**
     * @param code_acte the code_acte to set
     */
    public void setCode_acte(String code_acte) {
        this.code_acte = code_acte;
    }

    /**
     * @return the acte
     */
    public String getActe() {
        return acte;
    }

    /**
     * @param acte the acte to set
     */
    public void setActe(String acte) {
        this.acte = acte;
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
     * @return the id_sejour
     */
    public String getId_sejour() {
        return id_sejour;
    }

    /**
     * @param id_sejour the id_sejour to set
     */
    public void setId_sejour(String id_sejour) {
        this.id_sejour = id_sejour;
    }

    /**
     * @return the infirmier
     */
    public String getInfirmier() {
        return infirmier;
    }

    /**
     * @param infirmier the infirmier to set
     */
    public void setInfirmier(String infirmier) {
        this.infirmier = infirmier;
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

