package ru.netology;

import ru.netology.logger.Logger;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;
    private ClientSession clientSession;
    private Logger logger = ClientNetworkChat.logger;

    public ClientSocket(Socket clientSocker) throws IOException {
        this.clientSocket = clientSocker;
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocker.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientSession = new ClientSession(this);

    }

    public void sendMessage(String message){
        if(clientSocket.isClosed())
        {
            logger.error("Сокет уже закрыт");
            return;
        }
        try {
            this.out.write(message);
            this.out.newLine();
            this.out.flush();
        } catch (Exception e) {
            logger.error("Ошибка отправки: " + e.getMessage());
        }
    }
    public String getMessage(){
        try{
            return this.in.readLine();
        } catch (IOException e) {
            logger.error("Ошибка получения: " + e.getMessage());
        }
        return "";
    }

    public void closeSession(){
        try {
            if (clientSession != null) {
                clientSession.stopSession();
            }
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            logger.info("Соединение закрыто");
        } catch (IOException e) {
            logger.error("Ошибка закрытия сокета: " + e.getMessage());
        }
    }

}
