package model;

import java.time.LocalDateTime;

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

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFormattedStartDateTime() {
        return formattedStartDateTime;
    }

    public void setFormattedStartDateTime(String formattedStartDateTime) {
        this.formattedStartDateTime = formattedStartDateTime;
    }

    public String getFormattedEndDateTime() {
        return formattedEndDateTime;
    }

    public void setFormattedEndDateTime(String formattedEndDateTime) {
        this.formattedEndDateTime = formattedEndDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
