package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerServiceImpl extends AbstractServiceImpl<Customer> implements CustomerService {
    public static final String ERROR_CUSTOMER_NAME_EMPTY = "Поле name пользователя пусто!";
    public static final String ERROR_CUSTOMER_SECOND_NAME_EMPTY = "Поле secondName пользователя пусто!";
    public static final String ERROR_CUSTOMER_ADDRESS_NULL = "Поле address пользователя пусто!";
    public static final String ERROR_CUSTOMER_PHONE_EMPTY = "Поле phone пользователя пусто!";
    public static final String ERROR_CUSTOMER_BALANCE_NULL = "Поле balance пользователя пусто!";
    public static final String ERROR_CUSTOMER_DISCOUNT_NULL = "Поле discount пользователя пусто!";
    public static final String ERROR_CUSTOMER_EXISTS_BY_PHONE = "Пользователь с телефоном:{0} уже существует!";

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    AddressService addressService;

    public Customer findCustomerById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return customerDAO.findById(id);
    }

    public void insertCustomer(String name, String secondName, String fatherName, Integer addressId,
                               String phone, Double balance, Short discount) {
        CommonUtils.assertEmpty(name, ERROR_CUSTOMER_NAME_EMPTY);
        CommonUtils.assertEmpty(secondName, ERROR_CUSTOMER_SECOND_NAME_EMPTY);
        CommonUtils.assertNull(addressId, ERROR_CUSTOMER_ADDRESS_NULL);
        CommonUtils.assertEmpty(phone, ERROR_CUSTOMER_PHONE_EMPTY);
        CommonUtils.assertNull(balance, ERROR_CUSTOMER_BALANCE_NULL);
        CommonUtils.assertNull(discount, ERROR_CUSTOMER_DISCOUNT_NULL);


        if (isCustomerExistsByPhone(phone)) {
            throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
        }
        Address address = addressService.findAddressById(addressId);
        if (address == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Address.class, addressId));
        }

        Customer customer = createCustomer(name, secondName, fatherName, address, phone, balance, discount);
        customerDAO.insert(customer);
    }

    public void updateCustomer(Integer customerId, String name, String secondName, String fatherName, Integer addressId, String phone, Double balance, Short discount) {
        CommonUtils.assertNull(customerId, ERROR_ENTITY_ID_NULL);
        CommonUtils.assertEmpty(name, ERROR_CUSTOMER_NAME_EMPTY);
        CommonUtils.assertEmpty(secondName, ERROR_CUSTOMER_SECOND_NAME_EMPTY);
        CommonUtils.assertNull(addressId, ERROR_CUSTOMER_ADDRESS_NULL);
        CommonUtils.assertEmpty(phone, ERROR_CUSTOMER_PHONE_EMPTY);
        CommonUtils.assertNull(balance, ERROR_CUSTOMER_BALANCE_NULL);
        CommonUtils.assertNull(discount, ERROR_CUSTOMER_DISCOUNT_NULL);

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
        }
        Address address = addressService.findAddressById(addressId);
        if (address == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Address.class, addressId));
        }
        if (!customer.getPhone().equalsIgnoreCase(phone)) {
            if (isCustomerExistsByPhone(phone)) {
                throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
            }
        }

        customer.setAddress(address);
        customer.setBalance(balance);
        customer.setDiscount(discount);
        customer.setFatherName(fatherName);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setSecondName(secondName);
        customerDAO.update(customer);
    }

    public void deleteCustomer(Integer customerId) {
        CommonUtils.assertNull(customerId, ERROR_ENTITY_ID_NULL);

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            throw  new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, customerId));
        }

        customerDAO.delete(customer);
    }

    public Customer findCustomerByPhone(String phone) {
        CommonUtils.assertEmpty(phone, ERROR_CUSTOMER_PHONE_EMPTY);

        return customerDAO.findByPhone(phone);
    }

    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    private Customer createCustomer(String name, String secondName, String fatherName,
                                    Address address, String phone, Double balance, Short discount)  {
        return new Customer.Builder(name, secondName, address, phone, balance, discount).fatherName(fatherName).build();
    }

    private boolean isCustomerExistsByPhone(String phone) {
        return findCustomerByPhone(phone) != null;
    }

    private boolean isNotCustomerExistsByPhone(String phone) {
        return findCustomerByPhone(phone) == null;
    }

}
