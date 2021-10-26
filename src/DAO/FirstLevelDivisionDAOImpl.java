package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the FirstLevelDivisionDAO interface
 */
public class FirstLevelDivisionDAOImpl implements FirstLevelDivisionDAO{

    /**
     * This method retrieves a list of First-Level Divisions for a country
     * @param countryName Country Name
     * @return Returns an ObservableList of First-Level Divisions
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public ObservableList<String> getFirstLevelDiv(String countryName) throws SQLException, ClassNotFoundException {
        ObservableList<String> firstLevelDivList = FXCollections.observableArrayList();
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Division FROM first_level_divisions JOIN countries c on c.Country_ID = first_level_divisions.Country_ID WHERE Country = ?");
        statement.setString(1, countryName);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            firstLevelDivList.add(resultSet.getString("Division"));
        }
        JDBC.closeConnection();
        return firstLevelDivList;
    }

    /**
     * This method retrieves the First-Level Division ID for a First-Level Division
     * @param firstLevelDivName First-Level Division Name
     * @return Returns First-Level Division ID
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public int getFirstLevelDivID(String firstLevelDivName) throws SQLException, ClassNotFoundException {
        int firstLevelDivID = 0;
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Division_ID FROM first_level_divisions WHERE Division = ?");
        statement.setString(1, firstLevelDivName);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            firstLevelDivID = resultSet.getInt("Division_ID");
        }
        JDBC.closeConnection();
        return firstLevelDivID;
    }
}
