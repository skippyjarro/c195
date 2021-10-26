package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the Contact DAO interface
 */
public class ContactDAOImpl implements ContactDAO{
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * This method gets a list of contacts from the database
     * @return Returns an ObservableList of contacts
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public ObservableList<Contact> getContacts() throws SQLException, ClassNotFoundException {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT * FROM contacts");
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            contactID = resultSet.getInt("Contact_ID");
            contactName = resultSet.getString("Contact_Name");
            contactEmail = resultSet.getString("Email");
            contactList.add(new Contact(contactID, contactName, contactEmail));
        }
        JDBC.closeConnection();
        return contactList;
    }

    /**
     * This method retrieves a contact ID matching a contact name
     * @param name Contact Name
     * @return Returns contact's associated ID
     * @throws SQLException SQL error
     */
    @Override
    public int getContactIDByName(String name) throws SQLException {
        int contactID = 0;
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        statement.setString(1, name);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            contactID = resultSet.getInt("Contact_ID");
        }
        return contactID;
    }

    /**
     * This method retrieves a contact name matching an ID
     * @param ID Contact ID
     * @return Returns contact's name
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public String getContactNameByID(int ID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");
        statement.setInt(1, ID);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()) {
            contactName = resultSet.getString("Contact_Name");
        }
        JDBC.closeConnection();
        return contactName;
    }
}
