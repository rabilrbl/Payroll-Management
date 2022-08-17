package rbl.payrollmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import rbl.payrollmanagement.DB.User;
import io.github.palexdev.materialfx.controls.*;

import java.io.IOException;
import java.sql.SQLException;

public class Login {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;
    @FXML
    void login(ActionEvent e) throws IOException, SQLException {
        User user;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unauthorized!");
        alert.setHeaderText("You are unauthorized to login!");

        try {
            user = new User(username.getText());
            if(user.getPassword().equals(password.getText()))
                Main.switchScene(e, "index.fxml");
            else {
                alert.show();
            }
        } catch (SQLException exc) {
            if(exc.getMessage().equals("ResultSet closed"))
                alert.show();
            else
                throw exc;
        }
    }
}
