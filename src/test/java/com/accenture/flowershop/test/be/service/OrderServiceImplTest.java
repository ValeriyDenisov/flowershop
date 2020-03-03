package com.accenture.flowershop.test.be.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.test.be.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderServiceImplTest extends AbstractIntegrationTest {
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

    @Autowired
    OrderService orderService;

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void insertOrderCustomerNotFoundTest() {
        orderService.insertOrder(404, ORDER_PRICE_1, ORDER_IS_ACTIVE_1, ORDER_OPEN_DATE_1,
                ORDER_CLOSE_DATE_1);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void deleteOrderNotFoundTest() {
        orderService.deleteOrder(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql"})
    public void updateOrderNotFoundTest() {
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
        orderService.updateOrder(ORDER_ID_2, 404, ORDER_PRICE_2, ORDER_IS_ACTIVE_2, ORDER_OPEN_DATE_1,
                null
        );
    }
}
