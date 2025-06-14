package ru.netology;


import ru.netology.logger.Logger;

public class ClientSession extends Thread{

    private ClientSocket clientSocket;
    private boolean stop = false;
    private Logger logger = ClientNetworkChat.logger;

    public ClientSession(ClientSocket clientSocket){
        this.clientSocket = clientSocket;
        this.start();
    }

    public void run(){
        String message = "";
        while (!this.stop){
            message = this.clientSocket.getMessage();
            try {
                if (message.startsWith("[AUTH]")) {
                    System.out.println("\r" + message + "\n| ");
                } else {
                    System.out.print("\r" + message + "\n| ");
                    logger.info("Получено новое сообщение " + message);
                }
            }catch (NullPointerException npe){
                logger.info("Закрываем чат, ввод команды на выход");
                this.stop = true;
                clientSocket.closeSession();
                System.exit(0);
            }
        }

    }
    public void stopSession(){
        logger.info("Закрываем чат, ввод команды на выход");
        this.stop = true;
    }
}
