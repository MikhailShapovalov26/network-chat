package ru.netology.logger;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.*;


class LoggerTest {
    String fileToTest = "./log.test";
    File fileLog = new File(fileToTest);

    @Test
    void writeToLogFile() throws IOException {
        Logger logger = new Logger(fileToTest);
        String testMessage = "Тестовое сообщение для проверки работы метода writeToLogFile";
        logger.info(testMessage);
        logger.error(testMessage);

        BufferedReader br = new BufferedReader(new FileReader(fileLog));
        String st;
        while ((st = br.readLine()) !=null){
            assertThat(st, containsString(testMessage));
            System.out.println(st);
        }


    }
    @AfterEach
    void deleteFile(){
        fileLog.delete();
    }
}