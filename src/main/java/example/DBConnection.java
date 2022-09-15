package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    protected static Connection initializeDB() throws SQLException, ClassNotFoundException {
        String hostAddress =  "";
        String dbName = "";
        String dbDriver = "org.postgresql.Driver";
        String username = "";
        String password = "";

        Class.forName(dbDriver);
        Connection conn = DriverManager.getConnection("jdbc:postgresql://" + hostAddress + dbName, username, password);

        return conn;
        }
    }
