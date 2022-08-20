package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import rbl.payrollmanagement.DB.Transactions;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GenerateTransaction implements Initializable {
    @FXML
    private MFXTextField salary, hra, da, medical, overtime, professionalTax, esi, additionOther, deductionOther;
    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salary.setText(resourceBundle.getString("salary"));
        userId = Integer.parseInt(resourceBundle.getString("userId"));
        double sal = Double.parseDouble(salary.getText());
        hra.setText(String.valueOf(Math.round(sal * 0.4)));
    }

    private int toInt(MFXTextField s) {
        return Integer.parseInt(s.getText());
    }

    @FXML
    private void genTransaction(ActionEvent e) throws SQLException {
        System.out.println(salary.getText() + "  " + hra.getText());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Transaction generated successfully!");

        int payment = toInt(salary) + toInt(hra) + toInt(da) + toInt(medical) + toInt(overtime) + toInt(additionOther) - toInt(professionalTax) - toInt(esi) - toInt(deductionOther);

        Transactions.createNew(userId, String.valueOf(payment), hra.getText(), da.getText(), medical.getText(), overtime.getText(), professionalTax.getText(), esi.getText(), deductionOther.getText(), additionOther.getText());

        alert.setContentText("A payment of "+payment+" has been initiated.");
        alert.show();
    }


}
