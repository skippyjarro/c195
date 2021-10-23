package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        PreparedStatement statement = JDBC.openConnection().prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
        statement.setString(1, customerName);
        statement.setString(2, customerAddress);
        statement.setString(3, customerPostalCode);
        statement.setString(4, customerPhone);
        statement.setString(5, User.getCurrentUserName());
        statement.setInt(6, divisionID);
        statement.setInt(7, customerID);
        boolean result = Query.sqlUpdate(statement);
        JDBC.closeConnection();
        return result;
    }

    @Override
    public boolean deleteCustomer(int customerID) throws SQLException, ClassNotFoundException {
        PreparedStatement deleteStatement = JDBC.openConnection().prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
        deleteStatement.setInt(1, customerID);
        Query.sqlUpdate(deleteStatement);
        JDBC.closeConnection();
        return true;
    }

    @Override
    public boolean addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = JDBC.openConnection().prepareStatement("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)");
        statement.setString(1, customerName);
        statement.setString(2, customerAddress);
        statement.setString(3, customerPostalCode);
        statement.setString(4, customerPhone);
        statement.setString(5, User.getCurrentUserName());
        statement.setString(6, User.getCurrentUserName());
        statement.setInt(7, divisionID);
        boolean result = Query.sqlUpdate(statement);
        JDBC.closeConnection();
        return result;
    }

    @Override
    public ObservableList<String> getCustomerNames() throws SQLException, ClassNotFoundException {
        ObservableList<String> allCustomers = FXCollections.observableArrayList();
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Customer_Name FROM customers");
        ResultSet result = Query.sqlQuery(statement);
        while (result.next()) {
            customerName = result.getString("Customer_Name");
            allCustomers.add(customerName);
        }
        JDBC.closeConnection();
        return allCustomers;
    }

    @Override
    public int getCustomerIDByName(String name) throws SQLException, ClassNotFoundException {
        int custID = 0;
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Customer_ID FROM customers WHERE Customer_Name = ?");
        statement.setString(1, name);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            custID = resultSet.getInt("Customer_ID");
        }
        System.out.println(custID);
        JDBC.closeConnection();
        return custID;
    }
}
