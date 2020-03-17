package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.fe.application.Cart;

import java.util.Calendar;
import java.util.List;

public interface OrderService {

    Order findOrderById(Integer id);

    Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityCreatingException;

    Integer insertOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityCreatingException;

    Integer insertOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate, List<FlowerOrder> flowers)
            throws EntityCreatingException;

    void updateOrder(Integer orderId, Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityUpdatingException;

    void updateOrder(Order order, Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityUpdatingException;

    void deleteOrder(Integer id) throws EntityDeletingException;

    void createOrder(Double price, String customerEmail, Cart cart) throws EntityCreatingException;

    void closeOrder(Integer orderId) throws EntityUpdatingException;

    List<Order> findOrdersByCustomerEmail(String email);

    List<Order> findAllOrders();
}
