package rbl.payrollmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Navbar {
    @FXML
    void logout(ActionEvent e) throws IOException {
        Main.switchScene(e, "login.fxml");
    }

    @FXML
    void switchToEmployee(ActionEvent e) throws IOException {
        Main.switchScene(e, "employee.fxml");
    }

    @FXML
    void switchToHome(ActionEvent e) throws IOException {
        Main.switchScene(e, "index.fxml");
    }
}
