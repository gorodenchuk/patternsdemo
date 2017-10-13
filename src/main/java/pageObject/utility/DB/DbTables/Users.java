package pageObject.utility.DB.DbTables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DataBaseExecute;
import pageObject.utility.DateTime;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class Users extends DataBaseExecute{

private static final Logger LOG = LogManager.getLogger(Users.class);


    public static void deleteUser (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void deleteUser (Map<String,String> testdata) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "DELETE FROM `users` WHERE `email` = '" + testdata.get("Email") +  "'";
        dbe.conect(query);
        dbe.close();
    }

    public static String selectDiscount (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users`  WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("discount");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static String selectPassword (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("password");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static String selectPhone(String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("phone");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static String selectPhone_Code (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("phone_code");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static String selectIdentification (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("identification");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static String selectRecommendation (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "SELECT * FROM `users` WHERE `email` = 'mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.findColumn("recommendation");
        dbe.close();
        return DataBaseExecute.result;
    }

    public static void updatePhone_VerifyToApprove (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `phone_verify`= 1 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updatePhone (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `phone`= '(806) 945-1111' WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_PersonalToApprove (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_personal`= 1 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_MedicalToApprove (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_medical`= 1 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_PersonalToPending (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_personal`= 2 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_MedicalToPending (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_medical`= 2 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_PersonalToDeclined (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_personal`= 0 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateVerify_MedicalToDeclined (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `verify_medical`= 0 WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateRec_ExpiredToNotValidDate(String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `rec_expired`= '2016-08-31 00:00:00' WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateRec_ExpiredToValidDate (String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        DateTime dateTime = new DateTime();

        String query = "UPDATE `users` SET `rec_expired`= '" + dateTime.generateDateForValidMedical() + "' WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateDiscount (int discount, String generNumber1) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `discount`= " + discount +" WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void updateRefInvited(String generNumber1, String generNumber2) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "UPDATE `users` SET `ref_invited`= '3P83Z1" + generNumber2 + "' WHERE `email`='mmjtrain.test" + generNumber1 + "@gmail.com'";
        dbe.conect(query);
        dbe.close();
    }

    public static void insertUser (String generNumber2) throws SQLException {
        DataBaseExecute dbe = new DataBaseExecute();
        String query = "INSERT INTO `users` (id, email, password, zipcode, session, created_date, modify_date, firstname," +
                " lastname, phone, phone_verify, phone_code, ref, discount, location, location_full, location_zip, location_state, " +
                "longitude, latitude, identification, recommendation, verify_personal, verify_medical, rec_expired, ref_invited," +
                " count_orders, price_orders, forgot_pass) VALUES ('" + generNumber2 + generNumber2 + "', 'mmjtrain.test" + generNumber2 + "@gmail.com'," +
                " '$2a$10$uTm85rJ1R/KK8P7mOHeamuqDhMw2FMb7ycQIte43pRydxcKcC" + generNumber2 +"', '', " +
                "'I7ZLCS8ZQZ7O0C7WWZN3YAOXKS0LRXMA5MVHE4H43OT4000XZN0YXOVHHV39" + generNumber2 +"', '2017-09-13 04:10:16'," +
                " '2017-09-13 04:10:16', NULL, NULL, '(818) 797-" + generNumber2 + "', '1', 'NULL', '3P83Z1" + generNumber2 + "', '0', " +
                "'Amissville', 'Amissville, RAPPAHANNOCK, USA', '22002', 'VA', '-78.137016', '38.691448', " +
                "'files/identification/201709/c15da1f2b5e5ed6e6837a3802f0d1593_1505300517_31280.png', " +
                "'files/identification/201709/c15da1f2b5e5ed6e6837a3802f0d1593_1505300517_31280.png', '1', '1', NULL, NULL, '0', '0', '')";
        dbe.conect(query);
        dbe.close();
    }


}

