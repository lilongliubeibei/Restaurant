package com.example.yiliedurestaurant.model;

public class FoodOrderItem {
    private int foodId;
    private int count;

    public FoodOrderItem(int foodId, int count) {
        this.foodId = foodId;
        this.count = count;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}