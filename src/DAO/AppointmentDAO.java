package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * This interface is the basis for appointment IO functions
 */
public interface AppointmentDAO {

    /**
     * This method retrieves appointments from the database
     * @param radioSelection All, Week, or Month radio button selection
     * @return Returns ObservableList of appointments
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     * @throws ParseException Parse error
     */
    ObservableList<Appointment> getAppointments(String radioSelection) throws SQLException, ClassNotFoundException, ParseException;

    /**
     * This method retrieves appointments by customer ID
     * @param custID Customer ID
     * @return Returns true if a customer has appointments scheduled
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    boolean getCustomerAppointments(int custID) throws SQLException, ClassNotFoundException;

    /**
     * This method updates an existing appointment
     * @param apptID Appointment ID
     * @param apptTitle Appointment Title
     * @param apptDesc Appointment Description
     * @param apptLocation Appointment Location
     * @param apptType Appointment Type
     * @param startDate Appointment Start Date
     * @param startHour Appointment Start Hour
     * @param startMinute Appointment Start Minute
     * @param startAMPM Appointment Start AM/PM
     * @param endDate Appointment End Date
     * @param endHour Appointment End Hour
     * @param endMinute Appointment End Minute
     * @param endAMPM Appointment End AM/PM
     * @param customerName Customer Name
     * @param username User Name
     * @param contactName Contact Name
     * @return Returns true if update is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    boolean updateAppointment(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String customerName, String username, String contactName) throws SQLException, ClassNotFoundException;

    /**
     * This method deletes an appointment from the database
     * @param apptID Appointment ID
     * @return Returns true if delete is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    boolean deleteAppointment(int apptID) throws SQLException, ClassNotFoundException;

    /**
     * This method adds a new appointment to the database
     * @param apptTitle Appointment Title
     * @param apptDesc Appointment Description
     * @param apptLocation Appointment Location
     * @param apptType Appointment Type
     * @param startDate Appointment Start Date
     * @param startHour Appointment Start Hour
     * @param startMinute Appointment Start Minute
     * @param startAMPM Appointment Start AM/PM
     * @param endDate Appointment End Date
     * @param endHour Appointment End Hour
     * @param endMinute Appointment End Minute
     * @param endAMPM Appointment End AM/PM
     * @param customerName Customer Name
     * @param contactName Contact Name
     * @param userName User Name
     * @return Returns true if add is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    boolean addAppointment(String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String customerName, String contactName, String userName) throws SQLException, ClassNotFoundException;
}
