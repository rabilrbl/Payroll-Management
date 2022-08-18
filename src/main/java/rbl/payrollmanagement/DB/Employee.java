package rbl.payrollmanagement.DB;

import java.sql.*;

public class Employee  {
    private final static String TABLE_NAME = "employees";

    private String department, phone, email;
    private int id, salary, user;
    private static final Connection connection = User.connection;

    public Employee() throws SQLException {
        assert connection != null;
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (\n"
                + " id integer PRIMARY KEY,\n"
                + " user integer,\n"
                + "department text,\n"
                + "phone text,\n"
                + "email text,\n"
                + "salary integer)";

        Statement command = connection.createStatement();
        command.execute(sql);
    }

    public Employee(String username) throws SQLException {
        assert connection != null;

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE user='"+new User(username).getId()+"'";
        Statement command = connection.createStatement();

        ResultSet result = command.executeQuery(sql);
        this.id = result.getInt("id");
        this.user = result.getInt("user");
        this.salary = result.getInt("salary");
        this.department = result.getString("department");
        this.phone = result.getString("phone");
        this.email = result.getString("email");

    }


    public Employee(int Id, int user, int salary, String department, String phone, String email){
        this.id = Id;
        this.user = user;
        this.salary = salary;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    public void update(String column, String value) throws SQLException {
        String sql = "UPDATE "+TABLE_NAME+" SET "+column+"="+value;
        Statement command = connection.createStatement();
        command.executeUpdate(sql);
    }

    public static ResultSet getAll() throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME;
        Statement command = connection.createStatement();

        return command.executeQuery(sql);
    }

    public int getId() {
        return id;
    }

    public User getUser() throws SQLException {
        return new User(this.id);
    }

    public String getName() {
        try {
            return this.getUser().getName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        try {
            return this.getUser().getUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    public static Employee createNew(String name, String username, String password, String role, String phone, String email, int salary, String department) throws SQLException {
        User user = User.createNew(name, username, password, role);
        String sql = "INSERT INTO "+TABLE_NAME+"(user,department,phone,email,salary) VALUES(?,?,?,?,?)";
        PreparedStatement command = connection.prepareStatement(sql);
        command.setInt(1, user.getId());
        command.setString(2, department);
        command.setString(3, phone);
        command.setString(4, email);
        command.setInt(5, salary);
        command.executeUpdate();

        return new Employee(username);
    }
}
