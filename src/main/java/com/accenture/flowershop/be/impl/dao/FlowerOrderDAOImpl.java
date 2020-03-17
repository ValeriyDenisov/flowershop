package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.api.dao.FlowerOrderDAO;
import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import org.springframework.stereotype.Repository;

@Repository
public class FlowerOrderDAOImpl extends AbstractDAOImpl<FlowerOrder> implements FlowerOrderDAO {
    @Override
    protected Class<FlowerOrder> getType() {
        return FlowerOrder.class;
    }
}
