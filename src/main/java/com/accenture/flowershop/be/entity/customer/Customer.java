package com.accenture.flowershop.be.entity.customer;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address_id", nullable = false)
    @NotNull(message = "Customer address is null!")
    @Valid
    private Address address;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Customer name is empty!")
    @Size(min = 2, max = 50, message = "Customer name size is incorrect!")
    private String name;

    @Column(name = "second_name", nullable = false)
    @NotBlank(message = "Customer second name is empty!")
    @Size(min = 2, max = 50, message = "Customer second name size is incorrect!")
    private String secondName;

    @Column(name = "father_name")
    @Size(min = 2, max = 50, message = "Customer father name size is incorrect!")
    private String fatherName;

    @Column(name = "phone", unique = true, nullable = false)
    @NotBlank(message = "Customer phone number is empty!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Customer phone number is incorrect!")
    private String phone;

    @Column(name = "balance", nullable = false)
    @Min(value = 0, message = "Customer balance is negative!")
    @NotNull(message = "Customer balance is null!")
    private Double balance;

    @Column(name = "discount", nullable = false)
    @NotNull(message = "Customer discount is null!")
    @Min(value = 0, message = "Customer discount is negative!")
    @Max(value = 100, message = "Customer discount more than 100%!")
    private Short discount;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Customer email is empty!")
    @Size(min = 5, max = 50, message = "Customer email size is incorrect")
    @Email(message = "Customer email is incorrect!")
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
        StringBuilder builder = new StringBuilder();
        builder
                .append("Customer{")
                .append("id=").append(id != null ? id : "")
                .append(", name= ").append(name)
                .append(", secondName= ").append(secondName)
                .append(", fatherName= ").append(fatherName != null ? fatherName : "")
                .append(", address= ").append(address.toString())
                .append(", phone= ").append(phone)
                .append(", balance= ").append(balance)
                .append(", discount= ").append(discount)
                .append(", email= ").append(email)
                .append("}");
        return builder.toString();
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

