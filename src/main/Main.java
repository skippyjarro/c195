package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

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
        launch(args);
    }
}
