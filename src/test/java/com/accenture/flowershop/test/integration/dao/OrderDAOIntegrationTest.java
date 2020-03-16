package com.accenture.flowershop.test.integration.dao;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class OrderDAOIntegrationTest extends AbstractIntegrationTest {

    Logger logger = LoggerFactory.getLogger(OrderDAOIntegrationTest.class);

    @Autowired
    OrderDAO orderDAO;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/order/insert_order_dao.sql"})
    public void findOrdersByCustomerEmail() {
        logger.debug("[findOrdersByCustomerEmail]");
        List<Order> orders = orderDAO.findByCustomerEmail("login@222");
        Assert.assertFalse(CollectionUtils.isEmpty(orders));
        Assert.assertEquals(3, orders.size());
    }
}
