package pageObject.utility.DB.DbTables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DataBaseExecute;

import java.sql.SQLException;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class Menu_Products extends DataBaseExecute{

private static final Logger LOG = LogManager.getLogger(Menu_Products.class);


    public static void insertProductOz (String generNumber1, int inStock) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_products`(id, tid, strain_type, product_type, name, description, image_url, thumb_image_url, slug, published, date_create, date_modify, thc, cbd, price_per, inStock, idSupplier, reorderNotification, allowDiscounted) " +
                "VALUES('" + generNumber1 + "', '007bc586a1bf71a9cde462ce5462" + generNumber1 + "', '1', '1', 'testproduct" + generNumber1 + "', 'lorem ipsum', '-', '-', 'testproduct" + generNumber1 + "', '1', '2017-08-09 12:38:41', '2017-08-09 12:38:46', '12', '0.2', 'oz', '" + inStock + "', '1', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertProductEach (String generNumber1, int inStock) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_products`(id, tid, strain_type, product_type, name, description, image_url, thumb_image_url, slug, published, date_create, date_modify, thc, cbd, price_per, inStock, idSupplier, reorderNotification, allowDiscounted) " +
                "VALUES('" + generNumber1 + "', '007bc586a1bf71a9cde462ce5462" + generNumber1 + "', '2', '2', 'testproduct" + generNumber1 + "', 'lorem ipsum', '-', '-', 'testproduct" + generNumber1 + "', '1', '2017-08-09 12:38:41', '2017-08-09 12:38:46', '12', '0.2', 'each', '" + inStock + "', '1', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertProductEachHalf (String generNumber1, int inStock) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_products`(id, tid, strain_type, product_type, name, description, image_url, thumb_image_url, slug, published, date_create, date_modify, thc, cbd, price_per, inStock, idSupplier, reorderNotification, allowDiscounted) " +
                "VALUES('" + generNumber1 + "', '007bc586a1bf71a9cde462ce5462" + generNumber1 + "', '2', '2', 'testproduct" + generNumber1 + "', 'lorem ipsum', '-', '-', 'testproduct" + generNumber1 + "', '1', '2017-08-09 12:38:41', '2017-08-09 12:38:46', '12', '0.2', 'each', '" + inStock + "', '1', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertProductG (String generNumber1, int inStock) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `menu_products`(id, tid, strain_type, product_type, name, description, image_url, thumb_image_url, slug, published, date_create, date_modify, thc, cbd, price_per, inStock, idSupplier, reorderNotification, allowDiscounted) " +
                "VALUES('" + generNumber1 + "', '007bc586a1bf71a9cde462ce5462" + generNumber1 + "', '2', '2', 'testproduct" + generNumber1 + "', 'lorem ipsum', '-', '-', 'testproduct" + generNumber1 + "', '1', '2017-08-09 12:38:41', '2017-08-09 12:38:46', '12', '0.2', 'g', '" + inStock + "', '1', '0', '0')";
        dbe.conect(query);
        dbe.close();
    }

    public static void deleteProductById (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `menu_products` WHERE `id` = '" + generNumber1 + "'";
        dbe.conect(query);
        dbe.close();
    }

}