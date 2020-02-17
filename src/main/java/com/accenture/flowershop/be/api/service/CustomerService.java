package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Integer id);

    void insertCustomer(String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount, String email);

    void updateCustomer(Integer customerId, String name, String secondName, String fatherName,
                        Integer addressId, String phone, Double balance, Short discount,  String email);

    void deleteCustomer(Integer customerId);


    Customer findCustomerByPhone(String phone);

    Customer findCustomerByEmail(String email);

    List<Customer> findAllCustomers();
}
