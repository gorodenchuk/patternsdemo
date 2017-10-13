package pageObject.utility.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObject.utility.DB.DbTables.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

public class DataBaseExecute {

    private static Connection connection;
    private static Statement statement;
    protected static String result = "";
    public static ResultSet rs = null;
    private static final Logger LOG = LogManager.getLogger(DataBaseExecute.class);


//private final static String TABLE_NAME = (" ");


    public ResultSet conect(String query) throws SQLException {
        connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
        rs = statement.executeQuery(query);

        return rs;
    }

    public String findColumn(String columnLabel) throws SQLException {
        int columnName = rs.findColumn(columnLabel);
        if (rs.first()) {
            do {
                DataBaseExecute.result += rs.getString(columnName);

            } while (rs.next());
        }
        return DataBaseExecute.result;
    }

    public void close() {
        DbUtil.close(rs);
        DbUtil.close(statement);
        DbUtil.close(connection);
    }

}




