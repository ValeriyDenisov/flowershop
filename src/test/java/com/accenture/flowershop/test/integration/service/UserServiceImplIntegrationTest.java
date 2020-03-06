package com.accenture.flowershop.test.integration.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class UserServiceImplIntegrationTest extends AbstractIntegrationTest {
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_NAME_1 = "name_1";
    public static final String USER_PASSWORD_1 = "password_1";
    public static final String USER_NAME_2 = "name_2";
    public static final String USER_PASSWORD_2 = "password_2";

    @Autowired
    UserService userService;

    @Test(expected = EntityCreatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void insertUserExistsByLogin() {
        userService.insertUser(USER_NAME_1, USER_PASSWORD_1);
    }

    @Test(expected = EntityDeletingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void deleteUserNotFound() {
        userService.deleteUser(404);
    }

    @Test(expected = EntityUpdatingException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void updateUserNotFound() {
        userService.updateUser(404, USER_NAME_1, USER_PASSWORD_1);
    }
}
