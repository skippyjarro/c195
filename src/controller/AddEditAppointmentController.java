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
import model.DisplayAlert;
import utilities.DateTimeUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This class controls the Appointment Add/Edit Screen
 */
public class AddEditAppointmentController implements Initializable {
    //Lambda expression to display error messages using messageText as argument
    DisplayAlert errorMessage = messageText -> {
        Alert failureAlert = new Alert(Alert.AlertType.ERROR);
        failureAlert.setTitle("Error");
        failureAlert.setHeaderText(messageText);
        failureAlert.showAndWait();
    };
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
    String[] apptTypesArray;
    ObservableList<String> apptTypesList;

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
    private ComboBox apptTypeBox;

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
        apptTypesArray = new String[]{"Planning Session", "De-Briefing", "Support", "Other"};
        hoursArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        minutesArray = new String[]{"00", "30"};
        amPMArray = new String[]{"AM", "PM"};
        apptTypesList = FXCollections.observableArrayList(apptTypesArray);
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

        //Populate types, Hours, Minutes, and AM/PM boxes
        startHourBox.setItems(hoursList);
        startMinuteBox.setItems(minutesList);
        startAMPMBox.setItems(amPMList);
        endHourBox.setItems(hoursList);
        endMinuteBox.setItems(minutesList);
        endAMPMBox.setItems(amPMList);
        apptTypeBox.setItems(apptTypesList);

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
            apptTypeBox.getSelectionModel().select(AppointmentDAOImpl.selectedAppointment.getType());
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

    /**
     * This method saves the appointment to the database via Insert or Update depending on if the Appt ID is existing
     * Lambda Expression to display an error message if End time is before Start time
     * Lambda Expression to display an error message if appointment times are outside business hours
     * Lambda Expression to display an error message if appointment overlaps with another appointment
     * Lambda Expression to display an error message if there was a problem saving the appointment to the database
     * @param actionEvent Button click event
     * @throws SQLException SQL Exception
     * @throws IOException IO Exception
     * @throws ClassNotFoundException Class Not Found Exception
     */
    public void saveAppointment(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        boolean result;
        int apptID;
        apptTitle = apptTitleField.getText();
        apptDesc = apptDescriptionField.getText();
        apptLocation = apptLocationField.getText();
        apptType = apptTypeBox.getSelectionModel().getSelectedItem().toString();
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

        //check to see if start date/time is after end date/time
        if (apptTimeValidation(startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM)) {
            errorMessage.displayAlert("Start date/time must be before End date/time");
            return;
        }

        //Check if apptID field is empty as proxy for checking if adding vs updating an appointment
        if (apptIDField.getText().isEmpty()) {
            apptID = -1;
        } else {
            apptID = AppointmentDAOImpl.selectedAppointment.getAppointmentID();
        }

        //Check if appointment times are outside business hours and alert if true
        if (verifyAgainstBusinessHours(startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM)) {
            errorMessage.displayAlert("Appointment times are outside the business hours of 8AM-10PM Eastern Time");
            return;
        }

        //Check if appointment times overlap with an existing appointment and alert if true
        if (checkOverlappingAppts(customerDAO.getCustomerIDByName(customerName), apptID, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM)) {
            errorMessage.displayAlert("Appointment times overlap with another appointment for " + customerName);
            return;
        }

        //Update an existing appointment if Appointment ID field contains an ID otherwise add a new Appointment
        try {
            apptID = Integer.parseInt(apptIDField.getText());
            result = appointmentDAO.updateAppointment(apptID, apptTitle, apptDesc, apptLocation, apptType, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM, customerName, userName, contactName);
        } catch (Exception e) {
            result = appointmentDAO.addAppointment(apptTitle, apptDesc, apptLocation, apptType, startDate, startHour, startMinute, startAMPM, endDate, endHour, endMinute, endAMPM, customerName, contactName, userName);
        }

        //Display result of Add or Update
        if (result) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Appointment Saved Successfully");
            successAlert.showAndWait();
            toAppointmentManager(actionEvent);
        } else {
            errorMessage.displayAlert("There was a problem saving the appointment");
            return;
        }

