package com.accenture.flowershop.test.be.service;

import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.test.be.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class UserServiceImplTest extends AbstractTest<User> {
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_NAME_1 = "name_1";
    public static final String USER_PASSWORD_1 = "password_1";
    public static final String USER_NAME_2 = "name_2";
    public static final String USER_PASSWORD_2 = "password_2";

    @Autowired
    UserService userService;

    @Override
    protected String getTableName() {
        return USER_TABLE_NAME;
    }

    @Override
    protected Class<User> getType() {
        return User.class;
    }

    @Override
    protected void init() {

    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void insertUserExistsByLogin() {
        userService.insertUser(USER_NAME_1, USER_PASSWORD_1);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void deleteUserNotFound() {
        userService.deleteUser(404);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void updateUserNotFound() {
        userService.updateUser(404, USER_NAME_1, USER_PASSWORD_1);
    }

    @Test(expected = EntityException.class)
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void updateUserExistsByLogin() {
        userService.updateUser(1, USER_NAME_2, null);
    }

}
