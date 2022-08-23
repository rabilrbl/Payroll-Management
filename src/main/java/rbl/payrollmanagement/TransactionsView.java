package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import rbl.payrollmanagement.DB.Employee;
import rbl.payrollmanagement.DB.Transactions;
import rbl.payrollmanagement.DB.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class TransactionsView implements Initializable {
    @FXML
    private MFXTableView<Transactions> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Transactions> userColumn = new MFXTableColumn<>("User", false);
        MFXTableColumn<Transactions> paymentColumn = new MFXTableColumn<>("Payment", false);
        MFXTableColumn<Transactions> addColumn = new MFXTableColumn<>("Additions", false);
        MFXTableColumn<Transactions> dedColumn = new MFXTableColumn<>("Deductions", false);

        userColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Transactions::getUserName));
        paymentColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Transactions::getPayment));
        addColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Transactions::getAddition));
        dedColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Transactions::getDeductions));

        table.getTableColumns().addAll(userColumn, paymentColumn, addColumn, dedColumn);
        try {
            ResultSet res = Transactions.getALL(new User((String) Login.loggedInUser[0][1]));
            while (res.next()) {
                int additions = res.getInt("hra") + res.getInt("da") + res.getInt("medical") + res.getInt("overtime") + res.getInt("additionOther");
                int deductions = res.getInt("professionalTax") + res.getInt("esi") + res.getInt("deductionOther");
                String user = new User(res.getInt("user")).getName();
                int payment = res.getInt("payment");
                String msg =  user+"\t"+payment+"\t"+(additions)+"\t"+(deductions);
                table.getItems().add(new Transactions(user, payment, additions, deductions));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
