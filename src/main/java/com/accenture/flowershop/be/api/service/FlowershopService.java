package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.fe.application.Cart;

public interface FlowershopService {
    void customerRegistration(String name, String secondName, String fatherName, String city, String street, Integer code,
                              Integer building, String phone, String email, String password) throws EntityCreationException;

    Cart addFlowersToCart(Cart cart, String flowerName, Integer count, Short discount);
}