package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class executes SQL queries and updates
 */
public class Query {
    private static ResultSet resultSet;

    /**
     * This method executes SQL queries
     * @param ps PreparedStatement for query
     * @return Returns resultset from query
     */
    public static ResultSet sqlQuery(PreparedStatement ps) {
        try {
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultSet;
    }

    /**
     * This method executes SQL updates
     * @param ps PreparedStatement for update or delete
     * @return Returns true if update or delete is successful
     */
    public static boolean sqlUpdate(PreparedStatement ps) {
        try {
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
