package org.example.week_3.P;

public class WorkWithThread {
    public static void main(String[] args) {
        // Thread t = Thread.currentThread(); // Получаем главный поток
        // System.out.println(t.getName()); // main

        Runnable task1 = () -> {
            for (int i = 1; i <= 5; i++){
                System.out.println(i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 10; i <= 14; i++){
                System.out.println(i);
            }
        };

        Thread sveta = new Thread(task1);
        Thread misha = new Thread(task2);
        sveta.start();
        misha.start();

    }
}
