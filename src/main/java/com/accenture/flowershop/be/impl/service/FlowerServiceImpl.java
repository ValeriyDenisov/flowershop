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
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public List<Flower> findAllFlowers() {
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

        try {
            flowerDAO.insert(flower);
        } catch (HibernateException e) {
            throw new EntityCreatingException(e);
        }
        return flower.getId();
    }

    public void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock) throws EntityUpdatingException {
        CommonUtils.assertNull(flowerId, ERROR_ENTITY_ID_NULL);

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
            throw new EntityUpdatingException(ex);
        }


        if (StringUtils.isNotEmpty(name) && !name.equalsIgnoreCase(flower.getName())) {
            if (isFlowerExistByName(name)) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_FLOWER_EXISTS_BY_NAME, name));
                throw new EntityUpdatingException(ex);
            }
            flower.setName(name);
        }
        if (price != null && price != 0.0) {
            flower.setPrice(price);
        }
        if (quantityInStock != null && !quantityInStock.equals(flower.getQuantityInStock())) {
            flower.setQuantityInStock(quantityInStock);
        }
        validateEntity(flower, (ex -> {
            throw new EntityUpdatingException(ex);
        }));

        try {
            flowerDAO.update(flower);
        } catch (HibernateException e) {
            throw new EntityUpdatingException(e);
        }
    }

    public void deleteFlower(Integer flowerId) throws EntityDeletingException {
        CommonUtils.assertNull(flowerId, ERROR_ENTITY_ID_NULL);

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
            throw new EntityDeletingException(ex);
        }

        try {
            flowerDAO.delete(flower);
        } catch (HibernateException e) {
            throw new EntityDeletingException(e);
        }
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
