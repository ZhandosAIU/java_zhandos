package org.example.week_3.P.task;

public class Order {
    int id;
    String dishName;
    int cookingTime;

    // Constructor
    public Order(int id, String dishName, int cookingTime) {
        this.id = id;
        this.dishName = dishName;
        this.cookingTime = cookingTime;
    }

    public String toString(){
        return String.format("Заказ №%-3d | 🍽 %s | ⏳ %d мс", this.id, this.dishName, this.cookingTime);
    }


    // Getters
    public int getId() { return id; }

    public String getDishName() { return dishName; }

    public int getCookingTime() { return cookingTime; }

}
