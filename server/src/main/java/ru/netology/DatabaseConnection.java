package ru.netology;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection conn = null;
    static {
        final String DB_URL = "jdbc:mysql://localhost:3306/socket";
        final String USER = "user";
        final String PASS = "password";
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

}
