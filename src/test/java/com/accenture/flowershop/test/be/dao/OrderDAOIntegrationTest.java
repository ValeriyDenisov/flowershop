package com.accenture.flowershop.test.be.dao;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.test.be.AbstractIntegrationTest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class OrderDAOIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    OrderDAO orderDAO;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/order/insert_order_dao.sql"})
    public void findOrdersByCustomerEmail() {
        List<Order> orders = orderDAO.findByCustomerEmail("email_2");
        Assert.assertFalse(CollectionUtils.isEmpty(orders));
        Assert.assertEquals(3, orders.size());
    }
}
