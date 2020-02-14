package com.accenture.flowershop.test.be.service;


import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.test.be.AbstractTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.text.MessageFormat;

public class AddressServiceImplTest extends AbstractTest<Address> {
    public static final String ADDRESS_TABLE_NAME = "addresses";
    public static final String ADDRESS_STREET_1 = "street_1";
    public static final String ADDRESS_CITY_1 = "city_1";
    public static final Integer ADDRESS_CODE_1 = 111;
    public static final Integer ADDRESS_BUILDING_1 = 1;
    public static final String ADDRESS_STREET_2 = "street_2";
    public static final String ADDRESS_CITY_2 = "city_2";
    public static final Integer ADDRESS_CODE_2 = 222;
    public static final Integer ADDRESS_BUILDING_2 = 2;

    @Autowired
    AddressService addressService;

    @Override
    protected void init() {

    }

    @Override
    protected String getTableName() {
        return ADDRESS_TABLE_NAME;
    }

    @Override
    protected Class<Address> getType() {
        return Address.class;
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void updateAddressNotFoundTest() {
        addressService.updateAddress(404, ADDRESS_STREET_2, ADDRESS_CITY_2, ADDRESS_CODE_1, ADDRESS_BUILDING_2);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void deleteAddressNotFoundTest() {
        addressService.deleteAddress(404);
    }

    private Address createAddress(String street, String city, Integer code, Integer building) {
        return new Address.Builder(street, city, code, building).build();
    }
}
