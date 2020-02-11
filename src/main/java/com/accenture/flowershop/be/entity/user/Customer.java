package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "secondName", unique = true)
    private String secondName  ;

    @Column(name = "fatherName", unique = true)
    private String fatherName;

    @Column(name = "address", unique = true)
    private String address;

    @Column(name = "telephone", unique = true)
    private String telephone;

    @Column(name = "balance", unique = true)
    private double balance;

    @Column(name = "discount", unique = true)
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
