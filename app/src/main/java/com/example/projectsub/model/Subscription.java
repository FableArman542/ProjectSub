package com.example.projectsub.model;

import java.util.Date;

public class Subscription {

    private String name;
    private Date nextPay;
    private String amount;
    private String uuid;
    private String frequency;

    private int color;

    public Subscription (String name, Date nextPay, String amount, int color, String uuid, String frequency) {
        this.name = name;
        this.nextPay = nextPay;
        this.amount = amount;
        this.color = color;
        this.uuid = uuid;
        this.frequency = frequency;
    }

    public Subscription() { }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getNextPay() {
        return nextPay;
    }

    public void setNextPay(Date nextPay) {
        this.nextPay = nextPay;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
