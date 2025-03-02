package org.example.week_4.L;

import java.net.InetAddress;

public class JavaNet {
    public static void main(String[] args) {
        String host = "www.aiu.kz"; // Можно заменить на любой IP или домен
        try {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("IP-адрес: " + address.getHostAddress());
            System.out.println("Имя хоста: " + address.getHostName());
            System.out.println("Доступен? " + address.isReachable(3000)); // Проверяем доступность (3 сек)
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}