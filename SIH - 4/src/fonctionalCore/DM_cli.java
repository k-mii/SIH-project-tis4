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
public class DM_cli {

    public ArrayList<String> lObservation;

    public ArrayList<String> lPrescription;
    public String lettreSortie;

    public ArrayList<String> lOperation;
    public ArrayList<Prestation> lPrestation;

    public DM_cli() {
        lPrestation = new ArrayList<Prestation>();
       lObservation = new ArrayList<String>();
        lOperation = new ArrayList<String>();
        lPrescription = new ArrayList<String>();
    }
    /**
     *
     * @element-type Patient
     */

	
}
