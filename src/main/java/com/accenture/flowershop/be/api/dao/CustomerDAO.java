package com.accenture.flowershop.be.api.dao;

import com.accenture.flowershop.be.entity.user.Customer;

public interface CustomerDAO {
    Customer findById(int id);
}
