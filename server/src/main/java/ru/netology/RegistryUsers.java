package ru.netology;

import ru.netology.logger.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;


public class RegistryUsers {

    private BufferedWriter out;
    private BufferedReader in;
    private Logger logger;
    private Socket socket;

    public RegistryUsers(BufferedWriter out,
                         BufferedReader in,
                         Logger logger, Socket socket) {
        this.out = out;
        this.in = in;
        this.logger = logger;
    }

    public String register() throws IOException {
        try {
            sendAuthRequest("[AUTH] Введите ваше имя: \n");

            String name = in.readLine();
            if (name == null) {
                throw new IOException("Клиент отключился");
            }

            name = name.trim();
            if (name.isEmpty()) {
                sendAuthRequest("[AUTH] Ошибка: имя не может быть пустым!\n");
                throw new IOException("Пустое имя");
            }

            sendAuthRequest("[AUTH] Успешно! Добро пожаловать, " + name + "!\n");
            logger.info("[AUTH] Успешно! :"  + name);
            return name;

        } catch (IOException e) {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException ignored) {}

            throw e;
        }
    }


    public void sendAuthRequest(String message) throws IOException {
        this.out.write(message);
        this.out.flush();
    }


}
