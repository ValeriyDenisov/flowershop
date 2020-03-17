package com.accenture.flowershop.be.entity.flower;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "flowers_order")
public class FlowerOrder extends AbstractEntity {

    @Column(name="total_price", nullable = false)
    private Double totalPrice;

    @Column(name="count_to_order", nullable = false)
    private Integer countToOrder;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;


    public FlowerOrder() {
    }

    public FlowerOrder(String name, Double totalPrice, Integer countToOrder, Integer quantityInStock) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.countToOrder = countToOrder;
        this.quantityInStock = quantityInStock;
    }

    public FlowerOrder(String name, Double totalPrice, Integer countToOrder, Integer quantityInStock, Order order) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.countToOrder = countToOrder;
        this.quantityInStock = quantityInStock;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCountToOrder() {
        return countToOrder;
    }

    public void setCountToOrder(Integer count) {
        this.countToOrder = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
