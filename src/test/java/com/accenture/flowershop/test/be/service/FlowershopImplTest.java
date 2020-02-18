package com.accenture.flowershop.test.be.service;


import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.service.FlowershopService;
import com.accenture.flowershop.test.be.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class FlowershopImplTest extends AbstractTest {

    @Autowired
    FlowershopService flowershopService;

    @Test(expected = EntityCreationException.class)
    @Sql({"/sql/delete_data_tables.sql",
    "/sql/flowershop/create_flowershop_tabes.sql",
    "/sql/user/insert_user.sql",})
    public void customerRegistrationUserExists() {
        flowershopService.customerRegistration(CustomerServiceImplTest.CUSTOMER_NAME_1,
                CustomerServiceImplTest.CUSTOMER_SECOND_NAME_2, null, AddressServiceImplTest.ADDRESS_CITY_1,
                AddressServiceImplTest.ADDRESS_STREET_1, AddressServiceImplTest.ADDRESS_CODE_1,
                AddressServiceImplTest.ADDRESS_BUILDING_1, CustomerServiceImplTest.CUSTOMER_PHONE_1,
                UserServiceImplTest.USER_NAME_1, UserServiceImplTest.USER_PASSWORD_1);
    }

    @Test(expected = EntityCreationException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flowershop/create_flowershop_tabes.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"
            })
    public void customerRegistrationCustomerExists() {
        flowershopService.customerRegistration(CustomerServiceImplTest.CUSTOMER_NAME_1,
                CustomerServiceImplTest.CUSTOMER_SECOND_NAME_2, null, AddressServiceImplTest.ADDRESS_CITY_1,
                AddressServiceImplTest.ADDRESS_STREET_1, AddressServiceImplTest.ADDRESS_CODE_1,
                AddressServiceImplTest.ADDRESS_BUILDING_1, CustomerServiceImplTest.CUSTOMER_PHONE_1,
                UserServiceImplTest.USER_NAME_1, UserServiceImplTest.USER_PASSWORD_1);
    }
}
