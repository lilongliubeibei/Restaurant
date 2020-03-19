package com.example.yiliedurestaurant.model;

import java.util.List;

public class Bean_MomentList {
    private  Info info;
    private  List<Moments> momentList;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Moments> getMomentList() {
        return momentList;
    }

    public void setMomentList(List<Moments> momentList) {
        this.momentList = momentList;
    }
}
