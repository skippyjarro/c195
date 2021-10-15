package helper;

import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//skipfam.com:3306/";
    private static final String databaseName = "apptmgr";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "Remote";
    private static final String password = "Lianagirl1!@";
    public static Connection conn;

    public static Connection makeConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Successful");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static void closeConnection() throws ClassNotFoundException, SQLException {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static boolean userLogin(String userName, String password) throws SQLException, ClassNotFoundException {
        String dbPassword = null;
        Connection connection = JDBC.makeConnection();
        try (Statement stmt = connection.createStatement()) {
            String loginQuery = "SELECT Password FROM users WHERE User_Name = '" + userName + "';";
            ResultSet rs = stmt.executeQuery(loginQuery);
            while (rs.next()) {
                dbPassword = rs.getString("Password");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            JDBC.closeConnection();
        }
        if (password.equals(dbPassword)) {
            return true;
        } else {
            return false;
        }
    }

}
