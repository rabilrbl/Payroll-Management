package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import rbl.payrollmanagement.DB.Employee;
import rbl.payrollmanagement.DB.Transactions;
import rbl.payrollmanagement.DB.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class Index implements Initializable {

    @FXML
    Label empCount, trnsCount, greet;

    @FXML
    Label label1, label2, label3, label4, label5;

    private String greetMessage(){
        Date date = new Date();
        String msg;
        int currentHour = date.getHours();
        if(currentHour >= 4 && currentHour < 12)
            msg = "Good Morning!";
        else if(currentHour >= 12 && currentHour <= 17)
            msg = "Good Afternoon!";
        else if(currentHour > 17 || currentHour < 4)
            msg = "Good Evening!";
        else
            msg = "Welcome!";

        return msg;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            empCount.setText(String.valueOf(Employee.getCount()));
            trnsCount.setText(String.valueOf(Transactions.getCount()));
            ResultSet trns = Transactions.getLast5();
            String[] msg = new String[5];
            int i = -1;
            while (trns.next()) {
                int payment = trns.getInt("payment");
                String userName = new User(trns.getInt("user")).getName();
                msg[++i] = userName+ " got paid ₹"+ payment;
            }
            label1.setText(0 <= i ? "➱ "+msg[0] : "");
            label2.setText(1 <= i ? "➱ "+msg[1] : "");
            label3.setText(2 <= i ? "➱ "+msg[2] : "");
            label4.setText(3 <= i ? "➱ "+msg[3] : "");
            label5.setText(4 <= i ? "➱ "+msg[4] : "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        greet.setText(greetMessage());
    }
}