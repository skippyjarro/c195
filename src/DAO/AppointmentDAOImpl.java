package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import utilities.DateTimeUtilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class implements the Appointment DAO interface
 */
public class AppointmentDAOImpl implements AppointmentDAO {
    public static ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    public static Appointment selectedAppointment;
    private DateTimeFormatter formatter;
    private CustomerDAOImpl customerDAO;
    private ContactDAOImpl contactDAO;
    private UserDAOImpl userDAO;
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private String formattedStartDateTime;
    private String formattedEndDateTime;
    private Timestamp startTS;
    private Timestamp endTS;
    private int customerID;
    private String customerName;
    private int userID;
    private String userName;
    private int contactID;
    private String contactName;

    /**
     * This method retrieves appointments from the database
     * @param radioSelection All, Week, or Month radio button selection
     * @return Returns ObservableList of appointments
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     * @throws ParseException Parse error
     */
    @Override
    public ObservableList<Appointment> getAppointments(String radioSelection) throws SQLException, ClassNotFoundException, ParseException {
        appointmentObservableList.clear();
        formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a z");
        String queryString = "SELECT * FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID JOIN customers c2 on appointments.Customer_ID = c2.Customer_ID JOIN users u on appointments.User_ID = u.User_ID";
        PreparedStatement statement;
        if (radioSelection.contains("Week")) {
            queryString += " WHERE WEEK(START) = WEEK(NOW())";
            statement = JDBC.openConnection().prepareStatement(queryString);
        } else if (radioSelection.contains("Month")) {
            queryString += " WHERE MONTH(START) = MONTH(NOW())";
            statement = JDBC.openConnection().prepareStatement(queryString);
        } else {
            statement = JDBC.openConnection().prepareStatement(queryString);
        }
        ResultSet result = Query.sqlQuery(statement);
        while (result.next()) {
            apptID = result.getInt("Appointment_ID");
            title = result.getString("Title");
            description = result.getString("Description");
            location = result.getString("Location");
            contactName = result.getString("Contact_Name");
            contactID = result.getInt("Contact_ID");
            type = result.getString("Type");
            start = result.getTimestamp("Start").toLocalDateTime();
            end = result.getTimestamp("End").toLocalDateTime();
            formattedStartDateTime = DateTimeUtilities.getFormattedDateTime(start);
            formattedEndDateTime = DateTimeUtilities.getFormattedDateTime(end);
            customerID = result.getInt("Customer_ID");
            customerName = result.getString("Customer_Name");
            userID = result.getInt("User_ID");
            userName = result.getString("User_Name");
            appointmentObservableList.add(new Appointment(apptID, title, description, location, type, start, end, formattedStartDateTime, formattedEndDateTime, customerID, customerName, contactID, contactName, userID, userName));
        }
        JDBC.closeConnection();
        return appointmentObservableList;
    }

    /**
     * This method retrieves appointments by customer ID
     * @param custID Customer ID
     * @return Returns true if a customer has appointments scheduled
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Override
    public boolean getCustomerAppointments(int custID) throws SQLException, ClassNotFoundException {
        boolean result = false;
        PreparedStatement verifyApptStatement = JDBC.openConnection().prepareStatement("SELECT COUNT(*) AS ApptCount FROM appointments WHERE Customer_ID = ?");
        verifyApptStatement.setInt(1, custID);
        ResultSet verifyResult = Query.sqlQuery(verifyApptStatement);
        while (verifyResult.next()) {
            if (verifyResult.getInt("ApptCount") > 0) {
                result = true;
            }
        }
        JDBC.closeConnection();
        return result;
    }

    /**
     * This method updates an existing appointment
     * @param newApptID Appointment ID
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
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public boolean updateAppointment(int newApptID, String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String customerName, String username, String contactName) throws SQLException, ClassNotFoundException {
        customerDAO = new CustomerDAOImpl();
        userDAO = new UserDAOImpl();
        contactDAO = new ContactDAOImpl();
        Timestamp startTS = Timestamp.valueOf(DateTimeUtilities.convertInputToLocalDateTime(startDate, startHour, startMinute, startAMPM));
        Timestamp endTS = Timestamp.valueOf(DateTimeUtilities.convertInputToLocalDateTime(endDate, endHour, endMinute, endAMPM));
        PreparedStatement updateStatement = JDBC.openConnection().prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        updateStatement.setString(1, apptTitle);
        updateStatement.setString(2, apptDesc);
        updateStatement.setString(3, apptLocation);
        updateStatement.setString(4, apptType);
        updateStatement.setTimestamp(5, startTS);
        updateStatement.setTimestamp(6, endTS);
        updateStatement.setString(7, User.getCurrentUserName());
        updateStatement.setInt(8, customerDAO.getCustomerIDByName(customerName));
        updateStatement.setInt(9, userDAO.getUserIDByName(username));
        updateStatement.setInt(10, contactDAO.getContactIDByName(contactName));
        updateStatement.setInt(11, newApptID);
        boolean result = Query.sqlUpdate(updateStatement);
        JDBC.closeConnection();
        return result;
    }

    /**
     * This method deletes an appointment from the database
     * @param apptID Appointment ID
     * @return Returns true if delete is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException CLass not found error
     */
    @Override
    public boolean deleteAppointment(int apptID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = JDBC.openConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
        statement.setInt(1, apptID);
        boolean result = Query.sqlUpdate(statement);
        JDBC.closeConnection();
        return result;
    }

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
     * @param newCustomerName Customer Name
     * @param newContactName Contact Name
     * @param newUserName User Name
     * @return Returns true if add is successful
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Override
    public boolean addAppointment(String apptTitle, String apptDesc, String apptLocation, String apptType, String startDate, String startHour, String startMinute, String startAMPM, String endDate, String endHour, String endMinute, String endAMPM, String newCustomerName, String newContactName, String newUserName) throws SQLException, ClassNotFoundException {
        customerDAO = new CustomerDAOImpl();
        contactDAO = new ContactDAOImpl();
        userDAO = new UserDAOImpl();
        customerID = customerDAO.getCustomerIDByName(newCustomerName);
        contactID = contactDAO.getContactIDByName(newContactName);
        userID = userDAO.getUserIDByName(newUserName);
        startTS = Timestamp.valueOf(DateTimeUtilities.convertInputToLocalDateTime(startDate, startHour, startMinute, startAMPM));
        endTS = Timestamp.valueOf(DateTimeUtilities.convertInputToLocalDateTime(endDate, endHour, endMinute, endAMPM));
        PreparedStatement addStatement = JDBC.connection.prepareStatement("INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)");
        addStatement.setString(1, apptTitle);
        addStatement.setString(2, apptDesc);
        addStatement.setString(3, apptLocation);
        addStatement.setString(4, apptType);
        addStatement.setTimestamp(5, startTS);
        addStatement.setTimestamp(6, endTS);
        addStatement.setString(7, User.getCurrentUserName());
        addStatement.setString(8, User.getCurrentUserName());
        addStatement.setInt(9, customerID);
        addStatement.setInt(10, userID);
        addStatement.setInt(11, contactID);
        boolean result = Query.sqlUpdate(addStatement);
        JDBC.closeConnection();
        return result;
    }
}
