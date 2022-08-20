package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import rbl.payrollmanagement.DB.Employee;
import rbl.payrollmanagement.DB.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateEmployee implements Initializable {

    @FXML
    private MFXTextField name, username, email, phone, salary, department;
    @FXML
    private MFXPasswordField password;
    private int id;

    @FXML
    private Label label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(resourceBundle.getString("name"));
        username.setText(resourceBundle.getString("username"));
        password.setText(resourceBundle.getString("password"));
        email.setText(resourceBundle.getString("email"));
        phone.setText(resourceBundle.getString("phone"));
        salary.setText(resourceBundle.getString("salary"));
        department.setText(resourceBundle.getString("department"));
        id = Integer.parseInt(resourceBundle.getString("id"));

        label.setText("Update employee");
    }

    @FXML
    void saveEmployee(ActionEvent e) throws SQLException, IOException {
        Employee emp = new Employee(this.id);
        emp.setDepartment(department.getText());
        emp.setEmail(email.getText());
        emp.setPhone(email.getText());
        emp.setSalary(Integer.parseInt(salary.getText()));
        User usr = new User(username.getText());
        usr.setName(name.getText());
        if(password.getText().length() > 1)
            usr.setPassword(password.getText());
        usr.setUsername(username.getText());
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
