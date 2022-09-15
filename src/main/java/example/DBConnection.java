package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    protected static Connection initializeDB() throws SQLException, ClassNotFoundException {
        String hostAddress =  System.getenv("HOSTADDRESS");
        String dbName = System.getenv("DBNAME");
        String dbDriver = "org.postgresql.Driver";
        String username = System.getenv("PSUSERNAME");
        String password = System.getenv("PSPASSWORD");

        Class.forName(dbDriver);
        Connection conn = DriverManager.getConnection("jdbc:postgresql://" + hostAddress + dbName, username, password);

        return conn;
        }
    }
