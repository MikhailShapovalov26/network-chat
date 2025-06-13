package ru.netology;

import ru.netology.logger.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RegistryUsers {

    private BufferedWriter out;
    private BufferedReader in;
    private Logger logger;

    public RegistryUsers(BufferedWriter out,
                         BufferedReader in,
                         Logger logger) {
        this.out = out;
        this.in = in;
        this.logger = logger;
    }

    public String register() throws IOException {

        this.out.write("[AUTH] Введите ваше имя: \n");
        this.out.flush();

        String name;
        while ((name = in.readLine()) != null) {
            name = name.trim();
            if (name.isEmpty()) {
                this.out.write("[AUTH] Ошибка: имя не может быть пустым!\n");
                this.logger.error("Ошибка: имя не может быть пустым!");
                this.out.flush();
                continue;
            }


            this.out.write("[AUTH] Успешно! Добро пожаловать, " + name + "!\n");
            this.out.flush();
            this.logger.info("[AUTH] Успешно! " + name);
            this.logger.info("Пользователь " + name + " авторизовался.");
            return name;
        }
        throw new IOException("Соединение закрыто");
    }
}
