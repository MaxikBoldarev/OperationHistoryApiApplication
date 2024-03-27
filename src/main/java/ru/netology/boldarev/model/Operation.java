package ru.netology.boldarev.model;

import java.io.Serializable;

public class Operation implements Serializable {

    private double amount;
    private int date;

    private long id;

    public Operation(long id, double amount, int date) {
        this.id = id;
        this.amount = amount;
        if (date < 1 || date > 30) {
            this.date = 1;
        } else {
            this.date = date;
        }
    }

    @Override
    public String toString() {
        return "Operation{" +
                "amount=" + amount +
                ", date=" + date +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getDate() {
        return date;
    }
}
