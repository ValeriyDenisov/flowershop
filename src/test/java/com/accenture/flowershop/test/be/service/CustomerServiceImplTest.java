package com.accenture.flowershop.test.be.service;

import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.test.be.AbstractTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class CustomerServiceImplTest extends AbstractTest {
    public static final String CUSTOMER_TABLE_NAME = "customers";
    public static final String CUSTOMER_NAME_1 = "name_1";
    public static final String CUSTOMER_SECOND_NAME_1 = "secondName_1";
    public static final String CUSTOMER_FATHER_NAME_1 = "fatherName_1";
    public static final String CUSTOMER_PHONE_1 = "phone_1";
    public static final Double CUSTOMER_BALANCE_1 = 111.11;
    public static final Short CUSTOMER_DISCOUNT_1 = 1;
    public static final String CUSTOMER_EMAIL_1 = "email_1";
    public static final String CUSTOMER_NAME_2 = "name_2";
    public static final String CUSTOMER_SECOND_NAME_2 = "secondName_2";
    public static final String CUSTOMER_FATHER_NAME_2 = "fatherName_2";
    public static final String CUSTOMER_PHONE_2 = "phone_2";
    public static final Double CUSTOMER_BALANCE_2 = 222.22;
    public static final Short CUSTOMER_DISCOUNT_2 = 2;
    public static final String CUSTOMER_EMAIL_2 = "email_2";
    public static final String CUSTOMER_EMAIL_3= "email_3";

    @Autowired
    CustomerService customerService;

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void insertCustomerAddressNotFoundTest() {
        customerService.insertCustomer(CUSTOMER_NAME_1, CUSTOMER_SECOND_NAME_1, CUSTOMER_FATHER_NAME_1, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_1, CUSTOMER_DISCOUNT_1, CUSTOMER_EMAIL_1);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void insertCustomerExistsByPhoneTest() {
        customerService.insertCustomer(CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void insertCustomerExistsByEmailTest() {
        customerService.insertCustomer(CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_1);
    }


    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void deleteCustomerNotFoundTest() {
        customerService.insertCustomer(CUSTOMER_NAME_1, CUSTOMER_SECOND_NAME_1, CUSTOMER_FATHER_NAME_1, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_1, CUSTOMER_DISCOUNT_1, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql"})
    public void updateCustomerNotFoundTest() {
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2,CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerAddressNotFoundTest() {
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 555,
                CUSTOMER_PHONE_1, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerExistsByPhoneTest() {
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_2, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_3);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerExistsByEmailTest() {
        customerService.updateCustomer(1, CUSTOMER_NAME_2, CUSTOMER_SECOND_NAME_2, CUSTOMER_FATHER_NAME_2, 1,
                CUSTOMER_PHONE_2, CUSTOMER_BALANCE_2, CUSTOMER_DISCOUNT_2, CUSTOMER_EMAIL_2);
    }

    private Customer createCustomer(String name, String secondName, String fatherName,
                                   Address address, String phone, Double balance, Short discount, String email) {
        return new Customer.Builder(name, secondName, address, phone, balance, discount, email).fatherName(fatherName).build();
    }
}
