package fonctionalCore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class Sejour {

  public boolean hospitalisation;
  
  public int IPP;

  public Date dateEntree;

  public Date dateSortie;

  public String lettreSortie;

public ArrayList<Prestation> lPrestation;
    public PH phRef;
    public String motifA;

    public Sejour(boolean hospitalisation, Date dateEntree,  PH phRef) {
        this.hospitalisation = hospitalisation;
        this.dateEntree = dateEntree;
        this.phRef = phRef;
        lPrestation = new ArrayList<Prestation>();
    }
   public Sejour() {
        lPrestation = new ArrayList<Prestation>();
    } 
  public Sejour(boolean hospitalisation,Date dateEntree,Date dateSortie, PH phRef){
      
  }
}