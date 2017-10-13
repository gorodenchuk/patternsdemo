package pageObject.utility.DB.DbTables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DataBaseExecute;

import java.sql.SQLException;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class Promocodes extends DataBaseExecute{

private static final Logger LOG = LogManager.getLogger(Promocodes.class);

    public static void insertPromocodeNotUsed (String generNumber1, int price) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `promocodes`(code, created_date, end_date, price, publish, used, modify_date) " +
                "VALUES('testpromo" + generNumber1 + "', '2017-08-03 14:58:22', NULL, '" + price +"', '1', '0', '2017-08-09 16:19:56')";
        dbe.conect(query);
        dbe.close();
    }

    public static void deletePromocode (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `promocodes` WHERE `code` = 'testpromo" + generNumber1 + "'";
        dbe.conect(query);
        dbe.close();
    }

}

