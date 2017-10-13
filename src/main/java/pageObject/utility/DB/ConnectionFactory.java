package pageObject.utility.DB;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mariadb://88.99.251.184:3306/";
    public static final String SCHEMA_NAME = "mmjtrain2";
    public static final String USER = "mmjtrain2wqe12321";
    public static final String PASSWORD = "odaWjiv6WoDHsoP7";
    public static final String DRIVER_CLASS = "org.mariadb.jdbc.Driver";


    private static final Logger LOG = LogManager.getLogger(ConnectionFactory.class);



    private ConnectionFactory() {

        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL+SCHEMA_NAME, USER, PASSWORD);
            LOG.info("Connected to the database");
        } catch (SQLException e) {
            LOG.error("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }
}

// Use this script if will be problems with conection. Change ip to your current
//    GRANT ALL PRIVILEGES ON mmjtrain1.* TO 'NsnUsnBahN'@'217.20.186.161%' IDENTIFIED BY 'NsjabIshaBashakHSY' WITH GRANT OPTION