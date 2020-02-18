package com.accenture.flowershop.be.entity.order;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.flower.Flower;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "price")
    private Double price;

    @Column(name = "openDate")
    private Calendar openDate;

    @Column(name = "closeDate")
    private Calendar closeDate;

    @Column(name = "isActive")
    private Boolean isActive;

    @Transient
    private Map<Flower, Integer> flowers;

    public Map<Flower, Integer> getFlowers() {
        return flowers;
    }

    public void setFlowers(Map<Flower, Integer> flowers) {
        this.flowers = flowers;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + (id != null ? id : "") +
                ", customer=" + customer.toString() +
                ", price=" + price +
                ", isActive=" + isActive +
                ", openDate=" + openDate +
                ", closeDate=" + (closeDate != null ? closeDate.toString() : "") +
                '}';
    }

    public static class Builder {
        private Integer id;
        private Customer customer;
        private Double price;
        private Boolean isActive;
        private Calendar openDate;
        private Calendar closeDate;

        public Builder(Customer customer, Double price, Boolean isActive,
                       Calendar openDate) {
            this.customer = customer;
            this.price = price;
            this.isActive = isActive;
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
        this.isActive = builder.isActive;
        this.openDate = builder.openDate;
        this.id = builder.id;
        this.closeDate = builder.closeDate;
    }

    public Order() {}
}
