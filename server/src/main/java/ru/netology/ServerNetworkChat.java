package ru.netology;

import ru.netology.configureApp.ConfigureApp;
import ru.netology.logger.Logger;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ServerNetworkChat {
    public static Set<UserChat> users = Collections.synchronizedSet(new HashSet<>());
    private static ServerSocket serverSocket;
    private static Logger logger;
    private static final int DEFAULT_PORT = 5555;
    private static final String DEFAULT_HOST= "localhost";



    public static void startServerChat(int port, String inetAddress, Logger logger) throws IOException {
        try {
            serverSocket = new ServerSocket(port, 500, InetAddress.getByName(inetAddress));
            logger.info("Сервер будет запущен на порту: " + port + " и на хосту "+ InetAddress.getByName(inetAddress));
            System.out.println("Сервер будет запущен на порту: " + port + " и на хосту "+ InetAddress.getByName(inetAddress));
        }catch (BindException be){
            System.out.println("Адрес уже используется " + be.getMessage());
            logger.error("Адрес уже используется " + be.getMessage());
            serverSocket = new ServerSocket(DEFAULT_PORT, 500, InetAddress.getByName(inetAddress));
            logger.info("Сервер будет запущен на порту: " + DEFAULT_PORT + " и на хосту "+ InetAddress.getByName(inetAddress));
            System.out.println("Сервер будет запущен на порту: " + DEFAULT_PORT + " и на хосту "+ InetAddress.getByName(inetAddress));
        }
        while (true) {
            Socket newUserSocket = serverSocket.accept();
            logger.info(
                    "[INFO] Новое подключение\n"
                            + "[INFO] IP: " + newUserSocket.getInetAddress()
            );
            users.add(
                    new UserChat(newUserSocket, logger)
            );
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        ConfigureApp configureApp = new ConfigureApp("settings.file");
        int port = configureApp.readConfigure("server.port") !=null?
                Integer.parseInt(configureApp.readConfigure("server.port"))
                :DEFAULT_PORT;
        String host =  configureApp.readConfigure("server.host")!=null?
                configureApp.readConfigure("server.host")
                :DEFAULT_HOST;

        String pathLogger=configureApp.readConfigure("server.path.logger")!=null?
                configureApp.readConfigure("server.path.logger")
                : "/dev/null";
        logger = new Logger(pathLogger);
        logger.info("Конфиг файл найден");
        // Create table for User and save Message  chat
        CreateTables createTables = new CreateTables();
        Connection connection = DatabaseConnection.getConnection();
        createTables.setCreateTableUser(connection);
        createTables.setCreateTableChat(connection);
        new ServerJFrame().startJFrameServer();
        startServerChat(port, host, logger);
    }

    public static void broadcastMessage(String sender, String text) {
        if(users == null || text == null){
            logger.error("Некорректные параметры для рассылки");
            return;
        }
        synchronized (users) {
            users.forEach(user -> {
                try {
                    if (!user.getUserName().equals(sender)) {
                        user.send(text);
                    }
                }catch (NullPointerException npe){
                    logger.error(npe.getMessage());
                }
            });
        }
    }

    public static void removeUser(UserChat userChat) {
       users.remove(userChat);
    }
    public static void serverNetworkChatStop() throws IOException {
        serverSocket.close();
    }
    public static int getCountUsers(){
        return users.size();
    }

}
