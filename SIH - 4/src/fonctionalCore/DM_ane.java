package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * 
 */
public class DM_ane {

    public ArrayList<String> lobservation;

    public ArrayList<String> lprescription;

    public String lettreSortie;

    public ArrayList<Prestation> lPrestation;

    /**
     *
     * @element-type Patient
     */
    public void DM_ane() {
        lPrestation = new ArrayList<Prestation>();
        lobservation = new ArrayList<String>();
        lprescription = new ArrayList<String>();
    }


	
}
