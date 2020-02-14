package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.entity.order.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends AbstractDAOImpl<Order> implements OrderDAO {

    @Override
    protected Class<Order> getType() {
        return Order.class;
    }

}
