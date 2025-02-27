package com.example.fastfood.model;

import java.io.Serializable;

public class Order implements Serializable {
    private String name;
    private  String phone;
    private String address;
    private String total;

    public Order() {
    }

    public Order(String name, String phone, String address, String total) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
