package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.service.CustomerServiceImpl;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class CustomerServiceImplIntegrationTest extends AbstractIntegrationTest {
    public static final String CUSTOMER_TABLE_NAME = "customers";
    public static final String CUSTOMER_NAME_1 = "name_1";
    public static final String CUSTOMER_SECOND_NAME_1 = "secondName_1";
    public static final String CUSTOMER_FATHER_NAME_1 = "fatherName_1";
    public static final String CUSTOMER_PHONE_1 = "1113334455";
    public static final Double CUSTOMER_BALANCE_1 = 111.11;
    public static final Short CUSTOMER_DISCOUNT_1 = 1;
    public static final String CUSTOMER_EMAIL_1 = "email_1@1";
    public static final String CUSTOMER_NAME_2 = "name_2";
    public static final String CUSTOMER_SECOND_NAME_2 = "secondName_2";
    public static final String CUSTOMER_FATHER_NAME_2 = "fatherName_2";
    public static final String CUSTOMER_PHONE_2 = "1112223344";
    public static final Double CUSTOMER_BALANCE_2 = 222.22;
    public static final Short CUSTOMER_DISCOUNT_2 = 2;
    public static final String CUSTOMER_EMAIL_2 = "email_2@1";
    public static final String CUSTOMER_EMAIL_3 = "email_3@1";

    Logger logger = LoggerFactory.getLogger(CustomerServiceImplIntegrationTest.class);

    @Autowired
    CustomerService customerService;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql"})
    public void insertCustomerTest() {
        logger.debug("[insertCustomerTest]");
        customerService.insertCustomer("name", "secondName", null, 1, "7778889900",
                999.99, (short) 10, "YYY@YYY");
        Customer customer = findById(1, Customer.class);
        Assert.assertNotNull(customer);
        Assert.assertEquals("7778889900", customer.getPhone());
        Assert.assertEquals("YYY@YYY", customer.getEmail());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerTest() {
        logger.debug("[updateCustomerTest]");
        customerService.updateCustomer(1, null, "second", null, null, "5558889900",
                null, null, "AAA@YYY");
        Customer customer = findById(1, Customer.class);
        Assert.assertNotNull(customer);
        Assert.assertEquals("5558889900", customer.getPhone());
        Assert.assertEquals("AAA@YYY", customer.getEmail());
        Assert.assertEquals("second", customer.getSecondName());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void deleteCustomerTest() {
        logger.debug("[deleteCustomerTest]");
        customerService.deleteCustomer(1);
        Customer customer = findById(1, Customer.class);
        Assert.assertNull(customer);
    }

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void insertCustomerAddressNotFoundTest() {
        logger.debug("[insertCustomerAddressNotFoundTest]");
        customerService.insertCustomer(CUSTOMER_NAME_1, CUSTOMER_SECOND_NAME_1, CUSTOMER_FATHER_NAME_1, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_1, CUSTOMER_DISCOUNT_1, CUSTOMER_EMAIL_1);
    }

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void insertCustomerExistsByPhoneTest() {
        logger.debug("[insertCustomerExistsByPhoneTest]");
        customerService.insertCustomer(CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void insertCustomerExistsByEmailTest() {
        logger.debug("[insertCustomerExistsByEmailTest]");
        customerService.insertCustomer(CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_1);
    }


    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void deleteCustomerNotFoundTest() {
        logger.debug("[deleteCustomerNotFoundTest]");
        customerService.insertCustomer(CUSTOMER_NAME_1, CUSTOMER_SECOND_NAME_1, CUSTOMER_FATHER_NAME_1, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_1, CUSTOMER_DISCOUNT_1, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void updateCustomerNotFoundTest() {
        logger.debug("[updateCustomerNotFoundTest]");
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerAddressNotFoundTest() {
        logger.debug("[updateCustomerAddressNotFoundTest]");
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 555,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerExistsByPhoneTest() {
        logger.debug("[updateCustomerExistsByPhoneTest]");
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_2, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerExistsByEmailTest() {
        logger.debug("[updateCustomerExistsByEmailTest]");
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_2, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_2);
    }
}
