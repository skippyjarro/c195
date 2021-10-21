package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.text.ParseException;

public interface AppointmentDAO {
    public ObservableList<Appointment> getAppointments(String radioSelection) throws SQLException, ClassNotFoundException, ParseException;
    public void updateAppointment(Appointment appt);
    public void deleteAppointment(Appointment appt);
    public void addAppointment(Appointment appt);
}
