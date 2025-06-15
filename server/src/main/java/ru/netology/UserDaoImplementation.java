package ru.netology;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImplementation implements UserDao{

    static Connection connection
            = DatabaseConnection.getConnection();


    @Override
    public int add(User user) throws SQLException {
        String query =
                "insert into users(" +
                        "name," +
                        "ipAddress" +
                        ")" +
                        "VALUES (?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setString(2, user.getIpAddress());
        int n = ps.executeUpdate();
        return n;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public User getUser(int id) throws SQLException {
        return null;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return List.of();
    }

    @Override
    public void update(User user) throws SQLException {

    }
}
