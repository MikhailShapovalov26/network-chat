package ru.netology.logger;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Logger {
    static StringBuilder messageLog = new StringBuilder();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    LocalDateTime localDateTime = timestamp.toLocalDateTime();

    private String nameFile;

    public Logger(String nameFile){
        this.nameFile = nameFile;
    }

    public void error(String message){
        messageLog.append(localDateTime).append(" [ERROR] - ")
                .append(message).append("\n");;
        writeToLogFile(messageLog.toString());
        messageLog.setLength(0);
    }

    public void info(String message){
        messageLog.append(localDateTime).append(" [INFO] - ")
                .append(message).append("\n");
        writeToLogFile(messageLog.toString());
        messageLog.setLength(0);
    }
    public void writeToLogFile(String message){
        Path fileToLog = Paths.get(this.nameFile);
        Path parentDir = fileToLog.getParent();

        if(parentDir != null && !Files.exists(parentDir)){
            System.out.println("Файл для записи лога не обнаружен, требуется выбрать существующий каталог");
            return;
        }
        try( BufferedWriter writer = Files.newBufferedWriter(fileToLog,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(message);
        } catch (IOException e) {
            System.out.println("Файл для записи не обнаружен" + e.getMessage());
        }

    }


}
