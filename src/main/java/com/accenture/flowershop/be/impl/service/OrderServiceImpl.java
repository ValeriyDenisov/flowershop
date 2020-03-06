package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.application.Cart;
import org.hibernate.HibernateException;
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
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService {
    public static final String ERROR_ORDER_CUSTOMER_NOT_FOUND = "Customer with email: {0} not found";
    public static final String ERROR_ORDER_CUSTOMER_NOT_ENOUGH_BALANCE = "Customer with email: {0} not enough balance";

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerService customerService;

    public Order findOrderById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return orderDAO.findById(id);
    }

    public Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityCreatingException {
        CommonUtils.assertValues(getOrderFieldsValues(null, customerId, price, openDate, closeDate, isActive));

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
            throw new EntityCreatingException(ex);
        }

        Order order = initOrder(customer, price, isActive, openDate, closeDate);
        validateEntity(order, (ex) -> {
            throw new EntityCreatingException(ex);
        });

        try {
            orderDAO.insert(order);
        } catch (HibernateException e) {
            throw new EntityCreatingException(e);
        }
        return order.getId();
    }


    public void updateOrder(Integer orderId, Integer customerId, Double price,
                            Boolean isActive, Calendar openDate, Calendar closeDate) throws EntityUpdatingException {
        CommonUtils.assertNull(orderId, ERROR_ENTITY_ID_NULL);

        Order order = findOrderById(orderId);
        if (order == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Order.class, orderId));
            throw new EntityUpdatingException(ex);
        }


        if (isActive != null && isActive != order.getActive()) {
            order.setActive(isActive);
        }
        if (closeDate != null && !closeDate.equals(order.getCloseDate())) {
            order.setCloseDate(closeDate);
        }
        if (customerId != null && !customerId.equals(order.getCustomer().getId())) {
            Customer customer = customerService.findCustomerById(customerId);
            if (customer == null) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
                throw new EntityUpdatingException(ex);
            }
            order.setCustomer(customer);
        }
        if (openDate != null && !openDate.equals(order.getOpenDate())) {
            order.setOpenDate(openDate);
        }
        if (price != null) {
            order.setPrice(price);
        }
        validateEntity(order, (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        try {
            orderDAO.update(order);
        } catch (HibernateException e) {
            throw new EntityUpdatingException(e);
        }
    }

    @Override
    public void closeOrder(Integer orderId) throws EntityUpdatingException {
        CommonUtils.assertNull(orderId, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, "orderId"));

        Order order = findOrderById(orderId);
        if (order == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Constants.ENTITY_ORDER, orderId));
            throw new EntityUpdatingException(ex);
        }

        Customer customer = order.getCustomer();
        if (order.getCustomer().getBalance() <= order.getPrice()) {
            throw new EntityUpdatingException(MessageFormat.format(ERROR_ORDER_CUSTOMER_NOT_ENOUGH_BALANCE, order.getCustomer().getEmail()));
        }

        customerService.updateCustomer(customer.getId(), customer.getName(), customer.getSecondName(), customer.getFatherName(),
                customer.getAddress().getId(), customer.getPhone(), customer.getBalance() - order.getPrice(), customer.getDiscount(), customer.getEmail());
        order.setCloseDate(Calendar.getInstance());
        order.setActive(false);

        try {
            orderDAO.update(order);
        } catch (HibernateException e) {
            throw new EntityUpdatingException(e);
        }
    }

    public void deleteOrder(Integer id) throws EntityDeletingException {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        Order order = findOrderById(id);
        if (order == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Order.class, id));
            throw new EntityDeletingException(ex);
        }

        try {
            orderDAO.delete(order);
        } catch (HibernateException e) {
            throw new EntityDeletingException(e);
        }
    }

    @Override
    public void createOrder(Double price, String customerEmail, Cart cart) throws EntityCreatingException {
        CommonUtils.assertNull(price, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.ORDER_PRICE));
        CommonUtils.assertEmpty(customerEmail, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.CUSTOMER_EMAIL));

        Customer customer = customerService.findCustomerByEmail(customerEmail);
        if (customer == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ORDER_CUSTOMER_NOT_FOUND, customerEmail));
            throw new EntityCreatingException(ex);
        }

        Order order = new Order.Builder(customer, price, true, Calendar.getInstance()).build();
        order.setCart(cart);
        validateEntity(order, (ex -> {
            throw new EntityCreatingException(ex);
        }));

        try {
            orderDAO.insert(order);
        } catch (HibernateException e) {
            throw new EntityCreatingException(e);
        }
    }

    @Override
    public List<Order> findOrdersByCustomerEmail(String email) {
        return orderDAO.findByCustomerEmail(email);
    }

    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    private Order initOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
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
