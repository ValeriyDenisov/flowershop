package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class FlowerServiceImpl extends AbstractServiceImpl<Flower> implements FlowerService {
    public static final String ERROR_FLOWER_NAME_EMPTY = "Поле name цветка пусто!";
    public static final String ERROR_FLOWER_PRICE_NULL = "Поле price цветка пусто!";
    public static final String ERROR_FLOWER_QUANTITY_IN_STOCK_NULL = "Поле quantityInStock пользователя пусто!";
    public static final String ERROR_FLOWER_EXISTS_BY_NAME = "Цветок с именем: {0} уже существует!";

    @Autowired
    FlowerDAO flowerDAO;

    public Flower findFlowerByName(String name) {
        CommonUtils.assertEmpty(name, ERROR_FLOWER_NAME_EMPTY);

        return flowerDAO.findByUniqueElement(name, Flower.NAME);
    }

    public Flower findFlowerById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return flowerDAO.findById(id);
    }

    public List<Flower> findAllFlower() {
        return flowerDAO.findAll();
    }

    public void insertFlower(String name, Double price, Integer quantityInStock) {
        CommonUtils.assertEmpty(name, ERROR_FLOWER_NAME_EMPTY);
        CommonUtils.assertNull(price, ERROR_FLOWER_PRICE_NULL);
        CommonUtils.assertNull(quantityInStock, ERROR_FLOWER_QUANTITY_IN_STOCK_NULL);

        if (isFlowerExistByName(name)) {
            throw new EntityException(MessageFormat.format(ERROR_FLOWER_EXISTS_BY_NAME, name));
        }

        Flower flower = createFlower(name, price, quantityInStock);
        flowerDAO.insert(flower);
    }

    public void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock) {
        CommonUtils.assertNull(flowerId, ERROR_ENTITY_ID_NULL);
        CommonUtils.assertEmpty(name, ERROR_FLOWER_NAME_EMPTY);
        CommonUtils.assertNull(price, ERROR_FLOWER_PRICE_NULL);
        CommonUtils.assertNull(quantityInStock, ERROR_FLOWER_QUANTITY_IN_STOCK_NULL);

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
        }
        if(!flower.getName().equalsIgnoreCase(name)) {
            if (isFlowerExistByName(name)) {
                throw new EntityException(MessageFormat.format(ERROR_FLOWER_EXISTS_BY_NAME, name));
            }
        }

        flower.setName(name);
        flower.setPrice(price);
        flower.setQuantityInStock(quantityInStock);
        flowerDAO.update(flower);
    }

    public void deleteFlower(Integer flowerId) {
        CommonUtils.assertNull(flowerId, ERROR_ENTITY_ID_NULL);

        Flower flower = findFlowerById(flowerId);
        if (flower == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Flower.class, flowerId));
        }

        flowerDAO.delete(flower);
    }

    private boolean isFlowerExistByName(String name) {
        return findFlowerByName(name) != null;
    }

    private Flower createFlower(String name, Double price, Integer quantityInStock) {
        return new Flower.Builder(name, price, quantityInStock).build();
    }
}
