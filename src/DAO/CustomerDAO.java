package DAO;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.SQLException;
import java.text.ParseException;

public interface CustomerDAO {
    public ObservableList<Customer> getCustomers() throws SQLException, ClassNotFoundException, ParseException;
    public boolean updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(int customerID) throws SQLException, ClassNotFoundException;
    public boolean addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException, ClassNotFoundException;
}
