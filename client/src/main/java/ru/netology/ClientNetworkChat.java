package ru.netology;

import ru.netology.logger.Logger;
import ru.netology.configureApp.ConfigureApp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.UUID;


public class ClientNetworkChat {
    public static Logger logger;

    public static void main(String[] args) throws IOException {
        ConfigureApp configureApp = new ConfigureApp("settings.file");
        int port = Integer.parseInt(configureApp.readConfigure("server.port"));
        String host = configureApp.readConfigure("server.host");

        String pathLogger = String.valueOf(Paths.get(configureApp.readConfigure("server.path.logger")).getParent());
        String pathLog = pathLogger + "/client-" + UUID.randomUUID().toString().substring(0, 8);
        System.out.printf("Логирование будет произведено в файл %s " +
                "Можно открыть в терминале `tail -f %s` \n", pathLog, pathLog); // использую UUID чтоб на 1 сервере несколько клиентов могло работать

        logger = new Logger(pathLog);
        logger.info("Подключение к серверу " + host + " на порту " + port);

        startDeliverySend(host, port);
        logger.info("Начинаем чатиться!!!");

    }
    public static void startDeliverySend(String host, int port) throws IOException {
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        ClientSocket clientSocket = new ClientSocket(new Socket(host,port));
        System.out.printf("| ");
        String name = sysin.readLine();
        clientSocket.sendMessage(name);

        String message;
        while (!(message = sysin.readLine()).equals("exit")){

            logger.info("Новое сообщение готово для отправки " + message);
            clientSocket.sendMessage(message);
            System.out.printf("| ");
        }
        clientSocket.closeSession();

    }

}
