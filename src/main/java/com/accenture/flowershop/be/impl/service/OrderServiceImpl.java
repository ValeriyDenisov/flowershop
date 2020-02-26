package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdateException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl extends AbstractServiceImpl<Order> implements OrderService {
    public static final String ERROR_ORDER_CUSTOMER_NOT_FOUND = "Customer with email: {0} not found";

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerService customerService;

    public Order findOrderById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return orderDAO.findById(id);
    }

    public Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
        CommonUtils.assertValues(getOrderFieldsValues(null, customerId, price, openDate, closeDate, isActive));

        Customer customer = customerService.findCustomerById(customerId);
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
        Customer customer = customerService.findCustomerById(customerId);
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

    @Override
    public void closeOrder(Integer orderId) throws EntityUpdateException {
        CommonUtils.assertNull(orderId, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, "orderId"));

        Order order = findOrderById(orderId);
        if (order == null) {
            throw new EntityUpdateException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Constants.ENTITY_ORDER, orderId));
        }

        order.setCloseDate(Calendar.getInstance());
        order.setActive(false);
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

    @Override
    public void createOrder(Double price, String customerEmail) throws EntityCreationException {
        CommonUtils.assertNull(price, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.ORDER_PRICE));
        CommonUtils.assertEmpty(customerEmail, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.CUSTOMER_EMAIL));

        Customer customer = customerService.findCustomerByEmail(customerEmail);
        if (customer == null) {
            throw new EntityCreationException(MessageFormat.format(ERROR_ORDER_CUSTOMER_NOT_FOUND, customerEmail));
        }

        Order order = new Order.Builder(customer, price, true, Calendar.getInstance()).build();
        orderDAO.insert(order);
    }

    @Override
    public List<Order> findOrdersByCustomerEmail(String email) {
        return orderDAO.findByCustomerEmail(email);
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
