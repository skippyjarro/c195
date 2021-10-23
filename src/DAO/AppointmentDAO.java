package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.text.ParseException;

public interface AppointmentDAO {
    ObservableList<Appointment> getAppointments(String radioSelection) throws SQLException, ClassNotFoundException, ParseException;
    boolean getCustomerAppointments(int custID) throws SQLException, ClassNotFoundException;
    boolean updateAppointment(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String customerName, String username, String contactName) throws SQLException, ClassNotFoundException;
    boolean deleteAppointment(int apptID) throws SQLException, ClassNotFoundException;
    boolean addAppointment(String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String customerName, String contactName, String userName) throws SQLException, ClassNotFoundException;
}
