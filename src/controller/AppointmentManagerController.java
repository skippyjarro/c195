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
import model.Appointment;
import model.UpcomingAppointmentsAlert;
import model.User;
import utilities.DateTimeUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class controls the Appointment Manager screen
 */
public class AppointmentManagerController implements Initializable {

    AppointmentDAOImpl appointmentDAO;

    @FXML
    private ToggleGroup weekMonthViewRadio;

    @FXML
    private Label currentUserLabel;

    @FXML
    private RadioButton weekViewRadio;

    @FXML
    private RadioButton monthViewRadio;

    @FXML
    private TableColumn apptIdCol;

    @FXML
    private TableColumn apptTitleCol;

    @FXML
    private TableColumn apptDescriptionCol;

    @FXML
    private TableColumn apptLocationCol;

    @FXML
    private TableColumn contactCol;

    @FXML
    private TableColumn typeCol;

    @FXML
    private TableColumn startDateTimeCol;

    @FXML
    private TableColumn endDateTimeCol;

    @FXML
    private TableColumn CustomerIdCol;

    @FXML
    private TableColumn userIdCol;

    @FXML
    private Button addApptButton;

    @FXML
    private Button editButton;

    @FXML
    private Button cancelApptButton;

    @FXML
    private Button manageCustomersButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableView apptTableview;

    @FXML
    private RadioButton allApptRadio;

    @FXML
    private Button reportsButton;

    @FXML
    private Button deleteApptButton;

    /**
     * Lambda Expression for Upcoming Appointments Alert displays an alert to the user upon login with a list of appointments in the next 15 minutes for the current user or that there are no upcoming appointments
     * @param url URL
     * @param resourceBundle Localization ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Lambda expression to display alert for upcoming appointments if any exist
        UpcomingAppointmentsAlert upcomingAppointmentsAlert = appt -> {
            if (appt != null) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Upcoming Appointments");
                successAlert.setHeaderText("You have an upcoming appointments in the next 15 minutes.\n" + appt.getAppointmentID() + " - " + DateTimeUtilities.getFormattedDateTime(appt.getStartDateTime()));
                successAlert.showAndWait();
            } else {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Upcoming Appointments");
                successAlert.setHeaderText("You have no upcoming appointments in the next 15 minutes.");
                successAlert.showAndWait();
            }
        };
        appointmentDAO = new AppointmentDAOImpl();
        currentUserLabel.setText("Current User: " + User.getCurrentUserName());

        //Populate appointment table
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDateTime"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        try {
            apptTableview.setItems(appointmentDAO.getAppointments("All"));
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        //Display pop-up with number of appointments upcoming in the next 15 minutes on initial login only
        if (LoginController.firstLogin) {
            Appointment upcomingAppt = checkUpcomingAppointments();
            upcomingAppointmentsAlert.displayUpcomingAppointments(upcomingAppt);
            LoginController.firstLogin = false;
        }
    }

    /**
     * This method is called when the user clicks the Exit button. It closes the program.
     * @param actionEvent Exit button click event
     */
    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method populates the appointment table view based on the radio button selected
     * @param actionEvent Radio button selection event
     * @throws SQLException SQL Exception
     * @throws ClassNotFoundException Class Not Found
     * @throws ParseException Parese error
     */
    public void getAppointments(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        apptTableview.setItems(appointmentDAO.getAppointments(actionEvent.toString()));
    }

    /**
     * This method navigates the user to the Manage Customers screen
     * @param actionEvent Button Click Event
     * @throws IOException Incorrect Input
     */
    public void toManageCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1366, 768);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method navigates the user to the Add/Edit appointment screen and passes the selected appointment if editing
     * @param actionEvent Button click event
     * @throws IOException Input error
     */
    public void toAddEditAppointment(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getTarget().toString().contains("Edit Appointment")) {
            AppointmentDAOImpl.selectedAppointment = (Appointment) apptTableview.getSelectionModel().getSelectedItem();
        }
        if (actionEvent.getSource().toString().contains("Edit Apopintment") && AppointmentDAOImpl.selectedAppointment == null) {
            Alert unselectedPartAlert = new Alert(Alert.AlertType.ERROR);
            unselectedPartAlert.setHeaderText("Must select an appointment");
            unselectedPartAlert.showAndWait();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddEditAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 768);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method cancels a selected appointment by taking the selected appointment and deleting it from the database
     * @param actionEvent Button click event
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found
     * @throws ParseException Parse error
     */
    public void cancelAppt(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        AppointmentDAOImpl.selectedAppointment = (Appointment) apptTableview.getSelectionModel().getSelectedItem();
        if (actionEvent.getSource().toString().contains("Cancel Apopintment") && AppointmentDAOImpl.selectedAppointment == null) {
            Alert unselectedPartAlert = new Alert(Alert.AlertType.ERROR);
            unselectedPartAlert.setHeaderText("Must select an appointment");
            unselectedPartAlert.showAndWait();
            return;
        }
        boolean result = appointmentDAO.deleteAppointment(AppointmentDAOImpl.selectedAppointment.getAppointmentID());
        //Show deletion success/failure dialog
        if (result) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Appointment ID: " + AppointmentDAOImpl.selectedAppointment.getAppointmentID() + " Type: " + AppointmentDAOImpl.selectedAppointment.getType() + " Canceled Successfully");
            successAlert.showAndWait();
            getAppointments(actionEvent);
        } else if (!result) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("There was a problem canceling appointment with ID " + AppointmentDAOImpl.selectedAppointment.getAppointmentID());
            failureAlert.showAndWait();
        }
        AppointmentDAOImpl.selectedAppointment = null;
    }

    /**
     * This method checks the list of appointments for appointments upcoming in the next 15 minutes for the user that is currently logged in
     * @return Returns the appointment upcoming in the next 15 minutes
     */
    private Appointment checkUpcomingAppointments() {
        Appointment upcomingAppt = null;
        for (Appointment appt:AppointmentDAOImpl.appointmentObservableList) {
            if (User.getCurrentUserId() == appt.getUserID() && LocalDateTime.now().plusMinutes(15).isAfter(appt.getStartDateTime()) && appt.getStartDateTime().isAfter(LocalDateTime.now())) {
                upcomingAppt = appt;
            }
        }
        return upcomingAppt;
    }

    /**
     * This method navigates the user to the Reports screen
     * @param actionEvent Button click event
     * @throws IOException Input error
     */
    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1366, 768);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
