package ru.netology.configureApp;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConfigureAppTest {

    @Test
    void readConfigure() throws IOException {
        ConfigureApp configureApp = new ConfigureApp(getClass().getClassLoader().getResource("test.file").getFile());
        String testResult = configureApp.readConfigure("server.name");
        assertEquals("test", testResult);
    }
}