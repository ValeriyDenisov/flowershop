package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class UserServiceImplIntegrationTest extends AbstractIntegrationTest {
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_NAME_1 = "name_1@1";
    public static final String USER_PASSWORD_1 = "password_1";
    public static final String USER_NAME_2 = "name_2";
    public static final String USER_PASSWORD_2 = "password_2";

    Logger logger = LoggerFactory.getLogger(UserServiceImplIntegrationTest.class);
    
    @Autowired
    UserService userService;

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void insertUserTest() {
        logger.debug("[insertUserTest]");
        userService.insertUser("login@1", "12345");
        User user = findById(1, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals("login@1", user.getLogin());
        Assert.assertEquals( "12345", user.getPassword());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void updateUserTest() {
        logger.debug("[insertUserTest]");
        userService.updateUser(1, "name_1@123", null);
        User user = findById(1, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals("name_1@123", user.getLogin());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void deleteUserTest() {
        logger.debug("[deleteUserTest]");
        userService.deleteUser(1);
        User user = findById(1, User.class);
        Assert.assertNull(user);
    }
    
    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void insertUserExistsByLogin() {
        logger.debug("[insertUserExistsByLogin]");
        userService.insertUser(USER_NAME_1, USER_PASSWORD_1);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void deleteUserNotFound() {
        logger.debug("[deleteUserNotFound]");
        userService.deleteUser(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void updateUserNotFound() {
        logger.debug("[updateUserNotFound]");
        userService.updateUser(404, USER_NAME_1, USER_PASSWORD_1);
    }
}
