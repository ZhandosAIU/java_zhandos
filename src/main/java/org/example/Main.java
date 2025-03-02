package org.example;

import java.awt.desktop.ScreenSleepEvent;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String text = "Hello, World!\n";
        try (FileOutputStream file = new FileOutputStream("output.txt", false)){
            byte[] buffer = text.getBytes();
            file.write(buffer, 0, buffer.length);
            file.write(buffer[1]);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        try (FileInputStream f = new FileInputStream("output.txt")){
            int i;
            while ((i = f.read()) != 1){
                System.out.println((char)i);
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
