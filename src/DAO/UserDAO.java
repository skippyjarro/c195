package DAO;

import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.SQLException;

public interface UserDAO {
    ObservableList<User> getUserList() throws SQLException, ClassNotFoundException;
    String getUserNameByID(int userID);
    int getUserIDByName(String userName) throws SQLException;
}
