package ru.netology.configureApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigureApp {
    private String fileName;

    public ConfigureApp(String fileName) {
        this.fileName = fileName;
    }

    public String readConfigure(String nameParameter) {

        Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream(fileName)) {
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties.getProperty(nameParameter);
    }
}
