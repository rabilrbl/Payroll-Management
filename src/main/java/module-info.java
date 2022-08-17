module rbl.payrollmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires MaterialFX;
    requires sqlite.jdbc;


    opens rbl.payrollmanagement to javafx.fxml;
    exports rbl.payrollmanagement;
    exports rbl.payrollmanagement.DB;
    opens rbl.payrollmanagement.DB to javafx.fxml;
}