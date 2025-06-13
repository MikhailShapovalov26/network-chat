package ru.netology;


import ru.netology.logger.Logger;

public class ClientSession extends Thread{

    private ClientSocket clientSocket;
    private boolean stop = false;
    private Logger logger = ClientNetworkChat.logger;
    private boolean isAuthenticated = false;

    public ClientSession(ClientSocket clientSocket){
        this.clientSocket = clientSocket;
        this.start();
    }

    public void run(){
        String message = "";
        while (!this.stop){
            message = this.clientSocket.getMessage();
            if(message.startsWith("[AUTH]")){
                System.out.println("\r" + message + "\n| ");
            }else if(isAuthenticated){
                System.out.print("\r" + message + "\n| ");
            }
            System.out.flush();
            if(message == null) {
                logger.info("Сервер закрыл чат!");
                break;
            }
            logger.info("Получено новое сообщение " + message);

            System.out.print(message + "\n");

        }

    }
    public void stopSession(){
        logger.info("Закрываем чат, ввод команды на выход");
        this.stop = true;
    }
    public void setAuthenticated(boolean auth) {
        this.isAuthenticated = auth;
    }
}
