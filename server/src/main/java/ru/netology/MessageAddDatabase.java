package ru.netology;

import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class MessageAddDatabase {

    private String message;
    private int userId;
    static Connection connection =
            DatabaseConnection.getConnection();

    public int addNewMessageDB() throws SQLException {
        String query = "insert into chats(" +
                "message, " +
                "userId " +
                ")" +
                "VALUES (?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, this.message);
        ps.setInt(2, this.userId);
        int n = ps.executeUpdate();
        return n;
    }

}
