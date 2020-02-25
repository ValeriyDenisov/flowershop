package com.accenture.flowershop.be.entity.customer;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "discount", nullable = false)
    private Short discount;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String telephone) {
        this.phone = telephone;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setDiscount(Short discount) {
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

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Double getBalance() {
        return balance;
    }

    public Short getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + (id != null ? id : "") +
                ", name=" + name +
                ", secondName=" + secondName +
                ", fatherName=" + (fatherName != null ? fatherName : "") +
                ", address=" + address.toString() +
                ", phone=" + phone +
                ", balance=" + balance +
                ", discount=" + discount +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String secondName;
        private String fatherName;
        private Address address;
        private String phone;
        private Double balance;
        private Short discount;
        private String email;

        public Builder(String name, String secondName, Address address,
                       String phone, Double balance, Short discount, String email) {
            this.name = name;
            this.secondName = secondName;
            this.address = address;
            this.phone = phone;
            this.balance = balance;
            this.discount = discount;
            this.email = email;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder fatherName(String fatherName) {
            this.fatherName = fatherName;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    private Customer(Builder builder) {
        this.name = builder.name;
        this.secondName = builder.secondName;
        this.fatherName = builder.fatherName;
        this.address = builder.address;
        this.id = builder.id;
        this.phone = builder.phone;
        this.discount = builder.discount;
        this.balance = builder.balance;
        this.email = builder.email;
    }

    public Customer() {
    }
}

