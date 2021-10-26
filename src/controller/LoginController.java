package controller;

import DAO.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is the controller for the login prompt
 */
public class LoginController implements Initializable {
    public static boolean firstLogin;
    ResourceBundle rb;

    String filename = "src/login_activity.txt";
    FileWriter outputFile;
    PrintWriter printWriter;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    @FXML
    private Label title;

    @FXML
    private Button exitButton;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label userLocationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            outputFile = new FileWriter(filename, true);
            printWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstLogin = true;
        rb = ResourceBundle.getBundle("Localization/Bundle", Locale.getDefault());
        title.setText(rb.getString("title"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        userLocationLabel.setText("Current Location: " + ZoneId.systemDefault());
    }

    /**
     * This method checks the username and password against the database values.  It navigates the user to the Appointment Manager screen if the login is valid otherwise displays and error message if invalid.
     * @param actionEvent Login button click event
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     * @throws IOException Input error
     */
    public void login(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        int currentUserID = 0;
        String currentUserName = "";
        if (userName.isBlank() || password.isBlank()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText(rb.getString("invalidinput"));
            return;
        }
        PreparedStatement loginQuery = JDBC.openConnection().prepareStatement("SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?;");
        loginQuery.setString(1, userName);
        loginQuery.setString(2, password);
        boolean result = false;
        try {
            ResultSet rs = loginQuery.executeQuery();
            while (rs.next()) {
                if (userName.equals(rs.getString("User_Name")) && password.equals(rs.getString("Password"))) {
                    result = true;
                    currentUserID = rs.getInt("User_Id");
                    currentUserName = rs.getString("User_Name");
                } else {
                    result = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            JDBC.closeConnection();
        }
        if (result) {
            User.setCurrentUserId(currentUserID);
            User.setCurrentUserName(currentUserName);
            printWriter.println(LocalDateTime.now().format(format) + " - Successful Login by User: " + currentUserName);
            toAppointmentManager(actionEvent);
            printWriter.close();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText(rb.getString("error"));
            printWriter.println(LocalDateTime.now().format(format) + " - Unsuccessful Login Attempt by User: " + userName);
            printWriter.close();
        }


    }

    /**
     * This method navigates the user to the Appointment Manager screen.
     * @param actionEvent Button click event
     * @throws IOException Incorrect input
     */
    private void toAppointmentManager(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentManager.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1366, 768);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the user clicks the Exit button. It closes the program.
     * @param actionEvent Exit button click event
     */
    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}

