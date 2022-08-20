package rbl.payrollmanagement;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbl.payrollmanagement.DB.Employee;
import rbl.payrollmanagement.DB.Transactions;
import rbl.payrollmanagement.DB.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    private  static Stage stage;
    private static void initDatabase() throws SQLException {
        new User();
        new Employee();
        new Transactions();
    }
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        initDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Payroll Management");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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