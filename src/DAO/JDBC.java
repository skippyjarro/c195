package DAO;

import java.sql.*;

/**
 * This class performs database connection
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * This method opens the connection to the database
     * @return Returns connection
     */
    public static Connection openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Successful");
            return connection;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
