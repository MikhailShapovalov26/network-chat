package ru.netology;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


import ru.netology.logger.Logger;

import static ru.netology.ServerNetworkChat.getCountUsers;
import static ru.netology.ServerNetworkChat.users;

public class UserChat extends Thread {

    private String name;
    private static Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Logger logger;
    private static UserDaoImplementation userDao = null;
    private static User newUser = null;

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
            newUser = new User(this.name, this.socket.getInetAddress().toString());

            userDao = new UserDaoImplementation();
            try {
                newUser.setUserId(userDao.add(newUser));
            } catch (SQLIntegrityConstraintViolationException e) {
                logger.error("Дубликат логина");
                send("Дубликат логина");
                socket.close();
            }
            if (getCountUsers() > 1) {
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
                    MessageAddDatabase msd = new MessageAddDatabase();
                    msd.setUserId(userDao.getUserId(this.name));
                    msd.setMessage(message);
                    msd.addNewMessageDB();

                    logger.info("Пользователь " + this.name
                            + " отправил новое сообщение в чат: " +
                            message);

                }
            } catch (NullPointerException e) {
                logger.error("Пустое сообщение не допускается " + e.getMessage());
            }
        } catch (IOException e) {
            logger.error("Ошибка: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("Ошибка: " + e.getMessage());
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

    public String getUserName() {
        return this.name;
    }

    public static void closeUserSocket() throws IOException {
        socket.close();
    }
}
