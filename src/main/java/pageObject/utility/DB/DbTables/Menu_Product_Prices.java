package pageObject.utility.DB.DbTables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DataBaseExecute;

import java.sql.SQLException;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class Menu_Product_Prices extends DataBaseExecute{

private static final Logger LOG = LogManager.getLogger(Menu_Product_Prices.class);


    public static void insertMenuProductPriceForOunceOZ (String generNumber1, int price) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_product_prices`(id, product_id, six, eight, quarter, half_ounce, ounce, portions, portion_price, g2, g1_5, g1, g0_5, `each`) VALUES ('" + generNumber1 + generNumber1 + "', '" + generNumber1 + "', '0', '0', '0', '0', '"+ price +"', '0', '0', '0', '0', '0', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertMenuProductPriceForHalfOunceAndOunceOZ (String generNumber1, int ouncePrice, int halfOuncePrice) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_product_prices`(id, product_id, six, eight, quarter, half_ounce, ounce, portions, portion_price, g2, g1_5, g1, g0_5, `each`) VALUES ('" + generNumber1 + generNumber1 + "', '" + generNumber1 + "', '0', '0', '0', '" + halfOuncePrice + "', '"+ ouncePrice +"', '0', '0', '0', '0', '0', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertMenuProductPriceForEach (String generNumber1, int price) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_product_prices`(id, product_id, six, eight, quarter, half_ounce, ounce, portions, portion_price, g2, g1_5, g1, g0_5, `each`) VALUES ('" + generNumber1 + generNumber1 + "', '" + generNumber1 + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '"+ price +"')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertMenuProductPriceForHalfG (String generNumber1, int price) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_product_prices`(id, product_id, six, eight, quarter, half_ounce, ounce, portions, portion_price, g2, g1_5, g1, g0_5, `each`) VALUES ('" + generNumber1 + generNumber1 + "', '" + generNumber1 + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '"+ price +"', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void deleteMenuProductById (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `menu_product_prices` WHERE `id` = '" + generNumber1 + generNumber1 + "'";
        dbe.conect(query);
        dbe.close();
    }


}

