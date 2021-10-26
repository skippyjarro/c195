package model;

import java.time.LocalDateTime;

/**
 * This class serves as the model for Appointments
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String formattedStartDateTime;
    private String formattedEndDateTime;
    private int customerID;
    private String customerName;
    private int contactID;
    private String contactName;
    private int userID;
    private String userName;

    /**
     * This is the appointment constructor
     * @param appointmentID Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param startDateTime Appointment Start Date/Time
     * @param endDateTime Appointment End Date/Time
     * @param formattedStartDateTime Formatted Start Date/Time
     * @param formattedEndDateTime Formatted End Date/Time
     * @param customerID Customer ID
     * @param customerName Customer Name
     * @param contactID Contact ID
     * @param contactName Contact Name
     * @param userID User ID
     * @param userName User Name
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String formattedStartDateTime, String formattedEndDateTime, int customerID, String customerName, int contactID, String contactName, int userID, String userName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.formattedStartDateTime = formattedStartDateTime;
        this.formattedEndDateTime = formattedEndDateTime;
        this.customerID = customerID;
        this.customerName = customerName;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
        this.userName = userName;
    }

    /**
     * Getter for Appointment ID
     * @return Returns Appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter for Appointment ID
     * @param appointmentID Appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter for Appointment Title
     * @return Returns Appointment Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for Appointment Title
     * @param title Appointment Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Appointment Description
     * @return Returns Appointment Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for Appointment Description
     * @param description Appointment Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for Appointment Loacation
     * @return Returns Appointment Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for Appointment Location
     * @param location Appointment Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for Appointment Type
     * @return Returns Appointment Type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Appointment Type
     * @param type Appointment Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Appointment Start Date/Time
     * @return Returns Appointment Start Date/Time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Setter for Appointment Start Date/Time
     * @param startDateTime Appointment Start Date/Time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Getter for Appointment End Date/Time
     * @return Returns Appointment End Date/Time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Setter for Appointment End Date/Time
     * @param endDateTime Appointment End Date/Time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Getter for Customer ID
     * @return Returns Customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for Customer ID
     * @param customerID Customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for Contact ID
     * @return Returns Contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for Contact ID
     * @param contactID Contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for User ID
     * @return Returns User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for User ID
     * @param userID User ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for Formatted Start Date/Time
     * @return Returns Formatted Start Date/Time
     */
    public String getFormattedStartDateTime() {
        return formattedStartDateTime;
    }

    /**
     * Setter for Formatted Start Date/Time
     * @param formattedStartDateTime Formatted Start Date/Time
     */
    public void setFormattedStartDateTime(String formattedStartDateTime) {
        this.formattedStartDateTime = formattedStartDateTime;
    }

    /**
     * Getter for Formatted End Date/Time
     * @return Returns Formatted End Date/Time
     */
    public String getFormattedEndDateTime() {
        return formattedEndDateTime;
    }

    /**
     * Setter for Formatted End Date/Time
     * @param formattedEndDateTime Formatted End Date/Time
     */
    public void setFormattedEndDateTime(String formattedEndDateTime) {
        this.formattedEndDateTime = formattedEndDateTime;
    }

    /**
     * Getter for Customer Name
     * @return Returns Customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for Customer Name
     * @param customerName Customer Name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for Contact name
     * @return Returns Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for Contact Name
     * @param contactName Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for User Name
     * @return Returns User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for User name
     * @param userName User Name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
