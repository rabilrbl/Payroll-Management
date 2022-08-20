package rbl.payrollmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Navbar implements Initializable {
    @FXML
    private Label loggedUser;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Login.isLoggedIn())
        loggedUser.setText((String) Login.loggedInUser[1][1]);
    }
}
