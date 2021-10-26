package model;

/**
 * This functional interface displays an alert
 */
public interface DisplayAlert {

    /**
     * This method is the base for a Lambda expression to display alerts
     * @param messageText Message Text String
     */
    void displayAlert(String messageText);
}
