package ru.netology;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

import ru.netology.logger.Logger;

import static ru.netology.ServerNetworkChat.getCountUsers;

public class UserChat extends Thread {

    private String name;
    private static Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Logger logger;

    public UserChat(Socket socket, Logger logger) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.logger = logger;
        this.start();
    }
    @Override
    public void run() {
        logger.info("Новый поток для пользователя с IP: " + this.socket.getInetAddress());

        try {
            this.name = new RegistryUsers(
                    out, in, logger, socket
            ).register();
            User newUser = new User(this.name,this.socket.getInetAddress().toString());
            System.out.println("ТУТ " + newUser.getName() + newUser.getIpAddress());

            UserDaoImplementation userDao = new UserDaoImplementation();
            userDao.add(newUser);
            if(getCountUsers()>1) {
                ServerNetworkChat.broadcastMessage(this.name,
                        "[INFO] К нам присоединился " + this.name + "\n" + " Поприветствуем его!");
                logger.info("К нам присоединился " + this.name + "\n");
            }

            String message;
            try {
                while (!(message = this.in.readLine()).equals("exit")) {
                    if (message == null) {
                        this.out.write("[ERROR] пустые сообщения не допускаются");
                        this.out.flush();
                        continue;
                    }
                    ServerNetworkChat.broadcastMessage(this.name, this.name + ": " + message);
                    logger.info("Пользователь " + this.name
                            + " отправил новое сообщение в чат: " +
                            message);

                }
            }catch (NullPointerException e){
                logger.error("Пустое сообщение не допускается " + e.getMessage());
            }
        } catch (IOException e) {
            logger.error("Ошибка: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ServerNetworkChat.removeUser(this);
        }
    }

    void send(String s) {
        try {
            this.out.write(s + "\n");
            this.out.flush();
        } catch (IOException e) {
            logger.error("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    public String getUserName(){
        return this.name;
    }
    public static void closeUserSocket() throws IOException {
        socket.close();
    }
}
