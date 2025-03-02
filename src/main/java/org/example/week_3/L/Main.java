package org.example.week_3.L;

import java.io.IOException;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws IOException {

        int senderId = 1; // ID отправителя
        int receiverId = 2; // ID получателя
        double amount = 500.0; // Сумма перевода

        try (Connection conn = ConnectToDB.connectToMySQL()){
            System.out.println("Успешно подключилься к базе данных!");

            conn.setAutoCommit(false); // Отключаем авто-коммиты (Atomicity)
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // Высокий уровень изоляции (Isolation)

            // Проверяем баланс отправителя
            PreparedStatement checkBalance = conn.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            checkBalance.setInt(1, senderId);
            ResultSet rs = checkBalance.executeQuery();
            if (!rs.next() || rs.getDouble("balance") < amount) {
                throw new SQLException("Недостаточно средств!");
            }

            // Списываем деньги у отправителя
            PreparedStatement withdraw = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
            withdraw.setDouble(1, amount);
            withdraw.setInt(2, senderId);
            withdraw.executeUpdate();

            // Зачисляем деньги получателю
            PreparedStatement deposit = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
            deposit.setDouble(1, amount);
            deposit.setInt(2, receiverId);
            deposit.executeUpdate();

            conn.commit(); // Подтверждаем транзакцию (Durability)
            System.out.println("Перевод выполнен успешно!");

        }catch (SQLException e){
            e.printStackTrace();
            try {
                Connection conn = ConnectToDB.connectToMySQL();
                conn.rollback(); // Откат транзакции, если произошла ошибка
                System.out.println("Транзакция откатилась!");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }
}
