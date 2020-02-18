package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreationException;

public interface FlowershopService {
    void customerRegistration(String name, String secondName, String fatherName, String city, String street, Integer code,
                              Integer building, String phone, String email, String password) throws EntityCreationException;
}