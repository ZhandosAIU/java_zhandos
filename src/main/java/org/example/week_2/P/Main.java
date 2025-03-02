package org.example.week_2.P;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PATH_TO_FILE = "/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/week_2/P/"; // Абсолютный путь
    private static final String FILE_NAME = "memory.txt"; // Название файла

    public static void main(String[] args) throws IOException {
        start(); // старт
    }

    private static void start() throws IOException {
        Scanner sc = new Scanner(System.in);
        List<User> users = getUsersList();

        while (true){
            printMenu();

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1 -> {
                    System.out.print("Введите ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Введите логин: ");
                    String login = sc.nextLine();

                    System.out.print("Введите пароль: ");
                    String password = sc.nextLine();

                    users.add(new User(id, login, password));
                    System.out.println("Пользователь добален!");
                }
                case 2 -> {
                    System.out.println("\nСписок ползователи:");
                    if (users.isEmpty()){
                        System.out.println("Список пустой!");
                    }else {
                        for (User user: users){
                            System.out.println(user.toString());
                        }
                    }
                }
                case 3 -> {
                    System.out.println("\nВведите ID чтобы удалить:");
                    int id = sc.nextInt();
                    users.removeIf(user -> user.getId() == id);
                    saveUsersList(users);
                    System.out.println("Пользователь удален!");
                }
                case 4 -> {
                    System.out.println("Снова увидимся!");
                    return;
                }
                default -> System.out.println("Нет такой вариант. Выберите снова!");
            }
        }
    }

    private static void printMenu(){
        System.out.println("\nMENU:");
        System.out.println("PRESS [1] TO ADD USERS");
        System.out.println("PRESS [2] TO LIST USERS");
        System.out.println("PRESS [3] TO DELETE USERS");
        System.out.println("PRESS [4] TO EXIT");
        System.out.print("Выберите кнопку: ");
    }

    public static List<User> getUsersList() throws IOException { // Сигнатура
        List<User> users = new ArrayList<>();
        File file = openFileOrCreate(PATH_TO_FILE, FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.isEmpty()){
                    continue;
                }
                users.add(User.fromText(line));
            }
            return users;
        }
    }

    public static void saveUsersList(List<User> users) throws IOException {
        File file = openFileOrCreate(PATH_TO_FILE, FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for (User user: users){
                writer.write(user.toText());
                writer.newLine();
            }
        }
    }

    public static File openFileOrCreate(String filePath, String fileName) throws IOException {
        File file = new File(filePath + fileName);
        if (!file.exists()){
            file.createNewFile();
            System.out.println("Новый файл создан!");
        }
        return file;
    }

}
