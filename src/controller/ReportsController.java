package controller;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import javafx.collections.FXCollections;
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
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the reports screen
 */
public class ReportsController implements Initializable {
    ObservableList<Appointment> appoointmentList;
    CustomerDAOImpl customerDAO;
    ContactDAOImpl contactDAO;
    UserDAOImpl userDAO;
    AppointmentDAOImpl appointmentDAO;
    String[] reportTypeArray;
    ObservableList<String> reportTypesList;
    String[] monthArray;
    ObservableList<String> monthList;
    String[] apptTypesArray;
    ObservableList<String> apptTypesList;

    @FXML
    private ComboBox reportBox;

    @FXML
    private Label reportLabel1;

    @FXML
    private ComboBox reportBox1;

    @FXML
    private Label monthLabel;

    @FXML
    private ComboBox monthBox;

    @FXML
    private Label apptTypeLabel;

    @FXML
    private ComboBox apptTypeBox;

    @FXML
    private Button runReportButton;

    @FXML
    private Label apptCountLabel;

    @FXML
    private TableView apptTableview;

    @FXML
    private TableColumn apptIdCol;

    @FXML
    private TableColumn apptTitleCol;

    @FXML
    private TableColumn apptDescriptionCol;

    @FXML
    private TableColumn apptLocationCol;

    @FXML
    private TableColumn contactCol;

    @FXML
    private TableColumn typeCol;

    @FXML
    private TableColumn startDateTimeCol;

    @FXML
    private TableColumn endDateTimeCol;

    @FXML
    private TableColumn CustomerIdCol;

    @FXML
    private TableColumn userIdCol;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appoointmentList = FXCollections.observableArrayList();
        customerDAO = new CustomerDAOImpl();
        contactDAO = new ContactDAOImpl();
        userDAO = new UserDAOImpl();
        appointmentDAO = new AppointmentDAOImpl();
        reportTypeArray = new String[]{"Customer Appt by Month and Type", "Contact Appt Schedule", "Appts by User"};
        reportTypesList = FXCollections.observableArrayList(reportTypeArray);
        monthArray = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthList = FXCollections.observableArrayList(monthArray);
        monthBox.setItems(monthList);
        apptTypesArray = new String[]{"Planning Session", "De-Briefing", "Support", "Other"};
        apptTypesList = FXCollections.observableArrayList(apptTypesArray);
        apptTypeBox.setItems(apptTypesList);

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDateTime"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        reportBox.setItems(reportTypesList);
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

    /**
     * This method populates the first combobox depending on the report type selected and shows the remaining comboboxes based on report type
     * @param actionEvent Report type combobox selection event
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    public void setReportType(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int selectedReport = reportBox.getSelectionModel().getSelectedIndex();
        switch (selectedReport) {
            case 0:
                reportLabel1.setText("Customer");
                reportLabel1.setVisible(true);
                reportBox1.setVisible(true);
                reportBox1.setItems(customerDAO.getCustomerNames());
                monthLabel.setVisible(true);
                monthBox.setVisible(true);
                apptTypeLabel.setVisible(true);
                apptTypeBox.setVisible(true);
                runReportButton.setVisible(true);
                break;
            case 1:
                reportLabel1.setText("Contact");
                reportLabel1.setVisible(true);
                reportBox1.setVisible(true);
                reportBox1.setItems(contactDAO.getContacts());
                monthLabel.setVisible(false);
                monthBox.setVisible(false);
                apptTypeLabel.setVisible(false);
                apptTypeBox.setVisible(false);
                runReportButton.setVisible(true);
                startDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
                break;
            case 2:
                reportLabel1.setText("User");
                reportLabel1.setVisible(true);
                reportBox1.setVisible(true);
                reportBox1.setItems(userDAO.getUserList());
                monthLabel.setVisible(false);
                monthBox.setVisible(false);
                apptTypeLabel.setVisible(false);
                apptTypeBox.setVisible(false);
                runReportButton.setVisible(true);
                break;
            default:
                reportLabel1.setVisible(false);
                reportBox1.setVisible(false);
                monthLabel.setVisible(false);
                monthBox.setVisible(false);
                apptTypeLabel.setVisible(false);
                apptTypeBox.setVisible(false);
                runReportButton.setVisible(false);
        }
    }

    /**
     * This method populates the appointments table view and displays the total number of appointments meeting the selected criteria
     * @param actionEvent Button click event
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    public void runReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        appoointmentList.clear();
        int reportTypeSelection = reportBox.getSelectionModel().getSelectedIndex();
        String reportBox1Selection = reportBox1.getSelectionModel().getSelectedItem().toString();
        int monthSelection = 0;
        String apptTypeSelection = null;
        if (reportTypeSelection == 0){
            monthSelection = monthBox.getSelectionModel().getSelectedIndex() + 1;
            apptTypeSelection = apptTypeBox.getSelectionModel().getSelectedItem().toString();
        }
        int totalAppts = 0;
        switch (reportTypeSelection) {
            case 0:
                for (Appointment appt : AppointmentDAOImpl.appointmentObservableList) {
                    if (appt.getCustomerID() == customerDAO.getCustomerIDByName(reportBox1Selection)) {
                        if (appt.getStartDateTime().getMonth().getValue() == monthSelection) {
                            if (appt.getType().equals(apptTypeSelection)) {
                                appoointmentList.add(appt);
                                totalAppts++;
                            }
                        }

                    }
                }
                break;
            case 1:
                for (Appointment appt : AppointmentDAOImpl.appointmentObservableList) {
                    if (appt.getContactName().equals(reportBox1Selection)) {
                        appoointmentList.add(appt);
                        totalAppts++;
                    }
                }
                break;
            case 2:
                for (Appointment appt : AppointmentDAOImpl.appointmentObservableList) {
                    if (appt.getUserName().equals(reportBox1Selection)) {
                        appoointmentList.add(appt);
                        totalAppts++;
                    }
                }
                break;
        }
        apptTableview.setItems(appoointmentList);
        apptCountLabel.setText(String.valueOf(totalAppts));
    }
}
