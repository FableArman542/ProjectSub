package com.example.projectsub.model;

import android.graphics.Color;

public class Subscription {

    private String name;
    private String nextPay;
    private String amount;

    private int color;

    public Subscription (String name, String nextPay, String amount, int color) {
        this.name = name;
        this.nextPay = nextPay;
        this.amount = amount;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextPay() {
        return nextPay;
    }

    public void setNextPay(String nextPay) {
        this.nextPay = nextPay;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
