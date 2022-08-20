package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import rbl.payrollmanagement.DB.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;

    @FXML
    private MFXButton loginButton;

    public static Object loggedInUser[][];

    public static boolean isLoggedIn(){
        return loggedInUser != null;
    }

    void login(Event e) throws IOException, SQLException {
        User user;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unauthorized!");
        alert.setHeaderText("You are unauthorized to login!");
        alert.setContentText("Either you have entered your credentials wrong or you don't have access to this platform.");

        try {
            user = new User(username.getText());
            loggedInUser = new Object[][]{{"username",user.getUsername()},{"name", user.getName()}};
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        password.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                try {
                    this.login(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        loginButton.setOnAction(e -> {
            try {
                this.login(e);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
        });
    }
}
