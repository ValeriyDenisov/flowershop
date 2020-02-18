package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.address.Address;

import java.util.List;

public interface AddressService {
    Integer insertAddress(String street, String city, Integer code, Integer building);

    void deleteAddress(Integer id);

    void updateAddress(Integer id, String street, String city, Integer code, Integer building);

    Address findAddressById(Integer id);

    List<Address> findAllAddresses();
}
