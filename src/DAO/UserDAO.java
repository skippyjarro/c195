package DAO;

import javafx.collections.ObservableList;
import model.User;

import java.sql.SQLException;

/**
 * This interface retrieves User data from database
 */
public interface UserDAO {

    /**
     * This method retrieves a list of users from the Database
     * @return Returns an ObservableList of Users
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    ObservableList<User> getUserList() throws SQLException, ClassNotFoundException;

    /**
     * This method retrieves the user ID by name
     * @param userName User Name
     * @return Returns the user ID
     * @throws SQLException SQL error
     */
    int getUserIDByName(String userName) throws SQLException;
}
