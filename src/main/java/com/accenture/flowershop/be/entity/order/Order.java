package com.accenture.flowershop.be.entity.order;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.fe.application.Cart;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.util.Calendar;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Order customer is null!")
    @Valid
    private Customer customer;


    @Column(name = "price", nullable = false)
    @NotNull(message = "Order price is null!")
    @Positive(message = "Order price is negative!")
    private Double price;

    @Column(name = "open_date", nullable = false)
    @NotNull(message = "Order open date is null!")
    @PastOrPresent(message = "Order open date is incorrect!")
    private Calendar openDate;


    @Column(name = "close_date")
    @PastOrPresent(message = "Order close date is incorrect!")
    private Calendar closeDate;

    @Column(name = "is_active")
    @NotNull(message = "Order active is null!")
    private Boolean active;

    @Transient
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public Calendar getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Order{")
                .append("id=").append(id != null ? id : "")
                .append(", customer= ").append(customer.toString())
                .append(", price= ").append(price)
                .append(", isActive= ").append(active)
                .append(", openDate= ").append(openDate.getTime().toString())
                .append(", closeDate= ").append(closeDate != null ? closeDate.getTime().toString() : "")
                .append("}");
        return builder.toString();
    }

    public static class Builder {
        private Integer id;
        private Customer customer;
        private Double price;
        private Boolean active;
        private Calendar openDate;
        private Calendar closeDate;

        public Builder(Customer customer, Double price, Boolean active,
                       Calendar openDate) {
            this.customer = customer;
            this.price = price;
            this.active = active;
            this.openDate = openDate;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder closeDate(Calendar closeDate) {
            this.closeDate = closeDate;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    private Order(Builder builder) {
        this.customer = builder.customer;
        this.price = builder.price;
        this.active = builder.active;
        this.openDate = builder.openDate;
        this.id = builder.id;
        this.closeDate = builder.closeDate;
    }

    public Order() {
    }
}
