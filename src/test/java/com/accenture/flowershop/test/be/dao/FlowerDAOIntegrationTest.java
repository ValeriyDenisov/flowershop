package com.accenture.flowershop.test.be.dao;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.test.be.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;

public class FlowerDAOIntegrationTest extends AbstractIntegrationTest {
    public static final String ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND = "Flower not found, expected found flowers: {0}";
    public static final String ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT = "Flower found: {0}, expected found flowers: {1}";

    @Autowired
    FlowerDAO flowerDAO;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_dao.sql"})
    public void flowerFindByPriceTest() {
        List<Flower> flowers;

        flowers = flowerDAO.findByParameters(null, null, null, null, null);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 4), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 4), 4, flowers.size());

        flowers = flowerDAO.findByParameters(null, 111.11, 444.44, null, null);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 4), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 4), 4, flowers.size());

        flowers = flowerDAO.findByParameters(null, 333.34, null, null, null);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 1), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 1), 1, flowers.size());

        flowers = flowerDAO.findByParameters(null, 111.11, 444.44, 2, null);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 2), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 2), 2, flowers.size());

        flowers = flowerDAO.findByParameters(null, 111.11, 444.44, null, 1);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 3), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 3), 3, flowers.size());

        flowers = flowerDAO.findByParameters(null, 333.33, 444.44, null, 1);
        Assert.assertFalse(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_NOT_FOUND, 1), CollectionUtils.isEmpty(flowers));
        Assert.assertEquals(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 3), 1, flowers.size());

        flowers = flowerDAO.findByParameters(null, 111.11, 222.22, null, 3);
        Assert.assertTrue(MessageFormat.format(ERROR_FLOWER_FIND_BY_PRICE_WRONG_COUNT, flowers.size(), 0), CollectionUtils.isEmpty(flowers));
    }
}
