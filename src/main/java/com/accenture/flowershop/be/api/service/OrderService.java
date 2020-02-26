package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdateException;
import com.accenture.flowershop.be.entity.order.Order;

import java.util.Calendar;
import java.util.List;

public interface OrderService {

    Order findOrderById(Integer id);

    Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate);

    void updateOrder(Integer orderId, Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate);

    void deleteOrder(Integer id);

    void createOrder(Double price, String customerEmail) throws EntityCreationException;

    void closeOrder(Integer orderId) throws EntityUpdateException;

    List<Order> findOrdersByCustomerEmail(String email);

    List<Order> findAllOrders();
}
