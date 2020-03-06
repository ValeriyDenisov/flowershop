package com.accenture.flowershop.test.integration.service;


import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class AddressServiceImplIntegrationTest extends AbstractIntegrationTest {
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

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void updateAddressNotFoundTest() {
        addressService.updateAddress(404, ADDRESS_STREET_2, ADDRESS_CITY_2, ADDRESS_CODE_1, ADDRESS_BUILDING_2);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void deleteAddressNotFoundTest() {
        addressService.deleteAddress(404);
    }
}
