package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;
import utilities.DateTimeUtilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.util.Date;

public class CustomerDAOImpl implements CustomerDAO{
    private int customerID;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String customerPostalCode;
    private String customerDiv;
    private String customerCountry;

    @Override
    public ObservableList<Customer> getCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM customers JOIN first_level_divisions fld on fld.Division_ID = customers.Division_ID JOIN countries c on c.Country_ID = fld.Country_ID";
        PreparedStatement statement = JDBC.openConnection().prepareStatement(queryString);
        ResultSet result = Query.sqlQuery(statement);
        while (result.next()) {
            customerID = result.getInt("Customer_ID");
            customerName = result.getString("Customer_Name");
            customerAddress = result.getString("Address");
            customerPhone = result.getString("Phone");
            customerPostalCode = result.getString("Postal_Code");
            customerDiv = result.getString("Division");
            customerCountry = result.getString("Country");
            allCustomers.add(new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, customerDiv, customerCountry));
        }
        JDBC.closeConnection();
        return allCustomers;
    }

    @Override
    public boolean updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = JDBC.openConnection().prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
        statement.setString(1, customerName);
        statement.setString(2, customerAddress);
        statement.setString(3, customerPostalCode);
        statement.setString(4, customerPhone);
        statement.setString(5, DateTimeUtilities.nowToUTCDateTime());
        statement.setString(6, User.getCurrentUserName());
        statement.setInt(7, divisionID);
        statement.setInt(8, customerID);
        boolean result = Query.sqlUpdate(statement);
        JDBC.closeConnection();
        return result;
    }

    @Override
    public boolean deleteCustomer(int customerID) throws SQLException, ClassNotFoundException {
        PreparedStatement verifyApptStatement = JDBC.openConnection().prepareStatement("SELECT COUNT(*) AS ApptCount FROM appointments WHERE Customer_ID = ?");
        verifyApptStatement.setInt(1, customerID);
        ResultSet verifyResult = Query.sqlQuery(verifyApptStatement);
        while (verifyResult.next()) {
            if (verifyResult.getInt("ApptCount") > 0) {
                JDBC.closeConnection();
                return false;
            }
        }
        PreparedStatement deleteStatement = JDBC.connection.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
        deleteStatement.setInt(1, customerID);
        Query.sqlUpdate(deleteStatement);
        JDBC.closeConnection();
        return true;
    }

    @Override
    public boolean addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = JDBC.openConnection().prepareStatement("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, customerName);
        statement.setString(2, customerAddress);
        statement.setString(3, customerPostalCode);
        statement.setString(4, customerPhone);
        statement.setString(5, DateTimeUtilities.nowToUTCDateTime());
        statement.setString(6, User.getCurrentUserName());
        statement.setString(7, DateTimeUtilities.nowToUTCDateTime());
        statement.setString(8, User.getCurrentUserName());
        statement.setInt(9, divisionID);
        boolean result = Query.sqlUpdate(statement);
        JDBC.closeConnection();
        return result;
    }
}
