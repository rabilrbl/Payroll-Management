package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddEmployee {

    @FXML
    private MFXTextField name, username, email, phone, salary, department;
    @FXML
    private MFXPasswordField password;
    @FXML
    void saveEmployee(ActionEvent e) throws SQLException, IOException {
        rbl.payrollmanagement.DB.Employee.createNew(name.getText(), username.getText(), password.getText(), "employee", phone.getText(), email.getText(), Integer.parseInt(salary.getText()), department.getText());
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
