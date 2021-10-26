package DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * This interface retrieves First-Level Division data from the database
 */
public interface FirstLevelDivisionDAO {

    /**
     * This method retrieves a list of First-Level Divisions for a country
     * @param countryName Country Name
     * @return Returns an ObservableList of First-Level Divisions
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    ObservableList<String> getFirstLevelDiv(String countryName) throws SQLException, ClassNotFoundException;

    /**
     * This method retrieves the First-Level Division ID for a First-Level Division
     * @param firstLevelDivName First-Level Division Name
     * @return Returns First-Level Division ID
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    int getFirstLevelDivID(String firstLevelDivName) throws SQLException, ClassNotFoundException;
}
