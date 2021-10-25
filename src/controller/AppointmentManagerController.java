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
import model.User;
import utilities.DateTimeUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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
            if (upcomingAppt != null) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Upcoming Appointments");
                successAlert.setHeaderText("You have an upcoming appointments in the next 15 minutes.\n" + upcomingAppt.getAppointmentID() + " - " + DateTimeUtilities.getFormattedDateTime(upcomingAppt.getStartDateTime()));
                successAlert.showAndWait();
            } else {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Upcoming Appointments");
                successAlert.setHeaderText("You have no upcoming appointments in the next 15 minutes.");
                successAlert.showAndWait();
            }
            LoginController.firstLogin = false;
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

    public void cancelAppt(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        AppointmentDAOImpl.selectedAppointment = (Appointment) apptTableview.getSelectionModel().getSelectedItem();
        if (actionEvent.getSource().toString().contains("Edit Apopintment") && AppointmentDAOImpl.selectedAppointment == null) {
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
            successAlert.setHeaderText("Appointment Canceled Successfully");
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

    private Appointment checkUpcomingAppointments() {
        Appointment upcomingAppt = null;
        for (Appointment appt:AppointmentDAOImpl.appointmentObservableList) {
            if (User.getCurrentUserId() == appt.getUserID() && LocalDateTime.now().plusMinutes(15).isAfter(appt.getStartDateTime()) && appt.getStartDateTime().isAfter(LocalDateTime.now())) {
                upcomingAppt = appt;
            }
        }
        return upcomingAppt;
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1366, 768);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }
}
