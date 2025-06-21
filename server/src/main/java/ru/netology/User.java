package ru.netology;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String name;
    private String ipAddress;

    public User(){}

    public User(String name, String ipAddress){
        this.name = name;
        this.ipAddress = ipAddress;
    }



    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }


    @Override
    public String toString() {
        return "User:" + name +
                "id пользователя=" + userId +
                ", Имя ='" + name + '\'' +
                ", IP адрес подключения ='" + ipAddress + '\'' +
                '}';
    }
}
