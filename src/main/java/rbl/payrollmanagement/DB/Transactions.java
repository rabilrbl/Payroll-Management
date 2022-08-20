package rbl.payrollmanagement.DB;

import java.sql.*;

public class Transactions {
    private static final Connection connection = User.connection;
    private final static String TABLE_NAME = "transactions";

    public Transactions() throws SQLException {
        assert connection != null;
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (\n"
                + " id integer PRIMARY KEY,\n"
                + "payment text,\n"
                + "user int,\n"
                + "hra text,\n"
                + "da text,\n"
                + "medical text,\n"
                + "overtime text,\n"
                + "professionalTax text,\n"
                + "esi text,\n"
                + "deductionOther text,\n"
                + "additionOther text);";

        Statement command = connection.createStatement();
        command.execute(sql);
    }

    public ResultSet Transactions(int id) throws SQLException {
        assert connection != null;

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id='"+id+"'";
        Statement command = connection.createStatement();

        return command.executeQuery(sql);
    }

    public ResultSet Tranactions(User usr) throws SQLException {
        assert connection != null;

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE user='"+usr.getId()+"'";
        Statement command = connection.createStatement();

        return command.executeQuery(sql);
    }

    public static ResultSet getAll() throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME;
        Statement command = connection.createStatement();

        return command.executeQuery(sql);
    }

    public static void createNew(int user, String...args) throws SQLException {
        String sql = "INSERT INTO "+TABLE_NAME+"(payment,user,hra,da,medical,overtime,professionalTax,esi,deductionOther,additionOther) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement command = connection.prepareStatement(sql);
        command.setString(1, args[0]);
        command.setInt(2,user);
        command.setString(3, args[1]);
        command.setString(4, args[2]);
        command.setString(5, args[3]);
        command.setString(6, args[4]);
        command.setString(7, args[5]);
        command.setString(8, args[6]);
        command.setString(9, args[7]);
        command.setString(10, args[8]);

        command.executeUpdate();
    }

}
