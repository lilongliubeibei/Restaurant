package com.example.yiliedurestaurant.base;

public class PrensenterBase<T> {
    protected T activityBase;

    public void setActivityBase( T activityBase) {
        this.activityBase = activityBase;
    }
    public void release(){
        activityBase=null;
    }
}
