package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.api.service.FlowershopService;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;

@Service
@Transactional
public class FlowershopServiceImpl implements FlowershopService {
    public static final String ERROR_ENTITY_CREATION = "Error on creation {0}";
    private final Double STARTING_BALANCE = 2000.0;
    private final Short STARTING_DISCOUNT = 3;


    @Autowired
    CustomerService customerService;

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    public void customerRegistration(String name, String secondName, String fatherName, String city, String street,
                                     Integer code, Integer building, String phone, String email, String password) throws EntityCreationException {
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
            throw new EntityCreationException(MessageFormat.format(ERROR_ENTITY_CREATION, Constants.ENTITY_ADDRESS));
        }

        Integer userId;
        try {
            userId = userService.insertUser(email, CommonUtils.getPasswordEncoder().encode(password));
        } catch (EntityException e) {
            addressService.deleteAddress(addressId);
            throw new EntityCreationException(e.getMessage());
        }

        try {
            customerService.insertCustomer(name, secondName, fatherName, addressId, phone,
                    STARTING_BALANCE, STARTING_DISCOUNT, email);
        } catch (EntityException e) {
            userService.deleteUser(userId);
            addressService.deleteAddress(addressId);
            throw new EntityCreationException(e.getMessage());
        }
    }
}
