package org.example.week_3.P.task;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

class Waiter extends Thread {

    private int id;
    private final BlockingQueue<Order> queue;
    private final Random random = new Random();
    private static int orderCounter = 1; // Счетчик заказов

    public Waiter(int id_waiter, BlockingQueue<Order> queue) {
        this.id = id_waiter;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {

                // Генерируем случайное блюдо
                String[] dishes = {"Пицца", "Бургер", "Суши", "Паста", "Стейк"};
                String dishName = dishes[random.nextInt(dishes.length)];
                int cookingTime = (random.nextInt(3) + 2) * 2000; // от 4 до 6 секунд

                // Создаем заказ
                Order order = new Order(orderCounter++, dishName, cookingTime);

                queue.put(order); // Кладем заказ в очередь
                System.out.println("📝 Официант " + id + " ----------> №" + order.getId() +
                                " (" + dishName + ") ");

                Thread.sleep(4000); // Ждем 4 секунды перед новым заказом
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
