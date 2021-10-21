package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
    private static ResultSet resultSet;

    public static ResultSet sqlQuery(PreparedStatement ps) throws SQLException {
        try {
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultSet;
    }

    public static boolean sqlUpdate(PreparedStatement ps) throws SQLException {
        try {
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
