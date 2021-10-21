package DAO;

import javafx.collections.ObservableList;
import model.Country;

import java.sql.SQLException;

public interface CountryDAO {
    ObservableList<String> getCountries() throws SQLException, ClassNotFoundException;
    int getCountryID(String countryName) throws SQLException, ClassNotFoundException;
}
