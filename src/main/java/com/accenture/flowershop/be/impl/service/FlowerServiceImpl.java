package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.customer.Customer_;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.flower.Flower_;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FlowerServiceImpl extends AbstractServiceImpl<Flower, FlowerDAO> implements FlowerService {
    public static final String FLOWER_CLASS_NAME = Flower.class.getSimpleName();
    public static final String ERROR_FLOWER_EXISTS_BY_NAME = "Flower with name: {0} already exists!";

    private final Logger logger = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @Autowired
    FlowerDAO flowerDAO;

    @Override
    public String getEntityName() {
        return FLOWER_CLASS_NAME;
    }

    @Override
    public FlowerDAO getDAO() {
        return flowerDAO;
    }

    @Override
    public Flower findFlowerByName(String name) {
        return findEntityByUniqueField(Flower.NAME, name);
    }

    @Override
    public Flower findFlowerById(Integer id) {
        return findEntityByUniqueField(Flower_.ID, id);
    }

    @Override
    public List<Flower> findAllFlowers() {
        return findAllEntities();
    }

    @Override
    public Integer insertFlower(String name, Double price, Integer quantityInStock) throws EntityCreatingException {
        return insertEntity(getFlowersMandatoryFieldsValues(name, price, quantityInStock), () -> {
            if (isEntityExistsByFiled(Flower_.NAME, name)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(Flower_.NAME, name, (ex) -> {
                    throw new EntityCreatingException(ex);
                });
            }
            return createFlower(name, price, quantityInStock);
        });
    }

    @Override
    public void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock) throws EntityUpdatingException {
        updateEntityByUniqueField(Flower_.ID, flowerId, (flower) -> updateFlowerFields(flower, name, price, quantityInStock));
    }

    @Override
    public void deleteFlower(Integer flowerId) throws EntityDeletingException {
        deleteEntityByUniqueField(Customer_.ID, flowerId);
    }

    @Override
    public List<Flower> findFlowersByParameters(String name, Double priceFrom, Double priceTo, Integer limit, Integer offset) {
        return flowerDAO.findByParameters(name, priceFrom, priceTo, limit, offset);
    }


    private Flower createFlower(String name, Double price, Integer quantityInStock) {
        return new Flower.Builder(name, price, quantityInStock).build();
    }

    private Map<String, Object> getFlowersMandatoryFieldsValues(String name, Double price, Integer quantityInStock) {
        Map<String, Object> fieldsValues = new HashMap<>();

        fieldsValues.put(Flower_.ID, name);
        fieldsValues.put(Flower_.PRICE, price);
        fieldsValues.put(Flower_.QUANTITY_IN_STOCK, quantityInStock);

        return fieldsValues;
    }

    private Map<String, Object> getFlowersMandatoryFieldsValues(Integer id, String name, Double price, Integer quantityInStock) {
        Map<String, Object> fieldsValues = getFlowersMandatoryFieldsValues(name, price, quantityInStock);
        fieldsValues.put(Flower_.ID, id);
        return fieldsValues;
    }

    private void updateFlowerFields(Flower flower, String name, Double price, Integer quantityInStock) {
        if (StringUtils.isNotEmpty(name) && !name.equalsIgnoreCase(flower.getName())) {
            if (isEntityExistsByFiled(Flower_.NAME, name)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(Flower_.NAME, name, (ex) -> {
                    throw new EntityUpdatingException(ex);
                });
            }
            flower.setName(name);
        }
        if (price != null && price != 0) {
            flower.setPrice(price);
        }
        if (quantityInStock != null) {
            flower.setQuantityInStock(quantityInStock);
        }
    }
}
