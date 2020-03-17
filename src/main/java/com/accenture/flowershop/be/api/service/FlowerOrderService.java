package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import com.accenture.flowershop.be.entity.order.Order;

import java.util.List;

public interface FlowerOrderService {
    Integer insertFlowerOrder(Double totalPrice, String name, Integer countToOrder, Integer quantityInStock, Order order);

    void deleteFlowerOrder(Integer id);

    List<FlowerOrder> findAllFlowersOrder();

    FlowerOrder findById(Integer id);

    FlowerOrder findByName(String name);
}
