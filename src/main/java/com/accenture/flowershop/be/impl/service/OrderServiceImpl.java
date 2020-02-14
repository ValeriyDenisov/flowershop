package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends AbstractServiceImpl<Order> implements OrderService {
    public static final String ERROR_ORDER_PRICE_NULL = "Поле price заказа пусто!";
    public static final String ERROR_ORDER_OPED_DATE_NULL = "Поле openDate заказа пусто!";
    public static final String ERROR_ORDER_CLOSE_DATE_NULL = "Поле closeDate заказа пусто!";
    public static final String ERROR_ORDER_IS_ACTIVE_NULL = "Поле isActive заказа пусто!";
    public static final String ERROR_ORDER_CUSTOMER_NULL = "Поле customer заказа пусто!";

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerDAO customerDAO;

    public Order findOrderById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return orderDAO.findById(id);
    }

    public void insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
        CommonUtils.assertNull(customerId, ERROR_ENTITY_ID_NULL);
        CommonUtils.assertNull(price, ERROR_ORDER_PRICE_NULL);
        CommonUtils.assertNull(isActive, ERROR_ORDER_IS_ACTIVE_NULL);
        CommonUtils.assertNull(openDate, ERROR_ORDER_OPED_DATE_NULL);

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
        }

        Order order = createOrder(customer, price, isActive, openDate, closeDate);
        orderDAO.insert(order);
    }

    public void updateOrder(Integer orderId, Integer customerId, Double price,
                            Boolean isActive, Calendar openDate, Calendar closeDate) {
        CommonUtils.assertNull(customerId, ERROR_ORDER_CUSTOMER_NULL);
        CommonUtils.assertNull(orderId, ERROR_ENTITY_ID_NULL);
        CommonUtils.assertNull(price, ERROR_ORDER_PRICE_NULL);
        CommonUtils.assertNull(isActive, ERROR_ORDER_IS_ACTIVE_NULL);
        CommonUtils.assertNull(openDate, ERROR_ORDER_OPED_DATE_NULL);

        Order order = findOrderById(orderId);
        if (order == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Order.class, orderId));
        }
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
        }

        order.setActive(isActive);
        order.setCloseDate(closeDate);
        order.setCustomer(customer);
        order.setOpenDate(openDate);
        order.setPrice(price);
        orderDAO.update(order);
    }

    public void deleteOrder(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        Order order = findOrderById(id);
        if (order == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Order.class, id));
        }

        orderDAO.delete(order);
    }

    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    private Order createOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
        return new Order.Builder(customer, price, isActive, openDate).closeDate(closeDate).build();
    }
}
