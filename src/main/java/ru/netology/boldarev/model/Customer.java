package ru.netology.boldarev.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private long id;
    private String name;
    private int age;


    public Customer(long id, String name, int age) {
        this.id = id;
        this.name = name;
        if (age < 18 || age > 80) {
            this.age = 18;
        } else {
            this.age = age;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
