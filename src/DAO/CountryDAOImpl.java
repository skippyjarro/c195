package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAOImpl implements CountryDAO{
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

    @Override
    public int getCountryID(String countryName) throws SQLException, ClassNotFoundException {
        int countryID = 0;
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Country_ID FROM countries WHERE Country = ?");
        statement.setString(1, countryName);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            countryID = resultSet.getInt("Country_ID");
        }
        JDBC.closeConnection();
        return countryID;
    }
}
