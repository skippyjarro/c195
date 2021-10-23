package DAO;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.SQLException;
import java.text.ParseException;

public interface CustomerDAO {
    ObservableList<Customer> getCustomers() throws SQLException, ClassNotFoundException, ParseException;

    boolean updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(int customerID) throws SQLException, ClassNotFoundException;

    boolean addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;

    ObservableList<String> getCustomerNames() throws SQLException, ClassNotFoundException;

    int getCustomerIDByName(String name) throws SQLException, ClassNotFoundException;
}
