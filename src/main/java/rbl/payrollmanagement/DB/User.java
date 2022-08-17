package rbl.payrollmanagement.DB;

import java.sql.*;


public class User {
    final static String DATABASE_URL = "jdbc:sqlite:payroll.db";
    private final static String TABLE_NAME = "users";

    private String username, password, role, name;
    private int id;

    private static final Connection connection = Connector();

    public static Connection Connector() {
         try {
            Class.forName("org.sqlite.JDBC");
             System.out.println("Connection success");
            return DriverManager.getConnection(DATABASE_URL);
         } catch (Exception e){
             System.out.println("Connection fail");
             System.out.println(e.getMessage());
             return null;
         }
    }

    public User() throws SQLException {
        assert connection != null;
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (\n"
                + " id integer PRIMARY KEY,\n"
                + " username text,\n"
                + " password text,\n"
                + " role text,\n"
                + " name text);";

        Statement command = connection.createStatement();
        command.execute(sql);
    }

    public User(String username) throws SQLException {
        assert connection != null;

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE username='"+username+"'";
        Statement command = connection.createStatement();

        ResultSet result = command.executeQuery(sql);
        this.id = result.getInt("id");
        this.username = result.getString("username");
        this.password = result.getString("password");
        this.name = result.getString("name");
        this.role = result.getString("role");

    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) throws SQLException {
        this.name = name;
        this.update("name", this.name);
    }

    public void setUsername(String username) throws SQLException {
        this.username = username;
        this.update("username", this.username);
    }

    public void setRole(String role) throws SQLException {
        this.role = role;
        this.update("role", this.role);
    }

    public void setPassword(String password) throws SQLException {
        this.password = password;
        this.update("password", this.password);
    }

    public void update(String column, String value) throws SQLException {
        String sql = "UPDATE "+TABLE_NAME+" SET "+column+"="+value;
        Statement command = connection.createStatement();
        command.executeUpdate(sql);
    }

    public static User createNew(String name, String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO "+TABLE_NAME+"(name,username,password,role) VALUES(?,?,?,?)";
        PreparedStatement command = connection.prepareStatement(sql);
        command.setString(1, name);
        command.setString(2, username);
        command.setString(3, password);
        command.setString(4, role);
        command.executeUpdate();

        return new User(username);
    }
}