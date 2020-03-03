package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Integer id);

    Integer insertCustomer(String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount, String email) throws EntityCreatingException;

    void updateCustomer(Integer customerId, String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount,  String email) throws EntityUpdatingException;

    void deleteCustomer(Integer customerId) throws EntityDeletingException;


    Customer findCustomerByPhone(String phone);

    Customer findCustomerByEmail(String email);

    List<Customer> findAllCustomers();
}
