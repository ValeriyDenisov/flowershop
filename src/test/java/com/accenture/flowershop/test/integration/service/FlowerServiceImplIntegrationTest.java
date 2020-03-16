package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class FlowerServiceImplIntegrationTest extends AbstractIntegrationTest {
    public static final String FLOWER_TABLE_NAME = "flowers";
    public static final String FLOWER_NAME_1 = "name_1";
    public static final Double FLOWER_PRICE_1 = 111.11;
    public static final Integer FLOWER_QUANTITY_IN_STOCK_1 = 1;
    public static final String FLOWER_NAME_2 = "name_2";
    public static final Double FLOWER_PRICE_2 = 222.22;
    public static final Integer FLOWER_QUANTITY_IN_STOCK_2 = 2;

    Logger logger = LoggerFactory.getLogger(FlowerServiceImplIntegrationTest.class);

    @Autowired
    FlowerService flowerService;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void insertFlowerTest() {
        logger.debug("[insertFlowerTest]");
        flowerService.insertFlower("name__!", 444.44, 10);
        Flower flower = findById(1, Flower.class);
        Assert.assertNotNull(flower);
        Assert.assertEquals("name__!", flower.getName());
        Assert.assertEquals((Integer) 10, flower.getQuantityInStock());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void updateFlowerTest() {
        logger.debug("[updateFlowerTest]");
        flowerService.updateFlower( 1, "other_name", null, null);
        Flower Flower = findById(1, Flower.class);
        Assert.assertNotNull(Flower);
        Assert.assertEquals("other_name", Flower.getName());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void deleteFlowerTest() {
        logger.debug("[deleteFlowerTest]");
        flowerService.deleteFlower(1);
        Flower Flower = findById(1, Flower.class);
        Assert.assertNull(Flower);
    }

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void insertFlowerExistsTest() {
        logger.debug("[insertFlowerExistsTest]");
        flowerService.insertFlower(FLOWER_NAME_1, FLOWER_PRICE_1, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void deleteFlowerNotFoundTest() {
        logger.debug("[deleteFlowerNotFoundTest]");
        flowerService.deleteFlower(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void updateFlowerNotFoundTest() {
        logger.debug("[updateFlowerNotFoundTest]");
        flowerService.updateFlower(404, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void updateFlowerExistsTest() {
        logger.debug("[updateFlowerExistsTest]");
        flowerService.updateFlower(1, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }
}

