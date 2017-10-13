package pageObject.utility.DB.DbTables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DataBaseExecute;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class Orders extends DataBaseExecute{

private static final Logger LOG = LogManager.getLogger(Orders.class);


    public static void deleteOrder (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `orders` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

}

