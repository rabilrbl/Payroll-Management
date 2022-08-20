package rbl.payrollmanagement;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import rbl.payrollmanagement.DB.Employee;
import rbl.payrollmanagement.DB.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ListResourceBundle;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {

    @FXML
    private MFXTableView<Employee> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setupTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.autosizeColumnsOnInitialization();
    }


    private void setupTable() throws SQLException {
        MFXTableColumn<Employee> IdColumn = new MFXTableColumn<>("Id", false, Comparator.comparing(Employee::getId));
        MFXTableColumn<Employee> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Employee::getName));
        MFXTableColumn<Employee> usernameColumn = new MFXTableColumn<>("Username", false, Comparator.comparing(Employee::getUsername));
        MFXTableColumn<Employee> deptColumn = new MFXTableColumn<>("Department", false, Comparator.comparing(Employee::getDepartment));
        MFXTableColumn<Employee> phoneColumn = new MFXTableColumn<>("Phone", false, Comparator.comparing(Employee::getPhone));
        MFXTableColumn<Employee> emailColumn = new MFXTableColumn<>("Email", false, Comparator.comparing(Employee::getEmail));
        MFXTableColumn<Employee> salaryColumn = new MFXTableColumn<>("Salary", false, Comparator.comparing(Employee::getSalary));


        nameColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getName));
        usernameColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getUsername));
        deptColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getDepartment));
        IdColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getId) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        IdColumn.setAlignment(Pos.CENTER_RIGHT);
        phoneColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getPhone));
        emailColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getEmail));
        salaryColumn.setRowCellFactory(emp -> new MFXTableRowCell<>(Employee::getSalary));

        table.getTableColumns().addAll(IdColumn, nameColumn, usernameColumn, deptColumn, phoneColumn, emailColumn, salaryColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Id", Employee::getId),
                new StringFilter<>("Name", Employee::getName),
                new StringFilter<>("Username", Employee::getUsername),
                new StringFilter<>("Department", Employee::getDepartment),
                new StringFilter<>("Phone", Employee::getPhone),
                new StringFilter<>("Email", Employee::getEmail),
                new IntegerFilter<>("Salary", Employee::getSalary)
        );
        ResultSet results = Employee.getAll();
        String department, phone, email, salary;
        int id, user;
        while (results.next()) {
            id = results.getInt("id");
            user = results.getInt("user");
            department = results.getString("department");
            phone = results.getString("phone");
            email = results.getString("email");
            salary = results.getString("salary");
            table.getItems().add(new Employee(id, user, Integer.parseInt(salary), department, phone, email));
        }
    }

    @FXML
    void addEmployee(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
        loader.setController(new AddEmployee());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void generateTransaction(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("generateTransaction.fxml"));
        Employee emp = getSelectedItem();
        loader.setResources(new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                try {
                    return new Object[][]{
                            {"salary", String.valueOf(emp.getSalary())},
                            {"userId", String.valueOf(emp.getUser().getId())}
                    };
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Generate Transaction");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private Employee getSelectedItem() throws SQLException {
        try {
            int id = table.getSelectionModel().getSelectedValues().get(0).getId();
            return new Employee(id);

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a single item to perform operations");
            alert.setContentText("Please select an item to proceed!");
            alert.showAndWait();
            return null;
        }
    }

    @FXML
    public void updateItem(ActionEvent event) throws SQLException, IOException {
        Employee emp = getSelectedItem();
        if (emp != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));

            loader.setResources(new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][]{
                            {"name", emp.getName()},
                            {"username", emp.getUsername()},
                            {"password", ""},
                            {"email", emp.getEmail()},
                            {"salary", String.valueOf(emp.getSalary())},
                            {"phone", emp.getPhone()},
                            {"department", emp.getDepartment()},
                            {"id", String.valueOf(emp.getId())}
                    };
                }
            });
            loader.setController(new UpdateEmployee());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update Employee");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void deleteEmployee(ActionEvent e) throws SQLException, IOException {
        Employee emp = getSelectedItem();
        if (emp != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText("Are you sure to delete?");
            alert.setContentText("You are about to delete employee named " + emp.getName() + " with username " + emp.getUsername());

            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.get().equals(ButtonType.OK)) {
                emp.delete();
                Main.switchScene(e, "employee.fxml");
            }
        }
    }

    @FXML
    void refresh(ActionEvent e) throws IOException {
        Main.switchScene(e, "employee.fxml");
    }

}
