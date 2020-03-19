package com.example.yiliedurestaurant.model;

import java.util.List;

public class Bean_Orders {
    private  List<Orders> orders;
    private Info info;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
