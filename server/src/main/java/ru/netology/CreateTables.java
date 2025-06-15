package ru.netology;

import java.sql.*;

public class CreateTables {

    static final String CREATE_TABLE_USER = "CREATE TABLE  IF NOT EXISTS users (\n" +
                                    "UserId INT NOT NULL AUTO_INCREMENT, \n" +
                                     "name varchar(255) NOT NULL, \n" +
                                    " IPAddress varchar(255),\n" +
                                    " PRIMARY KEY (UserId)\n" +
                                    " )ENGINE=INNODB;";

    static final String CREATE_TABLE_CHAT="CREATE TABLE IF NOT EXISTS chats (\n" +
                                            "MessageId  INT NOT NULL AUTO_INCREMENT,\n"+
                                            "Message text NOT NULL, \n"+
                                            "UserID INT NOT NULL,\n" +
                                            "PRIMARY KEY (MessageId),\n"+
                                            "FOREIGN KEY (UserId) REFERENCES users(UserId) ON DELETE CASCADE"+
                                            " )ENGINE=INNODB;";

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
