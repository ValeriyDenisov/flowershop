package com.accenture.flowershop.test.integration.service;


import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AddressServiceImplIntegrationTest.class);

    @Autowired
    AddressService addressService;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void insertAddressTest() {
        logger.debug("[insertAddressTest]");
        addressService.insertAddress("sge", "sge", 1, 3);
        Address address = findById(3, Address.class);
        Assert.assertNotNull(address);
        Assert.assertEquals((Integer) 1,  address.getCode());
        Assert.assertEquals((Integer) 3,  address.getBuilding());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void updateAddressTest() {
        logger.debug("[updateAddressTest]");
        addressService.updateAddress(1, "street_5", "city_5", null, null);
        Address address = findById(1, Address.class);
        Assert.assertNotNull(address);
        Assert.assertEquals("street_5",  address.getStreet());
        Assert.assertEquals("city_5",  address.getCity());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void deleteAddressTest() {
        logger.debug("[deleteAddressTest]");
        addressService.deleteAddress(1);
        Address address = findById(1, Address.class);
        Assert.assertNull(address);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void deleteAddressNotFoundTest() {
        logger.debug("[deleteAddressNotFoundTest]");
        addressService.deleteAddress(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void updateAddressNotFoundTest() {
        logger.debug("[updateAddressNotFoundTest]");
        addressService.updateAddress(
                404, ADDRESS_STREET_2, ADDRESS_CITY_2, ADDRESS_CODE_1, ADDRESS_BUILDING_2);
    }
}
