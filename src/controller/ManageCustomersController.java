package controller;

import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.FirstLevelDivisionDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the Manage Customers view
 */
public class ManageCustomersController implements Initializable {
    CustomerDAOImpl customerDAO;
    CountryDAOImpl countryDAO;
    FirstLevelDivisionDAOImpl firstLevelDivisionDAO;
    int customerID;
    String customerName;
    String customerPhone;
    String customerAddress;
    String customerPostalCode;
    int divisionID;

    @FXML
    private ComboBox countryBox;

    @FXML
    private TextField custIDField;

    @FXML
    private TextField custNameField;

    @FXML
    private TextField custPhoneField;

    @FXML
    private TextArea custAddressField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private Label firstLevelDivLabel;

    @FXML
    private ComboBox firstLevelDivBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView customerTable;

    @FXML
    private TableColumn custIDCol;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn phoneCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn firstLevelDivCol;

    @FXML
    private TableColumn postCodeCol;

    @FXML
    private TableColumn countryCol;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstLevelDivisionDAO = new FirstLevelDivisionDAOImpl();
        countryDAO = new CountryDAOImpl();
        customerDAO = new CustomerDAOImpl();
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        firstLevelDivCol.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("custCountry"));

        populateCustomerTable();

        //Populate Country combobox
        try {
            countryBox.setItems(new CountryDAOImpl().getCountries());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void populateCustomerTable() {
        customerTable.getItems().clear();
        try {
            customerTable.setItems(customerDAO.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method populates the first-level division combobox after selecting a country
     * @param actionEvent country selection event
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setFirstLevelDiv(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!countryBox.getSelectionModel().isEmpty()) {
            ObservableList<String> firstLevelDiv = new FirstLevelDivisionDAOImpl().getFirstLevelDiv(countryBox.getSelectionModel().getSelectedItem().toString());
            firstLevelDivBox.setItems(firstLevelDiv);
        }
    }

    /**
     * This method navigates the user to the Appointment Manager screen.
     *
     * @param actionEvent Button click event
     * @throws IOException Incorrect input
     */
    public void toAppointmentManager(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentManager.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1366, 768);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }


    public void saveCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        customerID = Integer.parseInt(custIDField.getText());
        customerName = custNameField.getText();
        customerAddress = custAddressField.getText();
        customerPostalCode = postalCodeField.getText();
        customerPhone = custPhoneField.getText();
        divisionID = firstLevelDivisionDAO.getFirstLevelDivID(firstLevelDivBox.getSelectionModel().getSelectedItem().toString());
        if (!custIDField.getText().isEmpty()) {
            customerDAO.updateCustomer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
        } else {
            customerDAO.addCustomer(customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
        }
        clearForm(actionEvent);
        populateCustomerTable();
    }

    public void clearForm(ActionEvent actionEvent) {
        firstLevelDivBox.getItems().clear();
        if (!countryBox.getSelectionModel().isEmpty()) {
            countryBox.getSelectionModel().clearSelection();
        }
        custNameField.clear();
        custPhoneField.clear();
        custAddressField.clear();
        postalCodeField.clear();
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        boolean result = customerDAO.deleteCustomer(customer.getCustomerID());
        if (result) {
            System.out.println("Yay");
        } else {
            System.out.println("Boo");
        }
        clearForm(actionEvent);
        populateCustomerTable();
    }

    public void editCustomer(ActionEvent actionEvent) {
        Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        countryBox.getSelectionModel().select(customer.getCustCountry());
        custIDField.setText("" + customer.getCustomerID());
        custNameField.setText(customer.getCustomerName());
        custPhoneField.setText(customer.getCustomerPhone());
        custAddressField.setText(customer.getCustomerAddress());
        postalCodeField.setText(customer.getCustomerPostalCode());
        firstLevelDivBox.getSelectionModel().select(customer.getFirstLevelDivision());
    }
}
