package org.example.week_2.P;

import java.math.BigInteger;

public class User {

    private int id;
    private String login;
    private String password;

    // Конструктор
    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    // Геттеры
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toText(){
        return this.id + "," + this.login + "," + this.password;
    }

    public static User fromText(String line){
        String[] parts = line.split(",");
        return new User(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }

}
