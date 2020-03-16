package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.address.Address_;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.customer.Customer_;
import org.apache.commons.lang3.StringUtils;
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
public class CustomerServiceImpl extends AbstractServiceImpl<Customer, CustomerDAO> implements CustomerService {
    public static final String CUSTOMER_CLASS_NAME = Customer.class.getSimpleName();

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    AddressService addressService;

    @Override
    public String getEntityName() {
        return CUSTOMER_CLASS_NAME;
    }

    @Override
    public CustomerDAO getDAO() {
        return customerDAO;
    }

    @Override
    public Customer findCustomerById(Integer id) throws EntityFindingException {
        return findEntityByUniqueField(Customer_.ID, id);
    }

    @Override
    public Integer insertCustomer(String name, String secondName, String fatherName, Integer addressId,
                                  String phone, Double balance, Short discount, String email) throws EntityCreatingException {
        return insertEntity(getCustomerMandatoryFieldsValues(name, secondName, addressId, phone, balance, discount, email),
                () -> {
                    if (isEntityExistsByFiled(Customer_.EMAIL, email)) {
                        throwExceptionIfEntityAlreadyExistsByUniqueField(Customer_.EMAIL, email, (ex) -> {
                            throw new EntityCreatingException(ex);
                        });
                    }
                    if (isEntityExistsByFiled(Customer_.PHONE, phone)) {
                        throwExceptionIfEntityAlreadyExistsByUniqueField(Customer_.PHONE, phone, (ex) -> {
                            throw new EntityCreatingException(ex);
                        });
                    }
                    Address address = addressService.findAddressById(addressId);
                    assertEntityNull(address, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, AddressServiceImpl.ADDRESS_CLASS_NAME,
                            Address_.ID, addressId), (ex) -> {
                        throw new EntityCreatingException(ex);
                    });
                    return createCustomer(name, secondName, fatherName, address, phone, balance, discount, email);
                });
    }

    @Override
    public void updateCustomer(Integer customerId, String name, String secondName, String fatherName, Integer addressId,
                               String phone, Double balance, Short discount, String email) throws EntityUpdatingException {
        updateEntityByUniqueField(Customer_.ID, customerId, (customer) -> updateCustomerField(customer, name, secondName, fatherName, addressId, phone, balance, discount, email));
    }

    @Override
    public void updateCustomer(Customer customer, String name, String secondName, String fatherName, Integer addressId, String phone, Double balance, Short discount, String email) throws EntityUpdatingException {
        updateEntityByUniqueField(customer, (customerToUpdate) -> updateCustomerField(customerToUpdate, name, secondName, fatherName, addressId, phone, balance, discount, email));
    }

    @Override
    public void deleteCustomer(Integer customerId) throws EntityDeletingException {
        deleteEntityByUniqueField(Customer_.ID, customerId);
    }

    @Override
    public Customer findCustomerByPhone(String phone) throws EntityFindingException {
        return findEntityByUniqueField(Customer_.PHONE, phone);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return findEntityByUniqueField(Customer_.EMAIL, email);
    }

    public List<Customer> findAllCustomers() {
        return findAllEntities();
    }

    private Customer createCustomer(String name, String secondName, String fatherName,
                                    Address address, String phone, Double balance, Short discount, String email) {
        return new Customer.Builder(name, secondName, address, phone, balance, discount, email).fatherName(fatherName).build();
    }

    private Map<String, Object> getCustomerMandatoryFieldsValues(String name, String secondName,
                                                                 Integer addressId, String phone,
                                                                 Double balance, Short discount, String email) {
        Map<String, Object> fieldsValues = new HashMap<>();

        fieldsValues.put(Customer_.NAME, name);
        fieldsValues.put(Customer_.SECOND_NAME, secondName);
        fieldsValues.put(Customer_.ADDRESS, addressId);
        fieldsValues.put(Customer_.PHONE, phone);
        fieldsValues.put(Customer_.BALANCE, balance);
        fieldsValues.put(Customer_.DISCOUNT, discount);
        fieldsValues.put(Customer_.EMAIL, email);

        return fieldsValues;
    }

    private Map<String, Object> getCustomerMandatoryFieldsValues(Integer id, String name, String secondName,
                                                                 Integer addressId, String phone,
                                                                 Double balance, Short discount, String email) {
        Map<String, Object> fieldsValues = getCustomerMandatoryFieldsValues(name, secondName, addressId, phone, balance, discount, email);
        fieldsValues.put(Customer_.ID, id);
        return fieldsValues;
    }

    private void updateCustomerField(Customer customer, String name, String secondName, String fatherName, Integer addressId,
                                     String phone, Double balance, Short discount, String email) {
        if (StringUtils.isNotEmpty(name)) {
            customer.setName(name);
        }
        if (StringUtils.isNotEmpty(secondName)) {
            customer.setSecondName(secondName);
        }
        customer.setFatherName(fatherName);
        if (addressId != null) {
            Address address = addressService.findAddressById(addressId);
            assertEntityNull(address, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE, AddressServiceImpl.ADDRESS_CLASS_NAME,
                    Address_.ID, addressId), (ex) -> {
                throw new EntityUpdatingException(ex);
            });
        }
        if (StringUtils.isNotEmpty(phone) && !phone.equalsIgnoreCase(customer.getEmail())) {
            if (isEntityExistsByFiled(Customer_.PHONE, phone)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(Customer_.PHONE, phone, (ex) -> {
                    throw new EntityUpdatingException(ex);
                });
            }
            customer.setPhone(phone);
        }
        if (balance != null) {
            customer.setBalance(balance);
        }
        if (discount != null) {
            customer.setDiscount(discount);
        }
        if (StringUtils.isNotEmpty(email)) {
            if (isEntityExistsByFiled(Customer_.EMAIL, email)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(Customer_.EMAIL, email, (ex) -> {
                    throw new EntityUpdatingException(ex);
                });
            }
            customer.setEmail(email);
        }
    }
}
