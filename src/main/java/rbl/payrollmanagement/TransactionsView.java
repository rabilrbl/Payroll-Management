package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import rbl.payrollmanagement.DB.Transactions;
import rbl.payrollmanagement.DB.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionsView implements Initializable {
    @FXML
    private MFXListView<String> listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet res = Transactions.getAll();
            while (res.next()) {
                String msg = "Payment of " + res.getString("payment") + " made to " + new User(res.getInt("user")).getName();
                listView.getItems().add(msg);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
