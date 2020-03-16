package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderServiceImplIntegrationTest extends AbstractIntegrationTest {
    public static final String ORDER_TABLE_NAME = "orders";
    public static final Double ORDER_PRICE_1 = 111.11;
    public static final Integer ORDER_ID_1 = 1;
    public static final Calendar ORDER_OPEN_DATE_1 = new GregorianCalendar(2020, Calendar.JANUARY, 20);
    public static final Calendar ORDER_CLOSE_DATE_1 = null;
    public static final Boolean ORDER_IS_ACTIVE_1 = true;
    public static final Integer ORDER_CUSTOMER_ID_1 = 1;
    public static final Double ORDER_PRICE_2 = 222.22;
    public static final Calendar ORDER_OPEN_DATE_2 = new GregorianCalendar(2020, Calendar.JANUARY, 20);
    public static final Calendar ORDER_CLOSE_DATE_2 = new GregorianCalendar(2020, Calendar.JANUARY, 22);
    public static final Boolean ORDER_IS_ACTIVE_2 = false;
    public static final Integer ORDER_CUSTOMER_ID_2 = 2;
    public static final Integer ORDER_ID_2 = 2;

    Logger logger = LoggerFactory.getLogger(OrderServiceImplIntegrationTest.class);

    @Autowired
    OrderService orderService;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/order/insert_order_dao.sql",})
    public void insertOrderTest() {
        logger.debug("[insertOrderTest]");
        orderService.insertOrder(1, 111.43, true, Calendar.getInstance(), null);
        Order order = findById(6, Order.class);
        Assert.assertNotNull(order);
        Assert.assertEquals((Double) 111.43, order.getPrice());
        Assert.assertEquals(true, order.getActive());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/order/insert_order_dao.sql"})
    public void updateOrderTest() {
        logger.debug("[updateOrderTest]");
        orderService.updateOrder(1, null, 555.55, null, null, null);
        Order order = findById(1, Order.class);
        Assert.assertNotNull(order);
        Assert.assertEquals((Double) 555.55, order.getPrice());

    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/order/insert_order_dao.sql"})
    public void deleteOrderTest() {
        logger.debug("[deleteOrderTest]");
        orderService.deleteOrder(1);
        Order order = findById(1, Order.class);
        Assert.assertNull(order);
    }

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void insertOrderCustomerNotFoundTest() {
        logger.debug("[insertOrderCustomerNotFoundTest]");
        orderService.insertOrder(404, ORDER_PRICE_1, ORDER_IS_ACTIVE_1, ORDER_OPEN_DATE_1,
                ORDER_CLOSE_DATE_1);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void deleteOrderNotFoundTest() {
        logger.debug("[deleteOrderNotFoundTest]");
        orderService.deleteOrder(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void updateOrderNotFoundTest() {
        logger.debug("[updateOrderNotFoundTest]");
        orderService.updateOrder(404, ORDER_CUSTOMER_ID_1, ORDER_PRICE_1, ORDER_IS_ACTIVE_1,
                ORDER_OPEN_DATE_1, ORDER_CLOSE_DATE_1);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql",
            "/sql/order/insert_order.sql"})
    public void updateOrderCustomerNotFoundTest() {
        logger.debug("[updateOrderCustomerNotFoundTest]");
        orderService.updateOrder(ORDER_ID_2, 404, ORDER_PRICE_2, ORDER_IS_ACTIVE_2, ORDER_OPEN_DATE_1,
                null
        );
    }
}