        //Clear the currently selected appointment
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

    /**
     * This method provides validation of appointment times to ensure they are within the business hours of 8a-10p Eastern
     * @param startDate String Start Date
     * @param startHour String Start Hour
     * @param startMinute String Start Minute
     * @param startAMPM String Start AM/PM
     * @param endDate String End Date
     * @param endHour String End Hour
     * @param endMinute String End Minute
     * @param endAMPM String End AM/PM
     * @return Returns a boolean value true if appointment times are outside business hours
     */
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

    /**
     * This method checks to see if there are existing appointments for the customer that may overlap with the new appointment
     * @param custID Customer ID
     * @param newApptID New appointment ID, if exists
     * @param newStartDate New appointment Start Date
     * @param newStartHour New appointment Start Hour
     * @param newStartMinute New appointment Start Minute
     * @param newStartAMPM New appointment Start AM/PM
     * @param newEndDate New appointment End Date
     * @param newEndHour New appointment End Hour
     * @param newEndMinute New appointment End Minute
     * @param newEndAMPM New appointment End AM/PM
     * @return Returns true if the new appointment overlaps with an existing appointment
     */
    private boolean checkOverlappingAppts(int custID, int newApptID, String newStartDate, String newStartHour, String newStartMinute, String newStartAMPM, String newEndDate, String newEndHour, String newEndMinute, String newEndAMPM) {
        LocalDateTime startDateTime = DateTimeUtilities.convertInputToLocalDateTime(newStartDate, newStartHour, newStartMinute, newStartAMPM);
        LocalDateTime endDateTime = DateTimeUtilities.convertInputToLocalDateTime(newEndDate, newEndHour, newEndMinute, newEndAMPM);
        for (Appointment appt : AppointmentDAOImpl.appointmentObservableList) {
            int apptID = appt.getAppointmentID();
            if ((newApptID == apptID) || (appt.getCustomerID() != custID)) {
                continue;
            }
            LocalDateTime apptStartDateTime = appt.getStartDateTime();
            LocalDateTime apptEndDateTime = appt.getEndDateTime();
            if (startDateTime.isBefore(apptStartDateTime) && endDateTime.isAfter(apptEndDateTime)) {
                return true;
            } else if ((startDateTime.equals(apptStartDateTime) || startDateTime.isAfter(apptStartDateTime)) && startDateTime.isBefore(apptEndDateTime)) {
                return true;
            } else if (endDateTime.isAfter(apptStartDateTime) && ((endDateTime.isBefore(apptEndDateTime) || endDateTime.equals(apptEndDateTime)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method populates the End Date combobox if it is empty or before the start date
     * @param actionEvent Combobox selection event
     */
    public void autofillEndDate(ActionEvent actionEvent) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if ((endDatePicker.getValue() == null) || endDate.isBefore(startDate)) {
            endDatePicker.setValue(startDate);
        }
    }

    /**
     * This method populates the Start Date combobox if it is empty or after the end date
     * @param actionEvent Date picker selection
     */
    public void autofillStartDate(ActionEvent actionEvent) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if ((startDatePicker.getValue() == null) || startDate.isAfter(endDate)) {
            startDatePicker.setValue(endDate);
        }
    }

    /**
     * This method provides validation to ensure the start time is before the end time
     * @param startDate Start Date String
     * @param startHour Start Hour String
     * @param startMinute Start Minute String
     * @param startAMPM Start AM/PM String
     * @param endDate End Date String
     * @param endHour End Hour String
     * @param endMinute End Minute String
     * @param endAMPM End AM/PM String
     * @return Returns true if end time is equal to or before start time
     */
    private boolean apptTimeValidation(String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM) {
        boolean result = false;
        LocalDateTime startDateTime = DateTimeUtilities.convertInputToLocalDateTime(startDate, startHour, startMinute, startAMPM);
        LocalDateTime endDateTime = DateTimeUtilities.convertInputToLocalDateTime(endDate, endHour, endMinute, endAMPM);
        if (endDateTime.isBefore(startDateTime) || endDateTime.equals(startDateTime)) {
            result = true;
        }
        return result;
    }
}
