package com.accenture.flowershop.test.be.service;

import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.test.be.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class FlowerServiceImplTest extends AbstractTest {
    public static final String FLOWER_TABLE_NAME = "flowers";
    public static final String FLOWER_NAME_1 = "name_1";
    public static final Double FLOWER_PRICE_1 = 111.11;
    public static final Integer FLOWER_QUANTITY_IN_STOCK_1 = 1;
    public static final String FLOWER_NAME_2 = "name_2";
    public static final Double FLOWER_PRICE_2 = 222.22;
    public static final Integer FLOWER_QUANTITY_IN_STOCK_2 = 2;

    @Autowired
    FlowerService flowerService;

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower.sql"})
    public void insertFlowerExistsTest() {
        flowerService.insertFlower(FLOWER_NAME_1, FLOWER_PRICE_1, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void deleteFlowerNotFoundTest() {
        flowerService.deleteFlower(404);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void updateFlowerNotFoundTest() {
        flowerService.updateFlower(404, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower.sql"})
    public void updateFlowerExistsTest() {
        flowerService.updateFlower(1, FLOWER_NAME_2, FLOWER_PRICE_2, FLOWER_QUANTITY_IN_STOCK_2);
    }

    private Flower createFlower(String name, Double price, Integer quantityInStock) {
        return new Flower.Builder(name, price, quantityInStock).build();
    }
}

