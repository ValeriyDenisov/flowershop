package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
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
public class CustomerServiceImpl extends AbstractServiceImpl implements CustomerService {
    public static final String ERROR_CUSTOMER_EXISTS_BY_PHONE = "Customer with phone: {0} already exists!";
    public static final String ERROR_CUSTOMER_EXISTS_BY_EMAIL = "Customer with email: {0} already exists!";

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    AddressService addressService;

    public Customer findCustomerById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return customerDAO.findById(id);
    }

    public Integer insertCustomer(String name, String secondName, String fatherName, Integer addressId,
                                  String phone, Double balance, Short discount, String email) throws EntityCreatingException {
        CommonUtils.assertValues(getCustomerFieldsValues(null, name, secondName, fatherName, addressId,
                phone, balance, discount, email));

        if (isCustomerExistsByPhone(phone)) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
            throw new EntityCreatingException(ex);
        }
        if (isCustomerExistsByEmail(email)) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_EMAIL, email));
            throw new EntityCreatingException(ex);
        }
        Address address = addressService.findAddressById(addressId);
        if (address == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Address.class, addressId));
            throw new EntityCreatingException(ex);
        }


        Customer customer = createCustomer(name, secondName, fatherName, address, phone, balance, discount, email);
        validateEntity(customer, (ex -> {
            throw new EntityCreatingException(ex);
        }));

        try {
            customerDAO.insert(customer);
        } catch (HibernateException e) {
            throw new EntityCreatingException(e);
        }
        return customer.getId();
    }

    public void updateCustomer(Integer customerId, String name, String secondName, String fatherName, Integer addressId,
                               String phone, Double balance, Short discount, String email) throws EntityUpdatingException {
        CommonUtils.assertNull(customerId, ERROR_ENTITY_ID_NULL);
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
            throw new EntityUpdatingException(ex);
        }

        if (addressId != null && !addressId.equals(customer.getAddress().getId())) {
            Address address = addressService.findAddressById(addressId);
            if (address == null) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Address.class, addressId));
                throw new EntityUpdatingException(ex);
            }
            customer.setAddress(address);
        }
        if (balance != null) {
            customer.setBalance(balance);
        }
        if (discount != null && !discount.equals(customer.getDiscount())) {
            customer.setDiscount(discount);
        }
        if (StringUtils.isNotEmpty(fatherName) && !fatherName.equalsIgnoreCase(customer.getFatherName())) {
            customer.setFatherName(fatherName);
        }
        if (StringUtils.isNotEmpty(name) && !name.equalsIgnoreCase(customer.getName())) {
            customer.setName(name);
        }
        if (StringUtils.isNotEmpty(phone) && !phone.equalsIgnoreCase(customer.getPhone())) {
            if (isCustomerExistsByPhone(phone)) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
                throw new EntityUpdatingException(ex);
            }
            customer.setPhone(phone);
        }
        if (StringUtils.isNotEmpty(secondName) && !secondName.equalsIgnoreCase(customer.getSecondName())) {
            customer.setSecondName(secondName);
        }
        if (StringUtils.isNotEmpty(email) && !email.equalsIgnoreCase(customer.getEmail())) {
            if (isCustomerExistsByEmail(email)) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_EMAIL, email));
                throw new EntityUpdatingException(ex);
            }
            customer.setEmail(email);
        }
        validateEntity(customer, (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        try {
            customerDAO.update(customer);
        } catch (HibernateException e) {
            throw new EntityUpdatingException(e);
        }
    }

    public void deleteCustomer(Integer customerId) throws EntityDeletingException {
        CommonUtils.assertNull(customerId, ERROR_ENTITY_ID_NULL);

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
            throw new EntityUpdatingException(ex);
        }

        try {
            customerDAO.delete(customer);
        } catch (HibernateException e) {
            throw new EntityDeletingException(e);
        }
    }

    public Customer findCustomerByPhone(String phone) {
        CommonUtils.assertEmpty(phone, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.CUSTOMER_PHONE));

        return customerDAO.findByUniqueElement(phone, Customer.PHONE);
    }

    public Customer findCustomerByEmail(String email) {
        CommonUtils.assertEmpty(email, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.CUSTOMER_EMAIL));

        return customerDAO.findByUniqueElement(email, Customer.EMAIL);
    }

    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    private Customer createCustomer(String name, String secondName, String fatherName,
                                    Address address, String phone, Double balance, Short discount, String email) {
        return new Customer.Builder(name, secondName, address, phone, balance, discount, email).fatherName(fatherName).build();
    }

    private boolean isCustomerExistsByPhone(String phone) {
        return findCustomerByPhone(phone) != null;
    }

    private boolean isCustomerExistsByEmail(String email) {
        return findCustomerByEmail(email) != null;
    }

    private Map<String, Object> getCustomerFieldsValues(Integer id, String name, String secondName,
                                                        String fatherName, Integer addressId, String phone,
                                                        Double balance, Short discount, String email) {
        Map<String, Object> fieldsValues = new HashMap<>();

        if (id != null) {
            fieldsValues.put(Constants.ENTITY_ID, id);
        }
        fieldsValues.put(Constants.CUSTOMER_NAME, name);
        fieldsValues.put(Constants.CUSTOMER_SECOND_NAME, secondName);
        if (StringUtils.isNotEmpty(fatherName)) {
            fieldsValues.put(Constants.CUSTOMER_FATHER_NAME, fatherName);
        }
        fieldsValues.put(Constants.CUSTOMER_ADDRESS, addressId);
        fieldsValues.put(Constants.CUSTOMER_PHONE, phone);
        fieldsValues.put(Constants.CUSTOMER_BALANCE, balance);
        fieldsValues.put(Constants.CUSTOMER_DISCOUNT, discount);
        fieldsValues.put(Constants.CUSTOMER_EMAIL, email);

        return fieldsValues;
    }
}
