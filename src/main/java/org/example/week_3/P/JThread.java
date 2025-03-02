package org.example.week_3.P;

public class JThread extends Thread{

    JThread (String name){
        super(name);
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s fiished... \n", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        JThread t = new JThread("JThread Bekarys");
        t.start();

    }
}
