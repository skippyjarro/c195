package DAO;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.SQLException;

public interface ContactDAO {
    ObservableList<Contact> getContacts() throws SQLException, ClassNotFoundException;
    int getContactIDByName(String name) throws SQLException;
    String getContactNameByID(int ID) throws SQLException, ClassNotFoundException;
}
