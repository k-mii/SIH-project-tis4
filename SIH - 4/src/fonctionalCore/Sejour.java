package fonctionalCore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class Sejour {

  public boolean hospitalisation;

  public SimpleDateFormat dateEntree;

  public SimpleDateFormat dateSortie;

  public String lettreSortie;

public ArrayList<Prestation> lPrestation;
    public PH phRef;

    public Sejour(boolean hospitalisation, SimpleDateFormat dateEntree,  PH phRef) {
        this.hospitalisation = hospitalisation;
        this.dateEntree = dateEntree;
        this.phRef = phRef;
        lPrestation = new ArrayList<Prestation>();
    }
   public Sejour() {
        lPrestation = new ArrayList<Prestation>();
    } 
  
}