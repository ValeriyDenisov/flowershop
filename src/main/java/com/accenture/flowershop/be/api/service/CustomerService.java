package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Integer id) throws EntityFindingException;

    Integer insertCustomer(String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount, String email) throws EntityCreatingException;

    void updateCustomer(Integer customerId, String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount,  String email) throws EntityUpdatingException;

    void updateCustomer(Customer customer, String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount,  String email) throws EntityUpdatingException;

    void deleteCustomer(Integer customerId) throws EntityDeletingException;


    Customer findCustomerByPhone(String phone) throws EntityFindingException;

    Customer findCustomerByEmail(String email) throws EntityFindingException;

    List<Customer> findAllCustomers();
}
