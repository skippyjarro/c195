package controller;

import DAO.AppointmentDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AppointmentManagerController implements Initializable {
    public Button manageCustomersButton;
    AppointmentDAOImpl appointmentDAO;

    @FXML
    public ToggleGroup weekMonthViewRadio;

    @FXML
    public Label currentUserLabel;

    @FXML
    public RadioButton weekViewRadio;

    @FXML
    public RadioButton monthViewRadio;

    @FXML
    public TableColumn apptIdCol;

    @FXML
    public TableColumn apptTitleCol;

    @FXML
    public TableColumn apptDescriptionCol;

    @FXML
    public TableColumn apptLocationCol;

    @FXML
    public TableColumn contactCol;

    @FXML
    public TableColumn typeCol;

    @FXML
    public TableColumn startDateTimeCol;

    @FXML
    public TableColumn endDateTimeCol;

    @FXML
    public TableColumn CustomerIdCol;

    @FXML
    public TableColumn userIdCol;

    @FXML
    public Button addApptButton;

    @FXML
    public Button deleteApptButton;

    @FXML
    public Button exitButton;

    @FXML
    public TableView apptTableview;

    @FXML
    public RadioButton allApptRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDAO = new AppointmentDAOImpl();
        currentUserLabel.setText("Current User: " + User.getCurrentUserName());

        //Populate appointment table
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        try {
            apptTableview.setItems(appointmentDAO.getAppointments("All"));
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
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

    public void getAppointments(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        apptTableview.setItems(appointmentDAO.getAppointments(actionEvent.toString()));
    }

    public void toManageCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1366, 768);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }
}
