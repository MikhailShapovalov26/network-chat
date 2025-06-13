package ru.netology;

import ru.netology.configureApp.ConfigureApp;
import ru.netology.logger.Logger;

import javax.print.attribute.standard.NumberUp;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerNetworkChat {
    public static Set<UserChat> users = Collections.synchronizedSet(new HashSet<>());
    private static ServerSocket serverSocket;
    private static Logger logger;

    public static void StartServerChat(int port, String inetAddress, Logger logger) throws IOException {
        serverSocket = new ServerSocket(port, 500, InetAddress.getByName(inetAddress));
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

    public static void main(String[] args) throws IOException {

        ConfigureApp configureApp = new ConfigureApp("settings.file");
        int port = configureApp.readConfigure("server.port") !=null?
                Integer.parseInt(configureApp.readConfigure("server.port"))
                :4444;
        String host =  configureApp.readConfigure("server.host")!=null?
                configureApp.readConfigure("server.host")
                :"localhost";

        String pathLogger=configureApp.readConfigure("server.path.logger")!=null?
                configureApp.readConfigure("server.path.logger")
                : "/dev/null";
        logger = new Logger(pathLogger);
        logger.info("Конфиг файл найден");


        StartServerChat(port, host, logger);

    }

    public static void broadcastMessage(String sender, String text) {
        synchronized (users) {
            users.forEach(user -> {
                try {
                    if (!user.getUserName().equals(sender)) {
                        user.send(text);
                    }
                }catch (NullPointerException e){
                    logger.error("В данный момент нет собеседников "
                            + e.getMessage());
                }
            });
        }
    }

    public static void removeUser(UserChat userChat) {
       users.remove(userChat);
    }

}
