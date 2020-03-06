package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Test;
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

    @Autowired
    FlowerService flowerService;

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void insertFlowerExistsTest() {
        flowerService.insertFlower(FLOWER_NAME_1, FLOWER_PRICE_1, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void deleteFlowerNotFoundTest() {
        flowerService.deleteFlower(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void updateFlowerNotFoundTest() {
        flowerService.updateFlower(404, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void updateFlowerExistsTest() {
        flowerService.updateFlower(1, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }
}

