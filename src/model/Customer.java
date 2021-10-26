package model;

/**
 * This class serves as the model for Customers
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private String firstLevelDivision;
    private String custCountry;

    /**
     * This is the Customer contructor
     * @param customerID Customer ID
     * @param customerName Customer Name
     * @param customerAddress Customer Address
     * @param customerPostalCode Customer Postal Code
     * @param customerPhone Customer Phone Number
     * @param firstLevelDivision First-Level Division
     * @param custCountry Customer Country
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, String firstLevelDivision, String custCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.firstLevelDivision = firstLevelDivision;
        this.custCountry = custCountry;
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
     * Getter for Customer Address
     * @return Returns Customer Address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter for Customer Address
     * @param customerAddress Customer Address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter for Customer Postal Code
     * @return Returns Customer Postal Code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Setter for Customer Postal Code
     * @param customerPostalCode Customer Postal Code
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Getter for Customer Phone
     * @return Returns Customer Phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Setter for Customer Phone
     * @param customerPhone Customer Phone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Getter for First-Level Division
     * @return Returns First-Level Division
     */
    public String getFirstLevelDivision() {
        return firstLevelDivision;
    }

    /**
     * Setter for First-Level Division
     * @param firstLevelDivision First-Level Division
     */
    public void setFirstLevelDivision(String firstLevelDivision) {
        this.firstLevelDivision = firstLevelDivision;
    }

    /**
     * Getter for Customer Country
     * @return Returns Customer Country
     */
    public String getCustCountry() {
        return custCountry;
    }

    /**
     * Setter for Customer Country
     * @param custCountry Customer Country
     */
    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }
}
