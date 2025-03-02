package org.example.week_4.P;

public class Main {
    private int amount;

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.working();
        System.out.println(main.amount);
    }

    public void working() throws InterruptedException {
        Thread thread1 = new Thread(new Runn());
        Thread thread2 = new Thread(new Runn());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public synchronized void increment(){
        amount++;
    }

    class Runn implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 230; i++) {
                increment();
            }
        }
    }

}
