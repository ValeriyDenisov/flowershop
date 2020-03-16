package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.customer.Customer_;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.entity.order.Order_;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.application.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OrderServiceImpl extends AbstractServiceImpl<Order, OrderDAO> implements OrderService {
    public static final String ORDER_CLASS_NAME = Order.class.getSimpleName();
    public static final String ERROR_ORDER_CUSTOMER_NOT_FOUND = "Customer with email: {0} not found";
    public static final String ERROR_ORDER_CUSTOMER_NOT_ENOUGH_BALANCE = "Customer with email: {0} not enough balance";
    public static final String ERROR_ORDER_CUSTOMER_NOT_PERSISTENCE = "{} not persistence";

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerService customerService;

    @Override
    public String getEntityName() {
        return ORDER_CLASS_NAME;
    }

    @Override
    public OrderDAO getDAO() {
        return orderDAO;
    }

    @Override
    public Order findOrderById(Integer id) {
        return findEntityByUniqueField(Order_.ID, id);
    }

    @Override
    public Integer insertOrder(Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityCreatingException {
        return insertEntity(getOrderFieldsValues(customerId, price, openDate, isActive), () -> {
            Customer customer = customerService.findCustomerById(customerId);
            assertEntityNull(customer, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, CustomerServiceImpl.CUSTOMER_CLASS_NAME,
                    Customer_.ID, customerId), (ex) -> {
                throw new EntityCreatingException(ex);
            });
            return initOrder(customer, price, isActive, openDate, closeDate);
        });
    }

    @Override
    public Integer insertOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate)
            throws EntityCreatingException {
        return insertEntity(getOrderFieldsValues(customer, price, openDate, isActive), () -> {
            if (isPersist(customer)) {
                return initOrder(customer, price, isActive, openDate, closeDate);
            } else {
                throw new EntityCreatingException(MessageFormat.format(ERROR_ORDER_CUSTOMER_NOT_PERSISTENCE, customer.toString()));
            }
        });
    }


    @Override
    public void updateOrder(Integer orderId, Integer customerId, Double price,
                            Boolean isActive, Calendar openDate, Calendar closeDate) throws EntityUpdatingException {
        updateEntityByUniqueField(Order_.ID, orderId, (order) -> updateOrderFields(order, customerId, price, openDate, isActive, closeDate));
    }

    @Override
    public void updateOrder(Order order, Integer customerId, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) throws EntityUpdatingException {
        updateEntityByUniqueField(order, (orderToUpdate) -> updateOrderFields(order, customerId, price, openDate, isActive, closeDate));
    }

    @Override
    public void closeOrder(Integer orderId) throws EntityUpdatingException {
        Order order;
        try {
            order = findOrderById(orderId);
        } catch (IllegalArgumentException e) {
            throw new  EntityUpdatingException(e);
        }

        assertEntityNull(order, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, getEntityName(), Order_.ID, orderId), (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        Customer customer = order.getCustomer();
        if (order.getCustomer().getBalance() <= order.getPrice()) {
            throw new EntityUpdatingException(MessageFormat.format(ERROR_ORDER_CUSTOMER_NOT_ENOUGH_BALANCE, order.getCustomer().getEmail()));
        }

        customerService.updateCustomer(customer, null, null, null, null, null,
                customer.getBalance() - order.getPrice(), null, null);
        updateOrder(order, null, null, false, null, Calendar.getInstance());
    }

    @Override
    public void deleteOrder(Integer id) throws EntityDeletingException {
        deleteEntityByUniqueField(Order_.ID, id);
    }

    @Override
    public void createOrder(Double price, String customerEmail, Cart cart) throws EntityCreatingException {
        CommonUtils.assertNull(price, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.ORDER_PRICE));
        CommonUtils.assertEmpty(customerEmail, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.CUSTOMER_EMAIL));

        Customer customer = customerService.findCustomerByEmail(customerEmail);
        assertEntityNull(customer, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, CustomerServiceImpl.CUSTOMER_CLASS_NAME,
                Customer_.EMAIL, customerEmail), (ex) -> {
            throw new EntityCreatingException(ex);
        });

        insertOrder(customer, price, true, Calendar.getInstance(), null);
    }

    @Override
    public List<Order> findOrdersByCustomerEmail(String email) {
        return orderDAO.findByCustomerEmail(email);
    }

    public List<Order> findAllOrders() {
        return findAllEntities();
    }

    private Order initOrder(Customer customer, Double price, Boolean isActive, Calendar openDate, Calendar closeDate) {
        return new Order.Builder(customer, price, isActive, openDate).closeDate(closeDate).build();
    }

    private Map<String, Object> getOrderFieldsValues(Integer customerId, Double price, Calendar openDate, Boolean isActive) {
        Map<String, Object> fieldsValues = new HashMap<>();

        fieldsValues.put(Order_.CUSTOMER, customerId);
        fieldsValues.put(Order_.PRICE, price);
        fieldsValues.put(Order_.OPEN_DATE, openDate);
        fieldsValues.put(Order_.ACTIVE, isActive);

        return fieldsValues;
    }

    private Map<String, Object> getOrderFieldsValues(Customer customer, Double price, Calendar openDate, Boolean isActive) {
        Map<String, Object> fieldsValues = getOrderFieldsValues((Integer) null, price, openDate, isActive);
        fieldsValues.replace(Order_.CUSTOMER, customer);
        return fieldsValues;
    }

    private Map<String, Object> getOrderFieldsValues(Integer id, Integer customerId, Double price, Calendar openDate, Boolean isActive) {
        Map<String, Object> fieldsValues = getOrderFieldsValues(customerId, price, openDate, isActive);
        fieldsValues.put(Order_.ID, id);
        return fieldsValues;
    }

    private void updateOrderFields(Order order, Integer customerId, Double price, Calendar openDate, Boolean active, Calendar closeDate) {
        if (customerId != null) {
            Customer customer = customerService.findCustomerById(customerId);
            assertEntityNull(customer, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, CustomerServiceImpl.CUSTOMER_CLASS_NAME,
                    Customer_.ID, customerId), (ex) -> {
                throw new EntityUpdatingException(ex);
            });
            order.setCustomer(customer);
        }
        if (price != null && price != 0) {
            order.setPrice(price);
        }
        if (openDate != null) {
            order.setOpenDate(openDate);
        }
        if (active != null) {
            order.setActive(active);
        }
        order.setCloseDate(closeDate);
    }
}
