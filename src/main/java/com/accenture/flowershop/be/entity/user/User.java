package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

public class User extends AbstractEntity {

    private String name;

    private String secondName  ;

    private String fatherName;

    private String address;

    private String telephone;

    private double balance;

    private short discount;

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDiscount(short discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public double getBalance() {
        return balance;
    }

    public short getDiscount() {
        return discount;
    }
}
