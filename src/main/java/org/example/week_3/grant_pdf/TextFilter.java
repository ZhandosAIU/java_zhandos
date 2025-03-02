package org.example.week_3.grant_pdf;

import com.sun.istack.NotNull;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFilter {

    public static void main(String[] args) throws IOException {
        run(); // Запуск кода
    }

    public static void run() throws IOException {

        // Путь к директорию где расположен файл с исходным результатом где уже из pdf преобразован к текст
        String pathToDirectory = "/home/bekarys/IdeaProjects/WorkWord/src/main/java/org/example/week_3/grant_pdf/";
        // Название файла
        String fileName = "green.txt";

        // Файл где уже есть данные из pdf в виде текст
        // И код преобразования pdf -> txt в файле Main
        File file = new File(pathToDirectory, fileName); //

        // Проверят есть ли файл с названием green.txt
        if (!file.exists()){
            System.out.println("File Not Found");
            System.exit(0);
        }else {
            // try-catch-resources чтение файла
            try (FileReader fr = new FileReader(file);
                 BufferedReader br = new BufferedReader(fr)){

                // паттерн разделитель по дисциплину
                Pattern pattern = Pattern.compile("^[B6][B\\d]\\d{2} - [\\p{L}, ]+$");

                // Ключ-значение ---> ID дисциплина-ее расшифрока  (B001-Психология)
                Map<String, String> disciplines = new HashMap<>();

                // Ключ-значение ID дисциплина-данные или резултаты
                Map<String, String> data = new HashMap<>();
                String id_dis = ""; //  уникальный id дисциплина

                String line; // Чтение строку в файле

                // Чтение из файла пока не дойти до конца
                while (!((line = br.readLine()) == null)){

                    // Если строка пустой (не null) то просто пропустит
                    if (line.isEmpty()){
                        continue;
                    }

                    // Проверка строку по шаблону или регулярному выражений
                    Matcher matcher = pattern.matcher(line);

                    // Проверка есть ли текст которой подходить к шаблону
                    if (matcher.matches()){

                        // Пример строку которой подходит
                        // --- B001 - Педагогика және психология ---
                        // Если такой есть то разделить на две части
                        String[] split = line.strip().split(" - ");

                        // Добавить в отдельный хранение ключ-значение
                        disciplines.put(split[0], split[1]);

                        // Обновление дисциплина (Уникальный номер)
                        id_dis = split[0];

                        // Пропуск
                        continue;
                    }
                    // Добавляет строку в значение по ключу дисциплина
                    if (!id_dis.isEmpty()){
                        data.put(id_dis, data.getOrDefault(id_dis, "") + (data.containsKey(id_dis) ? " " : "") + line.strip());
                    }
                }

                int id_spec = 0;

                try (Connection conn = DBConnect.connectToMySQL();
                     Statement stmt = conn.createStatement()) {

                    createDBAndTables(conn);

                    // Преобразовать все данные в класс Student
                    for (Map.Entry<String, String> entry: data.entrySet()){
                        List<String> res = textToSplitWithReg(entry.getValue());

                        // Добавление в база данных
                        if (!isSpecExists(entry.getKey(), conn)){
                            insertToSpec(entry.getKey(), disciplines.get(entry.getKey()), conn);
                            id_spec++;
                        }

                        for (String el: res){
                            Student s = textToStudent(el);
                            insertToStudent(s, id_spec, conn);
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public static List<String> textToSplitWithReg(String text){

        // Паттерн для извлечения данные из текста
        Pattern student = Pattern.compile("\\d{1,3} \\d{9} (\\p{L}+(?: \\p{L}+)*) \\d{1,3} \\d{3}");

        //
        Matcher matcher = student.matcher(text);

        // Результат которой возврашает эту массив
        List<String> result = new ArrayList<>();

        while (matcher.find()){
            result.add(matcher.group());
        }
        return result;
    }

    public static Student textToStudent(String text){
        try{
            String[] splitText = text.strip().split(" ");
            int size = splitText.length;
            int id_student = Integer.parseInt(splitText[0]);
            String number_tjk = splitText[1];
            int scoreENT = Integer.parseInt(splitText[(size - 2)]);
            String numberJOO = splitText[(size - 1)];
            String name = String.join(" ", Arrays.copyOfRange(splitText, 2, size - 2));

            return new Student(id_student, number_tjk, name, scoreENT, numberJOO);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Student();
        }

    }

    public static void createDBAndTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()){

            String dbName = "grant_list";

            // 1. Создаем базу данных (если её нет)
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("База данных проверена/создана!");

            // 2. Подключаемся к базе данных
            conn.setCatalog(dbName);

            // 3. Создаём таблицу specializations
            String createSpecializations = """
                CREATE TABLE IF NOT EXISTS specializations (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    id_spec VARCHAR(50) UNIQUE NOT NULL,
                    description TEXT NOT NULL
                )
                """;
            stmt.executeUpdate(createSpecializations);
            System.out.println("Таблица specializations создана!");

            // 4. Создаём таблицу students
            String createStudents = """
                CREATE TABLE IF NOT EXISTS students (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    idNumber INT NOT NULL,
                    tjkNumber VARCHAR(50) UNIQUE NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    scoreENT INT NOT NULL,
                    numberJOO VARCHAR(50) NOT NULL,
                    id_special INT,
                    FOREIGN KEY (id_special) REFERENCES specializations(id) ON DELETE SET NULL
                )
                """;
            stmt.executeUpdate(createStudents);
            System.out.println("Таблица students создана!");
        }
    }

    public static void insertToSpec(String id, String desc, Connection conn) throws SQLException {
        String insertSqlQuery = " INSERT INTO specializations (id_spec, description) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSqlQuery)){

            // Устанавливаем параметры запроса
            pstmt.setString(1, id);
            pstmt.setString(2, desc);

            // Выполняем запрос
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Данные успешно добавлены!");
            }else {
                System.out.println("Ошибка во время добавления информации!");
            }
        }
    }

    public static void insertToStudent(Student student, int id, Connection conn) throws SQLException {
        // SQL-запрос для вставки данных
        String insertQuery = "INSERT INTO students (idNumber, tjkNumber, name, scoreENT, numberJOO, id_special) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)){

            pstmt.setInt(1, student.getIdNumber());
            pstmt.setString(2, student.getTjkNumber());
            pstmt.setString(3, student.getName());
            pstmt.setInt(4, student.getScoreENT());
            pstmt.setString(5, student.getNumberJOO());
            pstmt.setInt(6, id);

            // Выполняем запрос
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Студент успешно добавлен!");
            }else{
                System.out.println("Студент либо уже существует, либо какой то ошибка!");
            }

        }
    }

    public static boolean isSpecExists(String id, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM specializations WHERE id_spec = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Если есть хотя бы одна запись, значит, специальность уже существует
                }
            }
        }
        return false;
    }

}