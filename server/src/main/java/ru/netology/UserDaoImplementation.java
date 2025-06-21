package ru.netology;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplementation implements UserDao{

    static Connection connection
            = DatabaseConnection.getConnection();


    @Override
    public int add(User user) throws SQLException {
        int id = 0;
        String query =
                "insert into users(" +
                        "name," +
                        "networkAddress" +
                        ")" +
                        "VALUES (?,?)";

        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getIpAddress());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            id = rs.getInt(1);
        }
        return id;
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "delete from users where userId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    @Override
    public User getUser(int id) throws SQLException {
        String query = "select * from users where userId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setIpAddress(rs.getString("networkAddress"));
            user.setUserId(rs.getInt("userId"));
            return user;
        }
        return null;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        String query = "select * from users";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs =ps.executeQuery();
        List<User> users = new ArrayList<>();

        while (rs.next()){
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setName(rs.getString("name"));
            user.setIpAddress(rs.getString("networkAddress"));
            users.add(user);
        }
        return users;
    }

    @Override
    public void update(User user) throws SQLException {
        String query =
                "update users set name=?," +
                        " networkAddress= ? where userId=? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setString(2, user.getIpAddress());
        ps.setInt(3, user.getUserId());
        ps.executeUpdate();

    }

    public int getUserId(String name) throws SQLException {
        int id = 0;
        String query = "select * from users where name = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt(1);
        }
        return id;

    }

}
