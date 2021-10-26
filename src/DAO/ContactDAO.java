package DAO;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.SQLException;

/**
 * This interface retrieves Contact information from the database
 */
public interface ContactDAO {

    /**
     * This method gets a list of contacts from the database
     * @return Returns an ObservableList of contacts
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    ObservableList<Contact> getContacts() throws SQLException, ClassNotFoundException;

    /**
     * This method retrieves a contact ID matching a contact name
     * @param name Contact Name
     * @return Returns contact's associated ID
     * @throws SQLException SQL error
     */
    int getContactIDByName(String name) throws SQLException;

    /**
     * This method retrieves a contact name matching an ID
     * @param ID Contact ID
     * @return Returns contact's name
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    String getContactNameByID(int ID) throws SQLException, ClassNotFoundException;
}
