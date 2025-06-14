package ru.netology;

import org.junit.Test;
import ru.netology.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;


public class ServerNetworkChatTest {
    private static final int TEST_PORT = 6666;
    private static final String TEST_HOST = "localhost";
    private static final String TEST_LOG = "/tmp/testLog";

    @Test
    public void startServerChat() throws IOException, InterruptedException {
        Logger logger = new Logger(TEST_LOG);
        CountDownLatch serverReady = new CountDownLatch(1);
        serverReady.countDown();
        Thread serverThread = new Thread();
        try {
             serverThread= new Thread(() -> {
                try {
                    ServerNetworkChat.StartServerChat(TEST_PORT, TEST_HOST, logger);
                } catch (IOException e) {
                    try {
                        ServerNetworkChat.serverNetworkChatStop();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    System.out.println(e.getMessage());
                }
            });

        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        serverThread.setDaemon(true);
        serverThread.start();

        try (Socket clientSocket = new Socket("localhost", 6666)) {
            assertTrue(clientSocket.isConnected(),
                    "Accepts connection when server in listening"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (Socket client = new Socket(TEST_HOST, TEST_PORT);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            String authPrompt =in.readLine();
            assertTrue(authPrompt.contains("AUTH"), "Expected auth request");

            out.println("TEST-USER");
            assertEquals("[AUTH] Успешно! Добро пожаловать, TEST-USER!", in.readLine());


        } finally {
            Thread.sleep(10000);
            ServerNetworkChat.serverNetworkChatStop();
        }


    }
}