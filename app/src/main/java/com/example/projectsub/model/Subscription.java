package com.example.projectsub.model;

import java.util.Calendar;
import java.util.Date;

public class Subscription {

    private String name;
    private String nextPay;
    private String amount;
    private String uuid;
    private String frequency;
    private String finishDate;
    private String description;

    private int color;

    public Subscription (String name, String nextPay, String amount, int color, String uuid, String frequency, String finishDate, String description) {
        this.name = name;
        this.nextPay = nextPay;
        this.amount = amount;
        this.color = color;
        this.uuid = uuid;
        this.frequency = frequency;
        this.finishDate = finishDate;
        this.description = description;
    }

    public Subscription() { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

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
