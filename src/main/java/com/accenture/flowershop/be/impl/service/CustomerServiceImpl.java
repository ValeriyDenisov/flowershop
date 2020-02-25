package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl extends AbstractServiceImpl<Customer> implements CustomerService {
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
                               String phone, Double balance, Short discount, String email) {
        CommonUtils.assertValues(getCustomerFieldsValues(null, name, secondName, fatherName, addressId,
                phone, balance, discount, email));

        if (isCustomerExistsByPhone(phone)) {
            throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
        }
        if (isCustomerExistsByEmail(email)) {
            throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_EMAIL, email));
        }
        Address address = addressService.findAddressById(addressId);
        if (address == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Address.class, addressId));
        }


        Customer customer = createCustomer(name, secondName, fatherName, address, phone, balance, discount, email);
        customerDAO.insert(customer);
        return customer.getId();
    }

    public void updateCustomer(Integer customerId, String name, String secondName, String fatherName, Integer addressId,
                               String phone, Double balance, Short discount, String email) {
       CommonUtils.assertValues(getCustomerFieldsValues(customerId, name, secondName, fatherName, addressId, phone,
               balance, discount, email));

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
        if (!customer.getPhone().equalsIgnoreCase(phone)) {
            if (isCustomerExistsByPhone(phone)) {
                throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_PHONE, phone));
            }
        }
        if (!customer.getEmail().equalsIgnoreCase(email)) {
            if (isCustomerExistsByEmail(email)) {
                throw new EntityException(MessageFormat.format(ERROR_CUSTOMER_EXISTS_BY_EMAIL, email));
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
                                    Address address, String phone, Double balance, Short discount, String email)  {
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
        if (!StringUtils.isEmpty(fatherName)) {
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
