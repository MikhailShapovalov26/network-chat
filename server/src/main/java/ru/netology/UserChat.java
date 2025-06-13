package ru.netology;

import java.io.*;
import java.net.Socket;

import ru.netology.logger.Logger;

public class UserChat extends Thread {

    private String name;
    private Socket socket;
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
                    out, in, logger
            ).register();

            ServerNetworkChat.broadcastMessage(this.name,
                    "[INFO] К нам присоединился " + this.name + "\n" + " Поприветствуем его!");
            logger.info("К нам присоединился"  + this.name + "\n");

            String message;
            while (!(message = this.in.readLine()).equals("exit")) {
                if(message ==null){
                    this.out.write("[ERROR] пустые сообщения не допускаются");
                    this.out.flush();
                    continue;
                }
                ServerNetworkChat.broadcastMessage(this.name, this.name + ": " + message);
                logger.info("Пользователь " + this.name
                        + " отправил новое сообщение в чат: " +
                        message);

            }
        } catch (IOException e) {
            logger.error("Ошибка: " + e.getMessage());
        }
        finally {
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
}
