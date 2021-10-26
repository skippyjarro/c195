package model;

/**
 * This class serves as the model for Contacts
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * This is the Contact constructor
     * @param contactID Contact ID
     * @param contactName Contact Name
     * @param contactEmail Contact email address
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
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
     * Getter for Contact Name
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
     * Getter for Contact Email Address
     * @return Returns Contact Email Address
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter for Contact Email Address
     * @param contactEmail Contact Email Address
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * This method overrides the Object toString method
     * @return Returns string with Contact Name
     */
    @Override
    public String toString() {
        return this.contactName;
    }
}
