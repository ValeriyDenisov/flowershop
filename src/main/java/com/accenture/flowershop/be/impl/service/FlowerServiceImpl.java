package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowerServiceImpl extends AbstractServiceImpl implements FlowerService {
    public static final String ERROR_FLOWER_EXISTS_BY_NAME = "Flower with name: {0} already exists!";

    @Autowired
    FlowerDAO flowerDAO;

    public Flower findFlowerByName(String name) {
        CommonUtils.assertEmpty(name, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.FLOWER_NAME));

        return flowerDAO.findByUniqueElement(name, Flower.NAME);
    }

    public Flower findFlowerById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return flowerDAO.findById(id);
    }

    public List<Flower> findAllFlower() {
        return flowerDAO.findAll();
    }

    public Integer insertFlower(String name, Double price, Integer quantityInStock) throws EntityCreatingException {
        CommonUtils.assertValues(getFlowersFieldsValues(null, name, price, quantityInStock));

        if (isFlowerExistByName(name)) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_FLOWER_EXISTS_BY_NAME, name));
            throw new EntityCreatingException(ex);
        }

        Flower flower = createFlower(name, price, quantityInStock);
        validateEntity(flower, (ex) -> {
            throw new EntityCreatingException(ex);
        });

        flowerDAO.insert(flower);
        return flower.getId();
    }

    public void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock) throws EntityUpdatingException {
        CommonUtils.assertValues(getFlowersFieldsValues(flowerId, name, price, quantityInStock));

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
            throw new EntityUpdatingException(ex);
        }
        if (!flower.getName().equalsIgnoreCase(name)) {
            if (isFlowerExistByName(name)) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_FLOWER_EXISTS_BY_NAME, name));
                throw new EntityUpdatingException(ex);
            }
        }

        flower.setName(name);
        flower.setPrice(price);
        flower.setQuantityInStock(quantityInStock);
        validateEntity(flower, (ex -> {
            throw new EntityUpdatingException(ex);
        }));

        flowerDAO.update(flower);
    }

    public void deleteFlower(Integer flowerId) throws EntityDeletingException {
        CommonUtils.assertNull(flowerId, ERROR_ENTITY_ID_NULL);

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
            throw new EntityDeletingException(ex);
        }

        flowerDAO.delete(flower);
    }

    @Override
    public List<Flower> findFlowersByParameters(String name, Double priceFrom, Double priceTo, Integer limit, Integer offset) {
        return flowerDAO.findByParameters(name, priceFrom, priceTo, limit, offset);
    }

    private boolean isFlowerExistByName(String name) {
        return findFlowerByName(name) != null;
    }

    private Flower createFlower(String name, Double price, Integer quantityInStock) {
        return new Flower.Builder(name, price, quantityInStock).build();
    }

    private Map<String, Object> getFlowersFieldsValues(Integer id, String name, Double price, Integer quantityInStock) {
        Map<String, Object> fieldsValues = new HashMap<>();

        if (id != null) {
            fieldsValues.put(Constants.ENTITY_ID, id);
        }
        fieldsValues.put(Constants.FLOWER_NAME, name);
        fieldsValues.put(Constants.FLOWER_PRICE, price);
        fieldsValues.put(Constants.FLOWER_QUANTITY_IN_STOCK, quantityInStock);

        return fieldsValues;
    }
}
