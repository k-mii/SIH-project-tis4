package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 */
public class DM_mt {

  public String observation;

    /**
   * 
   * @element-type DM
   */
    public ArrayList<Prestation>  lPrestation;

    public DM_mt() {
        lPrestation = new ArrayList<Prestation>();
    }
 
}