package rbl.payrollmanagement;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Payroll Management");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Main.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }

    public static void switchScene(Event e, String name) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(name)));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}