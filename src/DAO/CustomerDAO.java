package DAO;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * This interface performs CRUD operations on Customers table
 */
public interface CustomerDAO {

    /**
     * This method retrieves a list of customers
     * @return Returns an ObservableList of customers
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     * @throws ParseException Parse error
     */
    ObservableList<Customer> getCustomers() throws SQLException, ClassNotFoundException, ParseException;

    /**
     * This method updates existing customers in the database
     * @param customerID Customer ID
     * @param customerName Customer Name
     * @param customerAddress Customer Address
     * @param customerPostalCode Customer Postal Code
     * @param customerPhone Customer Phone Number
     * @param divisionID First-Level Division ID
     * @return Returns true if update is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    boolean updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;

    /**
     * This method deletes a customer from the database
     * @param customerID Customer ID
     * @return Returns true if delete is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    boolean deleteCustomer(int customerID) throws SQLException, ClassNotFoundException;

    /**
     * This method adds a new customer to the database
     * @param customerName Customer Name
     * @param customerAddress Customer Address
     * @param customerPostalCode Customer postal Code
     * @param customerPhone Customer Phone Number
     * @param divisionID First-Level Division ID
     * @return Returns true if add is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    boolean addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;

    /**
     * This method retrieves a list of customer Names
     * @return Returns an ObservableList of strings with customer Name
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    ObservableList<String> getCustomerNames() throws SQLException, ClassNotFoundException;

    /**
     * This method retrieves the Customer ID for a customer
     * @param name Customer Name
     * @return Returns the ID for a customer
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    int getCustomerIDByName(String name) throws SQLException, ClassNotFoundException;
}
