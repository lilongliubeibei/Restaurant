package com.example.yiliedurestaurant.model;

import java.util.List;

public class Bean_Food {
    private  List<Food> foodList;
    private  Info info;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}



