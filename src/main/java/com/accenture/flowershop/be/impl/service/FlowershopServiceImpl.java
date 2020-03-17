package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.service.*;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.flower.FlowerOrder;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.application.Cart;
import com.accenture.flowershop.fe.application.FlowerInformation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FlowershopServiceImpl implements FlowershopService {
    public static final String ERROR_ENTITY_CREATION = "Error on creation {0}";
    public static final String ERROR_FLOWER_NOT_FOUND = "Flower {0} not found";
    public static final String ERROR_FLOWER_NOT_ENOUGH_IN_STOCK = "Flowers {0} not enough in stock";
    private final Double STARTING_BALANCE = 2000.0;
    private final Short STARTING_DISCOUNT = 3;

    @Autowired
    FlowerService flowerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void customerRegistration(String name, String secondName, String fatherName, String city, String street,
                                     Integer code, Integer building, String phone, String email, String password) throws EntityException {
        CommonUtils.assertValues(CommonUtils.getFieldsValues(() -> {
            HashMap<String, Object> fieldsValues = new HashMap<>();
            fieldsValues.put(Constants.CUSTOMER_NAME, name);
            fieldsValues.put(Constants.CUSTOMER_SECOND_NAME, secondName);
            if (!StringUtils.isEmpty(fatherName)) {
                fieldsValues.put(Constants.CUSTOMER_FATHER_NAME, fatherName);
            }
            fieldsValues.put(Constants.ADDRESS_CITY, city);
            fieldsValues.put(Constants.ADDRESS_STREET, street);
            fieldsValues.put(Constants.ADDRESS_CODE, code);
            fieldsValues.put(Constants.ADDRESS_BUILDING, building);
            fieldsValues.put(Constants.CUSTOMER_PHONE, phone);
            fieldsValues.put(Constants.CUSTOMER_EMAIL, email);
            fieldsValues.put(Constants.USER_PASSWORD, password);
            return fieldsValues;
        }));

        Integer addressId = addressService.insertAddress(street, city, code, building);
        if (addressId == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_CREATION, Constants.ENTITY_ADDRESS));
            throw new EntityCreatingException(ex);
        }

        Integer userId;
        try {
            userId = userService.insertUser(email, passwordEncoder.encode(password));
        } catch (EntityCreatingException e) {
            addressService.deleteAddress(addressId);
            throw new EntityCreatingException(e.getMessage());
        }

        try {
            customerService.insertCustomer(name, secondName, fatherName, addressId, phone,
                    STARTING_BALANCE, STARTING_DISCOUNT, email);
        } catch (EntityCreatingException e) {
            userService.deleteUser(userId);
            addressService.deleteAddress(addressId);
            throw new EntityCreatingException(e.getMessage());
        }
    }

    @Override
    public Cart addFlowersToCart(Cart cart, String flowerName, Integer count, Short discount) throws EntityException {
        CommonUtils.assertEmpty(flowerName, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, "flowerName"));
        CommonUtils.assertNull(count, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, "count"));
        CommonUtils.assertNull(discount, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, Constants.CUSTOMER_DISCOUNT));

        Map<Flower, FlowerInformation> flowersToAdd;
        if (cart == null) {
            cart = new Cart();
            flowersToAdd = new HashMap<>();
        } else {
            flowersToAdd = cart.getFlowers();
        }

        Flower flower = flowerService.findFlowerByName(flowerName);
        if (flower == null) {
            throw new EntityFindingException(MessageFormat.format(ERROR_FLOWER_NOT_FOUND, flowerName));
        }
        if (flower.getQuantityInStock() < count) {
            IllegalArgumentException ex = new IllegalArgumentException(MessageFormat.format(ERROR_FLOWER_NOT_ENOUGH_IN_STOCK, flowerName));
            throw new EntityCreatingException(ex);

        }

        Double priceForFlower = flower.getPrice() * count;
        FlowerInformation flowerInfo;
        if (MapUtils.isNotEmpty(flowersToAdd)) {
            boolean isNotAdded = true;
            for (Flower addedFlower : flowersToAdd.keySet()) {
                if (flower.equals(addedFlower)) {
                    flowerInfo = cart.getFlowers().get(addedFlower);
                    flowerInfo.setCountToOrder(flowerInfo.getCountToOrder() + count);
                    flowerInfo.setPrice(flowerInfo.getPrice() + priceForFlower);
                    flowersToAdd.replace(addedFlower, flowersToAdd.get(addedFlower), flowerInfo);
                    isNotAdded = false;
                }
            }
            if (isNotAdded) {
                flowerInfo = new FlowerInformation(priceForFlower, count);
                flowersToAdd.put(flower, flowerInfo);
            }
        } else {
            flowerInfo = new FlowerInformation(priceForFlower, count);
            flowersToAdd.put(flower, flowerInfo);
        }

        cart.setTotalPrice(calculateTotalPrice(flowersToAdd, discount));
        cart.setFlowers(flowersToAdd);
        flowerService.updateFlower(flower.getId(), flower.getName(), flower.getPrice(), flower.getQuantityInStock() - count);
        return cart;
    }

    private Double calculateTotalPrice(Map<Flower, FlowerInformation> flowers, Short discount) {
        Double price = 0.0;
        for (FlowerInformation flowerInfo : flowers.values()) {
            price += flowerInfo.getPrice();
        }
        if (discount != null && discount != 0) {
            price = ((100 - discount) * price) / 100;
        }
        return price;
    }
}
