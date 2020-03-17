package com.accenture.flowershop.fe.application;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("session")
public class Cart {
    private Double totalPrice;

    private Map<Flower, FlowerInformation> flowers;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Flower, FlowerInformation> getFlowers() {
        return flowers;
    }

    public void setFlowers(Map<Flower, FlowerInformation> flowers) {
        this.flowers = flowers;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
