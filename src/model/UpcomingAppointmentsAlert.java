package model;

/**
 * This functional interface serves to display an alert for upcoming appointments
 */
public interface UpcomingAppointmentsAlert {

    /**
     * This method is the base for a Lambda expression to display an alert with appointments upcoming in the next 15 minutes
     * @param appt Upcoming Appointment
     */
    void displayUpcomingAppointments(Appointment appt);
}
