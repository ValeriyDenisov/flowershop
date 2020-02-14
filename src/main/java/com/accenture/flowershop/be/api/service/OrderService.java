package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.order.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface OrderService {

    Order findOrderById(Integer id);

    void insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate);

    void updateOrder(Integer orderId, Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate);

    void deleteOrder(Integer id);

    List<Order> findAllOrders();
}
