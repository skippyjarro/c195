package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button exitButton;

    @FXML
    public TextField userNameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        boolean result = JDBC.userLogin(userName, password);
        if (result == false) {
            System.out.println("Login Failed");
        } else if (result == true) {
            toAppointmentManager(actionEvent);
        }


    }

    /**
     * This method navigates the user back to the Inventory Manager screen without saving any changes.
     *
     * @param actionEvent Button click event
     * @throws IOException Incorrect input
     */
    public void toAppointmentManager(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentManager.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 800, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method is called when the user clicks the Exit button. It closes the program.
     *
     * @param actionEvent Exit button click event
     */
    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}

