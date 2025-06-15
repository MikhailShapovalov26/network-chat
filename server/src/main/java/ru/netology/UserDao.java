package ru.netology;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    public int add(User user) throws SQLException;
    public void delete (int id) throws SQLException;
    public User getUser(int id) throws SQLException;
    public List<User> getUsers() throws SQLException;
    public void update(User user) throws SQLException;
}
