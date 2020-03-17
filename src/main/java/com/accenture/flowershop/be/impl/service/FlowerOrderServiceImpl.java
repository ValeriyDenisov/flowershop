package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.FlowerOrderDAO;
import com.accenture.flowershop.be.api.service.FlowerOrderService;
import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import com.accenture.flowershop.be.entity.flower.FlowerOrder_;
import com.accenture.flowershop.be.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowerOrderServiceImpl extends AbstractServiceImpl<FlowerOrder, FlowerOrderDAO> implements FlowerOrderService {

    @Autowired
    FlowerOrderDAO flowerOrderDAO;

    @Override
    public String getEntityName() {
        return FlowerOrder.class.getSimpleName();
    }

    @Override
    public FlowerOrderDAO getDAO() {
        return flowerOrderDAO;
    }

    @Override
    public Integer insertFlowerOrder(Double totalPrice, String name, Integer countToOrder, Integer quantityInStock, Order order) {
        return insertEntity(getFlowerOrderMandatoryFields(name, totalPrice, countToOrder, quantityInStock),
                () -> new FlowerOrder(name, totalPrice, countToOrder, quantityInStock, order));
    }

    @Override
    public void deleteFlowerOrder(Integer id) {
        deleteEntityByUniqueField(FlowerOrder_.ID, id);
    }

    @Override
    public List<FlowerOrder> findAllFlowersOrder() {
        return findAllEntities();
    }

    @Override
    public FlowerOrder findById(Integer id) {
        return findEntityByUniqueField(FlowerOrder_.ID, id);
    }

    @Override
    public FlowerOrder findByName(String name) {
        return findEntityByUniqueField(FlowerOrder_.NAME, name);
    }

    private Map<String, Object> getFlowerOrderMandatoryFields(String name, Double totalPrice, Integer countToOrder, Integer quantityInStock) {
        Map<String, Object> fields = new HashMap<>();

        fields.put(FlowerOrder_.NAME, name);
        fields.put(FlowerOrder_.TOTAL_PRICE, totalPrice);
        fields.put(FlowerOrder_.COUNT_TO_ORDER, countToOrder);
        fields.put(FlowerOrder_.QUANTITY_IN_STOCK, quantityInStock);

        return fields;

    }
}
