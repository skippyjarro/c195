package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the UserDAO interface
 */
public class UserDAOImpl implements UserDAO{

    /**
     * This method retrieves a list of users from the Database
     * @return Returns an ObservableList of Users
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public ObservableList<User> getUserList() throws SQLException, ClassNotFoundException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        int userID;
        String userName;
        PreparedStatement preparedStatement = JDBC.openConnection().prepareStatement("SELECT User_ID, User_Name FROM users");
        ResultSet resultSet = Query.sqlQuery(preparedStatement);
        while (resultSet.next()) {
            userID = resultSet.getInt("User_ID");
            userName = resultSet.getString("User_Name");
            userList.add(new User(userID, userName));
        }
        JDBC.closeConnection();
        return userList;
    }

    /**
     * This method retrieves the user ID by name
     * @param userName User Name
     * @return Returns the user ID
     * @throws SQLException SQL error
     */
    @Override
    public int getUserIDByName(String userName) throws SQLException {
        int userID = 0;
        PreparedStatement statement = JDBC.openConnection().prepareStatement("SELECT User_ID FROM users WHERE User_Name = ?");
        statement.setString(1, userName);
        ResultSet resultSet = Query.sqlQuery(statement);
        while (resultSet.next()){
            userID = resultSet.getInt("User_ID");
        }
        return userID;
    }
}
