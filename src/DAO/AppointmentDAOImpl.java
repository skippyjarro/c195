package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DateTimeUtilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class AppointmentDAOImpl implements AppointmentDAO {
    DateTimeFormatter formatter;
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String start;
    private String end;
    private int customerID;
    private int userID;


    @Override
    public ObservableList<Appointment> getAppointments(String radioSelection) throws SQLException, ClassNotFoundException, ParseException {
        formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a z");
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID";
        PreparedStatement statement;
        if (radioSelection.contains("Week")) {
            queryString += " WHERE ? = ?";
            statement = JDBC.openConnection().prepareStatement(queryString);
            statement.setString(1, "WEEK(Start)");
            statement.setString(2, "WEEK(GetDate())");
        } else if (radioSelection.contains("Month")) {
            queryString += " WHERE ? = ?";
            statement = JDBC.openConnection().prepareStatement(queryString);
            statement.setString(1, "MONTH(Start)");
            statement.setString(2, "MONTH(GetDate())");
        } else {
            statement = JDBC.openConnection().prepareStatement(queryString);
        }
        ResultSet result = Query.sqlQuery(statement);
        while (result.next()) {
            apptID = result.getInt("Appointment_ID");
            title = result.getString("Title");
            description = result.getString("Description");
            location = result.getString("Location");
            contact = result.getString("Contact_Name");
            type = result.getString("Type");
            start = DateTimeUtilities.convertToLocalDateTime(result.getDate("Start").toLocalDate(), result.getTime("Start").toLocalTime());
            end = DateTimeUtilities.convertToLocalDateTime(result.getDate("End").toLocalDate(), result.getTime("End").toLocalTime());
            customerID = result.getInt("Customer_ID");
            userID = result.getInt("User_ID");
            allAppointments.add(new Appointment(apptID, title, description, location, type, start, end, customerID, userID, contact));
        }
        JDBC.closeConnection();
        return allAppointments;
    }

    @Override
    public void updateAppointment(Appointment appt) {

    }

    @Override
    public void deleteAppointment(Appointment appt) {

    }

    @Override
    public void addAppointment(Appointment appt) {

    }
}
