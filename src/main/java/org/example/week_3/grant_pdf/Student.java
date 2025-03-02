package org.example.week_3.grant_pdf;

public class Student {

    int idNumber;
    String tjkNumber;
    String name;
    int scoreENT;
    String numberJOO;

    public Student() {}

    public Student(int idNumber, String tjkNumber, String name, int scoreENT, String numberJOO) {
        this.idNumber = idNumber;
        this.tjkNumber = tjkNumber;
        this.name = name;
        this.scoreENT = scoreENT;
        this.numberJOO = numberJOO;
    }

    public int getIdNumber() { return idNumber; }

    public String getTjkNumber() { return tjkNumber; }

    public String getName() { return name; }

    public int getScoreENT() { return scoreENT; }

    public String getNumberJOO() { return numberJOO; }

    @Override
    public String toString() {
        return "Student{" +
                "idNumber=" + idNumber +
                ", tjkNumber='" + tjkNumber + '\'' +
                ", name='" + name + '\'' +
                ", scoreENT=" + scoreENT +
                ", numberJOO='" + numberJOO + '\'' +
                '}';
    }
}
