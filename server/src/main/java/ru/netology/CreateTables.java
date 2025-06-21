package ru.netology;

import java.sql.*;

public class CreateTables {

    static final String CREATE_TABLE_USER = "CREATE TABLE  IF NOT EXISTS users (\n" +
                                    "userId INT NOT NULL AUTO_INCREMENT, \n" +
                                    "name varchar(255) NOT NULL, \n" +
                                    "networkAddress varchar(255),\n" +
                                    "PRIMARY KEY (UserId),\n" +
                                    "UNIQUE (name)\n" +
                                    ")ENGINE=INNODB;";

    static final String CREATE_TABLE_CHAT="CREATE TABLE IF NOT EXISTS chats (\n" +
                                            "messageId  INT NOT NULL AUTO_INCREMENT,\n"+
                                            "message text NOT NULL, \n"+
                                            "userID INT NOT NULL,\n" +
                                            "PRIMARY KEY (messageId),\n"+
                                            "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE"+
                                            ")ENGINE=INNODB;";

    public void createTable(String query, Connection connection) throws ClassNotFoundException, SQLException {
        try(Statement stmt = connection.createStatement();){
            stmt.execute(query);
        }catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
            throw e;
        }
    }

    public void setCreateTableUser(Connection conn) throws ClassNotFoundException, SQLException {
        createTable(CREATE_TABLE_USER, conn);
    }

    public void setCreateTableChat(Connection conn) throws ClassNotFoundException, SQLException {
        createTable(CREATE_TABLE_CHAT, conn);
    }

}
