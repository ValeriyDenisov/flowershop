package com.accenture.flowershop.test.be.service;


import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.service.FlowershopService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.fe.application.Cart;
import com.accenture.flowershop.test.be.AbstractTest;
import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class FlowershopImplTest extends AbstractTest {
    public static final String ERROR_CART_NULL = "Cart is null!";
    public static final String ERROR_CART_EMPTY = "Cart is empty";

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

    @Test(expected = EntityFindingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flowershop/create_flowershop_tabes.sql"})
    public void addFlowersToCartFlowerNotFound() {
        Cart cart = flowershopService.addFlowersToCart(null, "flower", 5, (short) 0);
    }

    @Test(expected = IllegalArgumentException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flowershop/create_flowershop_tabes.sql",
            "/sql/flowershop/insert_flower_flowershop.sql"})
    public void addFlowersToCartNotEnoughFlowersInStock() {
        Cart cart;
        cart = flowershopService.addFlowersToCart(null, "name_1", 500, (short) 0);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flowershop/create_flowershop_tabes.sql",
            "/sql/flowershop/insert_flower_flowershop.sql"})
    public void addFlowersToCart() {
        Cart cart;
        cart = flowershopService.addFlowersToCart(null, "name_1", 5, (short) 0);
        Assert.assertNotNull(ERROR_CART_NULL, cart);
        Assert.assertTrue(ERROR_CART_EMPTY, MapUtils.isNotEmpty(cart.getFlowers()));
        Assert.assertEquals((Double) 50.0, cart.getTotalPrice());
        Assert.assertEquals((Integer) 0, findByUniqueElement("name_1", Flower.class.getCanonicalName(), Flower.class, "name").getQuantityInStock());
        cart = flowershopService.addFlowersToCart(cart, "name_2", 5, (short) 0);
        cart = flowershopService.addFlowersToCart(cart, "name_2", 5, (short) 0);
        Assert.assertEquals(2, cart.getFlowers().size());
        Flower flower = findByUniqueElement("name_2", Flower.class.getCanonicalName(), Flower.class, "name");
        Assert.assertEquals((Integer) 10, cart.getFlowers().get(flower).getCountToOrder());
        cart = flowershopService.addFlowersToCart(null, "name_3", 10, (short) 10);
        flower = findByUniqueElement("name_3", Flower.class.getCanonicalName(), Flower.class, "name");
        Assert.assertEquals((Double) 270.0, cart.getTotalPrice());

    }


}
