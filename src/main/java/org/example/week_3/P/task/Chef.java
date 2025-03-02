package org.example.week_3.P.task;

import java.util.concurrent.BlockingQueue;

class Chef extends Thread {
    private int id;
    private final BlockingQueue<Order> queue;

    public Chef(int id, BlockingQueue<Order> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = queue.take(); // Берем заказ из очереди
                Thread.sleep(order.getCookingTime()); // Готовим
                System.out.println("👨‍🍳 Повар " + id + " --> №" + order.getId() +
                        " (" + order.getDishName() + ")");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}