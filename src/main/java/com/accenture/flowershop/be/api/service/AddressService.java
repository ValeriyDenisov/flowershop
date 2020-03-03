package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.address.Address;

import java.util.List;

public interface AddressService {
    Integer insertAddress(String street, String city, Integer code, Integer building) throws EntityCreatingException;

    void deleteAddress(Integer id) throws EntityDeletingException;

    void updateAddress(Integer id, String street, String city, Integer code, Integer building) throws EntityUpdatingException;

    Address findAddressById(Integer id);

    List<Address> findAllAddresses();
}
