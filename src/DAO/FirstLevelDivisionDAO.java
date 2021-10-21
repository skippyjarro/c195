package DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FirstLevelDivisionDAO {
    ObservableList<String> getFirstLevelDiv(String countryName) throws SQLException, ClassNotFoundException;
    int getFirstLevelDivID(String firstLevelDivName) throws SQLException, ClassNotFoundException;
}
