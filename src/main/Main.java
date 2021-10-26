package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * This is the Main class
 * @author Nate Sukhtipyaroge
 * @version 1.0
 */
public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Appointment Manager");
        stage.setScene(new Scene(root, 480, 480));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Locale.setDefault(Locale.FRANCE);
        launch(args);
    }
}
