package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAOImpl implements CountryDAO{

    /**
     * This method retrieves a list of countries from the database
     * @return Returns an ObservableList of countries
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public ObservableList<String> getCountries() throws SQLException, ClassNotFoundException {
        ObservableList<String> countryObservableList = FXCollections.observableArrayList();
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Country FROM countries");
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            countryObservableList.add(resultSet.getString("Country"));
        }
        JDBC.closeConnection();
        return countryObservableList;
    }
}
