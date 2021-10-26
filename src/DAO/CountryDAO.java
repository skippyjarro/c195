package DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * This interface retrieves a list of countries from the database
 */
public interface CountryDAO {

    /**
     * This method retrieves a list of countries from the database
     * @return Returns an ObservableList of countries
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    ObservableList<String> getCountries() throws SQLException, ClassNotFoundException;
}
