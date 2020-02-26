package com.accenture.flowershop.fe.application;

public class FlowerInformation {
    private Double price;
    private Integer countToOrder;

    public FlowerInformation(Double price, Integer countToOrder) {
        this.price = price;
        this.countToOrder = countToOrder;
    }

    public FlowerInformation() {}

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCountToOrder() {
        return countToOrder;
    }

    public void setCountToOrder(Integer countToOrder) {
        this.countToOrder = countToOrder;
    }
}
