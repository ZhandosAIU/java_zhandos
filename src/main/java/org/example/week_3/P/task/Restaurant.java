package org.example.week_3.P.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    public static void main(String[] args) {

        int timeWorkRestaurant = 20000;
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>(2); // Очередь заказов

        System.out.println("Ресторан работает " + Math.round((float)(timeWorkRestaurant) / 1000) + "с!");
        synchronized (queue){

            // Создаем официантов
            Waiter waiter1 = new Waiter(1, queue);
            Waiter waiter2 = new Waiter(2, queue);

            // Создаем поваров
            Chef chef1 = new Chef(1, queue);
            Chef chef2 = new Chef(2, queue);

            // Запускаем потоки
            waiter1.start();
            // waiter2.start();
            chef1.start();
            chef2.start();

            try{
                Thread.sleep(timeWorkRestaurant);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }


            // Останавливаем программу (не идеальный способ, но для примера подойдет)
            waiter1.interrupt();
            waiter2.interrupt();
            chef1.interrupt();
            chef2.interrupt();
            System.out.println("🏁 Ресторан закрывается!");

        }

    }
}