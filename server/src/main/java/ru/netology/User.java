package ru.netology;

import java.net.InetAddress;

public class User {
    private int UserId;
    private String name;
    private String ipAddress;

    public User(){}

    public User(String name, String ipAddress){
        this.name = name;
        this.ipAddress = ipAddress;
    }



    public int getUserId() {
        return UserId;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "User:" + name +
                "id пользователя=" + UserId +
                ", Имя ='" + name + '\'' +
                ", IP адрес подключения ='" + ipAddress + '\'' +
                '}';
    }
}
