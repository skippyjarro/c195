package controller;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import utilities.DateTimeUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddEditAppointmentController implements Initializable {
    private AppointmentDAOImpl appointmentDAO;
    private ContactDAOImpl contactDAO;
    private CustomerDAOImpl customerDAO;
    private UserDAOImpl userDAO;
    private ObservableList<String> hoursList;
    private ObservableList<String> minutesList;
    private ObservableList<String> amPMList;
    private String[] hoursArray;
    private String[] minutesArray;
    private String[] amPMArray;
    private int apptID;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private String contactName;
    private String apptType;
    private String startDate;
    private String startHour;
    private String startMinute;
    private String startAMPM;
    private String endDate;
    private String endHour;
    private String endMinute;
    private String endAMPM;
    private String customerName;
    private String userName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @FXML
    private TextField apptIDField;

    @FXML
    private TextField apptTitleField;

    @FXML
    private TextField apptDescriptionField;

    @FXML
    private TextField apptLocationField;

    @FXML
    private ComboBox contactBox;

    @FXML
    private TextField apptTypeField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox startHourBox;

    @FXML
    private ComboBox startMinuteBox;

    @FXML
    private ComboBox startAMPMBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox endHourBox;

    @FXML
    private ComboBox endMinuteBox;

    @FXML
    private ComboBox endAMPMBox;

    @FXML
    private ComboBox customerBox;

    @FXML
    private ComboBox userBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDAO = new AppointmentDAOImpl();
        contactDAO = new ContactDAOImpl();
        customerDAO = new CustomerDAOImpl();
        userDAO = new UserDAOImpl();
        hoursArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        minutesArray = new String[]{"00", "15", "30", "45"};
        amPMArray = new String[]{"AM", "PM"};
        hoursList = FXCollections.observableArrayList(hoursArray);
        minutesList = FXCollections.observableArrayList(minutesArray);
        amPMList = FXCollections.observableArrayList(amPMArray);

        //Populate Contact and Customer boxes
        try {
            contactBox.setItems(contactDAO.getContacts());
            customerBox.setItems(customerDAO.getCustomerNames());
            userBox.setItems(userDAO.getUserList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Populate Hours, Minutes, and AM/PM boxes
        startHourBox.setItems(hoursList);
        startMinuteBox.setItems(minutesList);
        startAMPMBox.setItems(amPMList);
        endHourBox.setItems(hoursList);
        endMinuteBox.setItems(minutesList);
        endAMPMBox.setItems(amPMList);

        //Populate fields if modifying appointment
        if (AppointmentDAOImpl.selectedAppointment != null) {
            startDateTime = AppointmentDAOImpl.selectedAppointment.getStartDateTime();
            endDateTime = AppointmentDAOImpl.selectedAppointment.getEndDateTime();
            apptIDField.setText(String.valueOf(AppointmentDAOImpl.selectedAppointment.getAppointmentID()));
            apptTitleField.setText(AppointmentDAOImpl.selectedAppointment.getTitle());
            apptDescriptionField.setText(AppointmentDAOImpl.selectedAppointment.getDescription());
            apptLocationField.setText(AppointmentDAOImpl.selectedAppointment.getLocation());
            try {
                contactBox.getSelectionModel().select(contactDAO.getContactNameByID(AppointmentDAOImpl.selectedAppointment.getContactID()));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            apptTypeField.setText(AppointmentDAOImpl.selectedAppointment.getType());
            startDatePicker.setValue(startDateTime.toLocalDate());
            if (startDateTime.getHour() >= 12) {
                if (startDateTime.getHour() > 12) {
                    startHourBox.getSelectionModel().select(String.valueOf(startDateTime.minusHours(12).getHour()));
                } else {
                    startHourBox.getSelectionModel().select(String.valueOf(startDateTime.getHour()));
                }
                startAMPMBox.getSelectionModel().select("PM");
            } else {
                startHourBox.getSelectionModel().select(String.valueOf(startDateTime.getHour()));
                startAMPMBox.getSelectionModel().select("AM");
            }
            if (startDateTime.getMinute() < 10) {
                startMinuteBox.getSelectionModel().select("0" + startDateTime.getMinute());
            } else {
                startMinuteBox.getSelectionModel().select(String.valueOf(startDateTime.getMinute()));
            }
            endDatePicker.setValue(endDateTime.toLocalDate());
            if (endDateTime.getHour() >= 12) {
                if (endDateTime.getHour() > 12) {
                    endHourBox.getSelectionModel().select(String.valueOf(endDateTime.minusHours(12).getHour()));
                } else {
                    endHourBox.getSelectionModel().select(String.valueOf(endDateTime.getHour()));
                }
                endAMPMBox.getSelectionModel().select("PM");
            } else {
                endHourBox.getSelectionModel().select(String.valueOf(endDateTime.getHour()));
                endAMPMBox.getSelectionModel().select("AM");
            }
            if (endDateTime.getMinute() < 10) {
                endMinuteBox.getSelectionModel().select("0" + endDateTime.getMinute());
            } else {
                endMinuteBox.getSelectionModel().select(String.valueOf(endDateTime.getMinute()));
            }
            customerBox.getSelectionModel().select(AppointmentDAOImpl.selectedAppointment.getCustomerName());
            userBox.getSelectionModel().select(AppointmentDAOImpl.selectedAppointment.getUserName());
        }
    }

    public void saveAppointment(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        boolean result;
        int apptID;
        apptTitle = apptTitleField.getText();
        apptDesc = apptDescriptionField.getText();
        apptLocation = apptLocationField.getText();
        apptType = apptTypeField.getText();
        startDate = startDatePicker.getValue().toString();
        startHour = startHourBox.getSelectionModel().getSelectedItem().toString();
        startMinute = startMinuteBox.getSelectionModel().getSelectedItem().toString();
        startAMPM = startAMPMBox.getSelectionModel().getSelectedItem().toString();
        endDate = endDatePicker.getValue().toString();
        endHour = endHourBox.getSelectionModel().getSelectedItem().toString();
        endMinute = endMinuteBox.getSelectionModel().getSelectedItem().toString();
        endAMPM = endAMPMBox.getSelectionModel().getSelectedItem().toString();
        customerName = customerBox.getSelectionModel().getSelectedItem().toString();
        contactName = contactBox.getSelectionModel().getSelectedItem().toString();
        userName = userBox.getSelectionModel().getSelectedItem().toString();

        if (apptIDField.getText().isEmpty()) {
            apptID = -1;
        } else {
            apptID = AppointmentDAOImpl.selectedAppointment.getAppointmentID();
        }

        if (verifyAgainstBusinessHours(startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM)) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("Appointment times are outside the business hours of 8AM-10PM Eastern Time");
            failureAlert.showAndWait();
            return;
        }

        if (checkOverlappingAppts(customerDAO.getCustomerIDByName(customerName), apptID, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM)) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("Appointment times overlap with another appointment for " + customerName);
            failureAlert.showAndWait();
            return;
        }

        try {
            apptID = Integer.parseInt(apptIDField.getText());
            result = appointmentDAO.updateAppointment(apptID, apptTitle, apptDesc, apptLocation, apptType, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM, customerName, userName, contactName);
        } catch (Exception e) {
            result = appointmentDAO.addAppointment(apptTitle, apptDesc, apptLocation, apptType, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM, customerName, contactName, userName);
        }
        if (result) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Appointment Saved Successfully");
            successAlert.showAndWait();
            toAppointmentManager(actionEvent);
        } else {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("There was a problem saving the appointment");
            failureAlert.showAndWait();
            return;
        }
        AppointmentDAOImpl.selectedAppointment = null;
    }

    /**
     * This method navigates the user to the Appointment Manager screen.
     *
     * @param actionEvent Button click event
     * @throws IOException Incorrect input
     */
    public void toAppointmentManager(javafx.event.ActionEvent actionEvent) throws IOException {
        AppointmentDAOImpl.selectedAppointment = null;
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentManager.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1366, 768);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    private boolean verifyAgainstBusinessHours(String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM) {
        LocalTime businessStart = LocalTime.of(8, 00);
        LocalTime businessEnd = LocalTime.of(22, 00);
        LocalTime apptStartTime = DateTimeUtilities.convertToEasternTime(startDate, startHour, startMinute, startAMPM);
        LocalTime apptEndTime = DateTimeUtilities.convertToEasternTime(endDate, endHour, endMinute, endAMPM);
        if (apptStartTime.isBefore(businessStart) || apptStartTime.isAfter(businessEnd) || apptEndTime.isBefore(businessStart) || apptEndTime.isAfter(businessEnd)) {
            return true;
        }
        return false;
    }

    private boolean checkOverlappingAppts(int custID, int newApptID, String newStartDate, String newStartHour, String newStartMinute, String newStartAMPM, String newEndDate, String newEndHour, String newEndMinute, String newEndAMPM) {
        LocalDateTime startDateTime = DateTimeUtilities.convertInputToLocalDateTime(newStartDate, newStartHour, newStartMinute, newStartAMPM);
        LocalDateTime endDateTime = DateTimeUtilities.convertInputToLocalDateTime(newEndDate, newEndHour, newEndMinute, newEndAMPM);
        LocalDate startLocalDate = startDateTime.toLocalDate();
        LocalDate endLocalDate = endDateTime.toLocalDate();
        LocalTime startLocalTime = startDateTime.toLocalTime();
        LocalTime endLocalTime = endDateTime.toLocalTime();
        for (Appointment appt : AppointmentDAOImpl.appointmentObservableList) {
            int apptID = appt.getAppointmentID();
            if (newApptID == apptID) {
                continue;
            }
            LocalDateTime apptStartDateTime = appt.getStartDateTime();
            LocalDateTime apptEndDateTime = appt.getEndDateTime();
            LocalDate apptStartDate = apptStartDateTime.toLocalDate();
            LocalDate apptEndDate = apptEndDateTime.toLocalDate();
            LocalTime apptStartTime = apptStartDateTime.toLocalTime();
            LocalTime apptEndTime = apptEndDateTime.toLocalTime();
            if ((appt.getCustomerID() == custID) && (apptStartDate.isEqual(startLocalDate) || apptEndDate.isEqual(endLocalDate)) && (startLocalTime.equals(apptStartTime) || endLocalTime.equals(apptEndTime) || (startLocalTime.equals(apptStartTime) || startLocalTime.isBefore(apptStartTime)) && (endLocalTime.equals(apptEndTime) || endLocalTime.isAfter(apptEndTime))) || (startLocalTime.isAfter(apptStartTime) && startLocalTime.isBefore(apptEndTime)) || (endLocalTime.isAfter(apptStartTime) && endLocalTime.isBefore(apptEndTime))) {
                return true;
            }
        }
        return false;
    }
}
