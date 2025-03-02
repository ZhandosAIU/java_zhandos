-- Проверяем, существует ли база данных, и создаем только если её нет
CREATE DATABASE IF NOT EXISTS grant_list;
USE grant_list;

-- Создаем таблицу специальностей, если её нет
CREATE TABLE IF NOT EXISTS specializations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_spec VARCHAR(50) UNIQUE NOT NULL,
    description TEXT NOT NULL
);

-- Создаем таблицу студентов, если её нет
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idNumber INT NOT NULL,
    tjkNumber VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    scoreENT INT NOT NULL,
    numberJOO VARCHAR(50) NOT NULL,
    id_special INT,
    FOREIGN KEY (id_special) REFERENCES specializations(id) ON DELETE SET NULL
);
