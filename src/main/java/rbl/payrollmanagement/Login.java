package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import rbl.payrollmanagement.DB.User;

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
        alert.setContentText("Either you have entered your credentials wrong or you don't have access to this platform.");

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
