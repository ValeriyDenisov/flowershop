package com.accenture.flowershop.test.integration.soap;

import com.accenture.flowershop.be.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;

public class UserSoapIntegrationTest extends AbstractSoapIntegrationTest {

    Logger logger = LoggerFactory.getLogger(UserSoapIntegrationTest.class);

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql"})
    public void createUserTest() {
        logger.debug("[createUserTest]");
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<createUserRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<login>name_1@1</login>")
                .append("\n\t<password>12356</password>")
                .append("\n\t<role>USER</role>")
                .append("\n</createUserRequest>");
        xmlResponse
                .append("<createUserResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</createUserResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void updateUserTest() {
        logger.debug("[updateUserTest]");
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<updateUserByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n\t<login>AAA@AAA</login>")
                .append("\n</updateUserByIdRequest>");
        xmlResponse
                .append("<updateUserByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</updateUserByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        User user = findById(1, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals( "AAA@AAA", user.getLogin());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void deleteUserTest() {
        logger.debug("[deleteUserTest]");
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<deleteUserByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n</deleteUserByIdRequest>");
        xmlResponse
                .append("<deleteUserByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</deleteUserByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        User user = findById(1, User.class);
        Assert.assertNull(user);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void findAllUsersTest() {
        logger.debug("[findAllUsersTest]");
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findAllUsersRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n</findAllUsersRequest>");
        xmlResponse
                .append("<findAllUsersResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<users>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<login>name_1@1</login>")
                .append("\n\t\t<password>password_1</password>")
                .append("\n\t\t<role>USER</role>")
                .append("\n\t</users>")
                .append("\n\t<users>")
                .append("\n\t\t<id>2</id>")
                .append("\n\t\t<login>name_2@2</login>")
                .append("\n\t\t<password>password_2</password>")
                .append("\n\t\t<role>USER</role>")
                .append("\n\t</users>")
                .append("\n</findAllUsersResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/user/create_user_table.sql",
            "/sql/user/insert_user.sql"})
    public void findUserByIdTest() {
        logger.debug("[findUserByIdTest]");
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findUserByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</findUserByIdRequest>");
        xmlResponse
                .append("<findUserByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<user>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<login>name_1@1</login>")
                .append("\n\t\t<password>password_1</password>")
                .append("\n\t\t<role>USER</role>")
                .append("\n\t</user>")
                .append("\n</findUserByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }
}
