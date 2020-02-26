package com.accenture.flowershop.be.api.dao;

import com.accenture.flowershop.be.entity.order.Order;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {
    List<Order> findByCustomerEmail(String email);
}
