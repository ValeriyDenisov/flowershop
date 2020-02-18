package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends AbstractServiceImpl<Order> implements OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerDAO customerDAO;

    public Order findOrderById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return orderDAO.findById(id);
    }

    public Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
        CommonUtils.assertValues(getOrderFieldsValues(null, customerId, price, openDate, closeDate, isActive));

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
        }

        Order order = createOrder(customer, price, isActive, openDate, closeDate);
        orderDAO.insert(order);
        return order.getId();
    }

    public void updateOrder(Integer orderId, Integer customerId, Double price,
                            Boolean isActive, Calendar openDate, Calendar closeDate) {
        CommonUtils.assertValues(getOrderFieldsValues(orderId, customerId, price, openDate, closeDate, isActive));

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

    private Map<String, Object> getOrderFieldsValues(Integer id, Integer customerId, Double price, Calendar openDate,
                                                     Calendar closeDate, Boolean isActive) {
        Map<String, Object> fieldsValues = new HashMap<String, Object>();

        if (id != null) {
            fieldsValues.put(Constants.ENTITY_ID, id);
        }
        fieldsValues.put(Constants.ORDER_CUSTOMER, customerId);
        fieldsValues.put(Constants.ORDER_PRICE, price);
        fieldsValues.put(Constants.ORDER_OPEN_DATE, openDate);
        if (closeDate != null) {
            fieldsValues.put(Constants.ORDER_CLOSE_DATE, closeDate);
        }
        fieldsValues.put(Constants.ORDER_IS_ACTIVE, isActive);

        return fieldsValues;

    }
}
