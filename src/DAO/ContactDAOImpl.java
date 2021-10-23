package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOImpl implements ContactDAO{
    private int contactID;
    private String contactName;
    private String contactEmail;

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
